package renderer;
import geometries.Intersectable.GeoPoint;
import geometries.Intersectable;
import primitives.Point3D;
import primitives.Ray;
import scene.Scene;
import elements.*;
import primitives.*;
import java.lang.Math;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;
import static java.lang.Math.pow;
import static primitives.Util.alignZero;

public class Render {

    public class Pixel
    {
        private long _maxRows = 0; // Ny
        private long _maxCols = 0; // Nx
        private long _pixels = 0; // Total number of pixels: Nx*Ny
        public volatile int row = 0; // Last processed row
        public volatile int col = -1; // Last processed column
        private long _counter = 0; // Total number of pixels processed
        private int _percents = 0; // Percent of pixels processed
        private long _nextCounter = 0; // Next amount of processed pixels for percent progress
        private static final double COLOR_DIFFERENCE_THRESHOLD = 20;
        /**
         * The constructor for initializing the main follow up Pixel object
         * @param maxRows the amount of pixel rows
         * @param maxCols the amount of pixel columns
         */
        public Pixel(int maxRows, int maxCols)
        {
            _maxRows = maxRows;_maxCols = maxCols; _pixels = maxRows * maxCols;
            _nextCounter = _pixels / 100;
            if (Render.this._print) System.out.printf("\r %02d%%", _percents);
        }
        /**
         * Default constructor for secondary Pixel objects
         */
        public Pixel()
        {}

        /**
         * Public function for getting next pixel number into secondary Pixel object.
         * The function prints also progress percentage in the console window.
         * @param target target secondary Pixel object to copy the row/column of the next pixel
         * @return true if the work still in progress, -1 if it's done
         */
        public boolean nextPixel(Pixel target)
        {
            int percents = nextP(target);
            if (_print && percents > 0) System.out.printf("\r %02d%%", percents);
            if (percents >= 0) return true;
            if (_print) System.out.printf("\r %02d%%", 100);
            return false;
        }

        /**
         * Internal function for thread-safe manipulating of main follow up Pixel object - this function is
         * critical section for all the threads, and main Pixel object data is the shared data of this critical
         * section.<br/>
         * The function provides next pixel number each call.
         * @param target target secondary Pixel object to copy the row/column of the next pixel
         * @return the progress percentage for follow up: if it is 0 - nothing to print, if it is -1 - the task is
         * finished, any other value - the progress percentage (only when it changes)
         */
        private synchronized int nextP(Pixel target)
        {
            ++col;
            ++_counter;
            if (col < _maxCols)
            {
                target.row = this.row;
                target.col = this.col;
                if (_print && _counter == _nextCounter)
                {
                    ++_percents;
                    _nextCounter = _pixels * (_percents + 1) / 100;
                    return _percents;
                }
                return 0;
            }
            ++row;
            if (row < _maxRows)
            {
                col = 0;
                if (_print && _counter == _nextCounter)
                {
                    ++_percents;
                    _nextCounter = _pixels * (_percents + 1) / 100;
                    return _percents;
                }
                return 0;
            }
            return -1;
        }
















    }






    /**
     * value render
     */
    ImageWriter _imageWriter;
    Scene _scene;

    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;

    /**threshold for color difference (the percentage is the value / 255) */
    private static final double COLOR_DIFFERENCE_THRESHOLD = 20;
    private int _threads = 4;
    private final int SPARE_THREADS = 2; // Spare threads if trying to use all the cores
    private boolean _print = false; // printing progress percentage
    boolean _adaptiveSampling;//boolean flag for rendering with or without adaptive sampling


    /**
     * constructor
     *
     * @param _scene
     */
    public Render(Scene _scene) {
        this._scene = _scene;
    }

    /**
     * constructor
     *
     * @param imageWriter
     * @param scene
     */
    public Render(ImageWriter imageWriter, Scene scene) {
        this(imageWriter,scene,false);
    }

    /**
     * constructor
     *
     * @param imageWriter
     * @param scene
     */
    public Render(ImageWriter imageWriter, Scene scene,boolean adaptiveSampling) {
        this._imageWriter = imageWriter;
        this._scene = scene;
        _adaptiveSampling=adaptiveSampling;
    }

    /**
     * get_scene
     *
     * @return _scene
     */
    public Scene get_scene() {
        return _scene;
    }

    /**
     * Filling the buffer according to the geometries that are in the scene.
     * This function does not creating the picture file, but create the buffer pf pixels
     */
    public void renderImage() {

        java.awt.Color temp = _scene.get_background().getColor();
        Color background= new Color(temp);
        Camera camera = _scene.get_camera();
        Intersectable geometries = _scene.get_geometries();
        double distance = _scene.get_distance();

        //width and height are the number of pixels in the rows
        //and columns of the view plane
        int width = (int) _imageWriter.getWidth();
        int height = (int) _imageWriter.getHeight();

        //Nx and Ny are the width and height of the image.
        int Nx = _imageWriter.getNx();
        int Ny = _imageWriter.getNy();


        final Pixel thePixel = new Pixel(Ny, Nx); // Main pixel management object
        Thread[] threads = new Thread[_threads];
        for (int i = _threads - 1; i >= 0; --i)
        { // create all threads
            threads[i] = new Thread(() ->
            {
                Pixel pixel = new Pixel(); // Auxiliary thread’s pixel object
                //
                while (thePixel.nextPixel(pixel))
                {
                    if(thePixel.row==200&&thePixel.col==200)
                    {
                        int x=9;
                    }
                    Color c = _adaptiveSampling ? pixelColorByAdaptiveSampling( pixel,  Nx,  Ny,  distance, width,  height, camera,  background)
                            : pixelColorByRegularSampling( pixel,  Nx,  Ny,  distance, width,  height, camera,  background);
                    _imageWriter.writePixel( pixel.row, pixel.col, c.getColor());
                }
            }
            );
        }
        for (Thread thread : threads) thread.start(); // Start all the threads
// Wait for all threads to finish
        for (Thread thread : threads) try { thread.join(); } catch (Exception e) {}
        if (_print) System.out.printf("\r100%%\n"); // Print 100%

    }

    private Color pixelColorByAdaptiveSampling(Pixel pixel2, int Nx, int Ny, double distance,int width, int height,Camera camera, Color background) {
        Color resultColor = Color.BLACK;
        //create the pixel that is calculated by the camera

        PixelImp pixel =  _scene.get_camera().constructPixelCorners(Nx, Ny, pixel2.col, pixel2.row, distance, _scene.getFocusLength(), _scene.getApertureSize(),
                _imageWriter.getWidth(), _imageWriter.getHeight(), _scene.getdofRayBeamSize());
        //save the colors of the pixel corners
        setPixelCornersColors(pixel);
        //in case the colors of the corners are close enough - the result is the average of the corners colors
        if (isSameColor(pixel)) {
            return resultColor.add(pixel.aCornerRays.color, pixel.bCornerRays.color, pixel.cCornerRays.color,
                    pixel.dCornerRays.color).reduce(4);
        }

        //in case the colors are different - divide the pixel and check the sub pixels
        List<PixelImp> pixels = new ArrayList<PixelImp>();
        pixels.add(pixel);
        //every time sub pixels are added - the size of the list grows
        for (int k = 0; k < pixels.size(); ++k) {
            PixelImp subPixel = pixels.get(k);
            setPixelCornersColors(subPixel);
            //in case the colors of the sub pixel are different and it is not the last level of division - divide it
            if (!isSameColor(subPixel) && subPixel.getRank() < 64)
                pixels.addAll(_scene.get_camera().dividePixel(subPixel,  _scene.getFocusLength(), _scene.getApertureSize(),  _scene.getdofRayBeamSize()));
                //if colors are similar or the pixel is maximum divided - add the color part of the sub pixel
            else {
                Color tmpColor = subPixel.aCornerRays.color.add(subPixel.bCornerRays.color, subPixel.cCornerRays.color,
                        subPixel.dCornerRays.color);
                tmpColor = tmpColor.reduce(4);
                //the part contributed to the result is 1 / rank
                resultColor = resultColor.add(tmpColor.reduce(subPixel.getRank()));
            }
        }
        return resultColor;
    }
    /**
     * calculate and save the color of a rayBeam in a pixel if it is not saved already
     * @param pixel
     */
    private void setPixelCornersColors(PixelImp pixel) {
        //a corner that has not been checked for its color is null
        GeoPoint intersection=findCLosestIntersection(pixel.aCornerRays.rayBeam);
        if (pixel.aCornerRays.color == null)
            {
                if(intersection!=null)
                    pixel.aCornerRays.color = (calcColor(findCLosestIntersection(pixel.aCornerRays.rayBeam),pixel.aCornerRays.rayBeam));else
                    pixel.aCornerRays.color= _scene.get_background();}


         intersection=findCLosestIntersection(pixel.bCornerRays.rayBeam);
        if (pixel.bCornerRays.color == null)
        {
            if(intersection!=null)
                pixel.bCornerRays.color = (calcColor(findCLosestIntersection(pixel.bCornerRays.rayBeam),pixel.bCornerRays.rayBeam));else
                pixel.bCornerRays.color= _scene.get_background();}


         intersection=findCLosestIntersection(pixel.cCornerRays.rayBeam);
        if (pixel.cCornerRays.color == null)
        {
            if(intersection!=null)
                pixel.cCornerRays.color = (calcColor(findCLosestIntersection(pixel.cCornerRays.rayBeam),pixel.cCornerRays.rayBeam));else
                pixel.cCornerRays.color= _scene.get_background();}

         intersection=findCLosestIntersection(pixel.dCornerRays.rayBeam);
        if (pixel.dCornerRays.color == null)
        {
            if(intersection!=null)
                pixel.dCornerRays.color = (calcColor(findCLosestIntersection(pixel.dCornerRays.rayBeam),pixel.dCornerRays.rayBeam)); else
                pixel.dCornerRays.color= _scene.get_background();}


    }

    /**
     * method calculates the average color of a rays beam
     * @param rayBeam - list of rays
     * @return the average color of the rays
     */
    private Color calcRayBeamColor(Ray rayBeam) {
        Color resultColor = Color.BLACK;

            resultColor = resultColor.add(findCLosestIntersection(rayBeam) == null ? _scene.get_background() : calcColor(findCLosestIntersection(rayBeam), rayBeam));
        return resultColor;
    }




    private Color pixelColorByRegularSampling(Pixel pixel, int Nx, int Ny, double distance,int width, int height,Camera camera, Color background) {
        ArrayList<Ray>rayList = camera.constructRayThroughPixel(Nx, Ny, pixel.row, pixel.col, distance, width, height);
        Color color=Color.BLACK;
        double counter=0;
        for(Ray ray:rayList){
            GeoPoint intersectionPoints = findCLosestIntersection(ray);

            if (intersectionPoints==null) {

                color=color.add(background);
                //_imageWriter.writePixel( row,column, background);
            } else {
                counter++;
                color=color.add(calcColor(intersectionPoints,ray));
                //GeoPoint closestPoint = getClosestPoint(ray);
                //_imageWriter.writePixel( row,column, calcColor(intersectionPoints,ray).getColor());
            }
        }
        Color c;
        if(counter==0)
            c=new Color(background);
        else
        {
            c=color;
            c=c.scale(1d/rayList.size());}//the average of all the rays
        return c;

    }

    /**
     * checks if corners colors of a pixel are close enough to be considered as the same color
     * @param pixel
     * @return boolean same or not
     */
    private boolean isSameColor(PixelImp pixel) {
        // check if any corner is different dramatically from it's neighbors
        return (difference(pixel.aCornerRays.color, pixel.bCornerRays.color) < COLOR_DIFFERENCE_THRESHOLD
                && difference(pixel.aCornerRays.color, pixel.dCornerRays.color) < COLOR_DIFFERENCE_THRESHOLD
                && difference(pixel.cCornerRays.color, pixel.bCornerRays.color) < COLOR_DIFFERENCE_THRESHOLD
                && difference(pixel.cCornerRays.color, pixel.dCornerRays.color) < COLOR_DIFFERENCE_THRESHOLD);
    }

    /**
     * checks the difference between two colors and returns the difference value
     * @param color1
     * @param color2
     * @return difference value (called the delta in math)
     */
    private double difference(Color color1, Color color2) {
        //difference of color is (|R1 - R2| + |G1 - G2| + |B1 - B2|) / 255
        double r = Math.abs(color1.getColor().getRed() - color2.getColor().getRed());
        double g = Math.abs(color1.getColor().getGreen() - color2.getColor().getGreen());
        double b = Math.abs(color1.getColor().getBlue() - color2.getColor().getBlue());
        // the value is still related to 255 whole value, the threshold is the fragment of 255
        return r + g + b;
    }





















    /**
     * Finding the closest point to the P0 of the camera.
     *
     * @param intersectionPoints list of points, the function should find from
     *                           this list the closet point to P0 of the camera in the scene.
     * @return the closest point to the camera
     */

    private GeoPoint getClosestPoint(List<Intersectable.GeoPoint> intersectionPoints) {
        GeoPoint result = null;
        double mindist = Double.MAX_VALUE;

        Point3D p0 = this._scene.get_camera().get_p0();

        for (Intersectable.GeoPoint pt : intersectionPoints) {
            double distance = p0.distance(pt.point);
            if (distance < mindist) {
                mindist = distance;
                result = new GeoPoint(pt.geometry, pt.point);
            }
        }
        return result;
    }

    /**
     * Printing the grid with a fixed interval between lines
     *
     * @param interval The interval between the lines.
     */
    public void printGrid(int interval, Color colorsep) {
        double rows = this._imageWriter.getNx();
        double collumns = _imageWriter.getNy();
        //Writing the lines.
        for (int col = 0; col < collumns; col++) {
            for (int row = 0; row < rows; row++) {
                if (col % interval == 0 || row % interval == 0) {
                    _imageWriter.writePixel(row, col, colorsep.getColor());
                }
            }
        }
    }

    /**
     * writing to image
     */
    public void writeToImage() {
        _imageWriter.writeToImage();
    }

    /**
     * Calculate the color intensity in a point
     *
     * @param intersection the point for which the color is required
     * @return the color intensity
     */
    private Color calcColor(GeoPoint intersection ,Ray ray, int level, double k) {

        //return _scene.get_ambientLight().get_intensity().add(point.geometry.get_emmission());
        if(level==1)
            return  Color.BLACK;

        Color color = intersection.geometry.get_emmission();
//        color = color.add(intersection.geometry.get_emmission());
        Vector v = intersection.point.subtract(_scene.get_camera().get_p0()).normalize();
        Vector n = intersection.geometry.getNormal(intersection.point);
        double nv = alignZero(n.dotProduct(v));
        if (nv == 0) {
            //ray parallel to geometry surface ??
            //and orthogonal to normal
            return color;
        }

        Material material = intersection.geometry.get_material();
        int nShininess = material.getnShininess();
        double kd = material.getKd();
        double ks = material.getKs();
        double ktr;
        for (LightSource lightSource : _scene.get_lights()) {
            ArrayList<Vector> l = lightSource.getL(intersection.point);
                if (sign(n.dotProduct(l.get(0))) == sign(n.dotProduct(v))) {
//                Color lightIntensity = lightSource.getIntensity(intersection.point);
//                color = color.add(calcDiffusive(kd, l, n, lightIntensity),
//                        calcSpecular(ks, l, n, v, nShininess, lightIntensity));

                     ktr = transparency(lightSource, l, n, intersection);

                    if (ktr * k > MIN_CALC_COLOR_K) {

                        Color lightIntensity = lightSource.getIntensity(intersection.point).scale(ktr);
                        color = color.add(calcDiffusive(kd, l.get(0), n, lightIntensity),
                                calcSpecular(ks, l.get(0), n, v, nShininess, lightIntensity));
                    }

            }

        }
        double kr = intersection.geometry.get_material().get_kR();
        double kkr = k * kr;
        if(kkr>MIN_CALC_COLOR_K)
        {
            Ray reflectedRay=constructReflectedRay(n,intersection,ray);
            GeoPoint reflerctPoint=findCLosestIntersection(reflectedRay);
            if(reflerctPoint!=null)
                color = color.add(calcColor(reflerctPoint, reflectedRay,level-1, kkr).scale(kr));
        }
        double kt = intersection.geometry.get_material().get_kT();
        double kkt = k * kt;
        if(kkt>MIN_CALC_COLOR_K)
        {
            Ray transperantRay=constructTransperantRay(intersection,ray,n);
            GeoPoint transperantPoint=findCLosestIntersection(transperantRay);
            if(transperantPoint!=null)
                color = color.add(calcColor(transperantPoint, transperantRay,level-1, kkt).scale(kt));
        }
        return color;

    }

    /**
     * a func that calculet the color of specular
     * @param ks
     * @param l
     * @param n
     * @param v
     * @param nShininess
     * @param lightIntensity
     * @return the color of specular giving
     */
    private Color calcSpecular(double ks, Vector l, Vector n, Vector v, int nShininess, Color lightIntensity) {
        Vector r = l.subtract(n.scale(2 * (l.dotProduct(n))));
        return lightIntensity.scale(ks * (pow((-v.dotProduct(r)), nShininess)));
    }

    /**
     * a func that calculet the color of diffusive
     * @param kd
     * @param l
     * @param n
     * @param lightIntensity
     * @return the color of diffusive giving
     */
    private Color calcDiffusive(double kd, Vector l, Vector n, Color lightIntensity) {
        return lightIntensity.scale(kd * abs(l.dotProduct(n)));
    }

    /**
     * a func to check positivity of numbers
     * @param val
     * @return True if the number is positive
     */
    private boolean sign(double val) {
        return (val > 0d);
    }


    /**
     * func to chrck if a point is shadowed
     * @param l
     * @param n
     * @param gp
     * @return false if point id shadowed
     */
    private boolean unshaded(Vector l, Vector n, GeoPoint gp,LightSource ls)
    {
        Vector lightDirection = l.scale(-1); // from point to light source
//        Vector delta = n.scale(n.dotProduct(lightDirection) > 0 ? DELTA : - DELTA);
//        Point3D point = gp.point.add(delta);
        Ray lightRay = new Ray( gp.point,lightDirection, n);
        List<GeoPoint> intersections = _scene.get_geometries().findIntsersections(lightRay);


        if (intersections.size() == 0) {
            return true;
        }
        double lightDistance = ls.getDistance(gp.point);
        for (GeoPoint gp1 : intersections) {
            double temp = gp1.point.distance(gp.point) - lightDistance;
            if (alignZero(temp) <= 0 && gp.geometry.get_material().get_kT() !=1)
                return false;
        }
        return true;

    }

    /**
     * function to construct reflaction ray
     * @param n
     * @param point
     * @param v
     * @return
     */
    private Ray constructReflectedRay(Vector n, GeoPoint point, Ray v)
    {
        //r = v − 2 * (v *n) * n
        if(v.get_dir().dotProduct(n)==0)
        {
            return null;
        }
        return new Ray(point.point,v.get_dir().subtract(n.scale(2*v.get_dir().dotProduct(n))),n);
    }

    /**
     * unction to construct transperant ray
     * @param point
     * @param v
     * @param n
     * @return
     */
    private Ray constructTransperantRay(GeoPoint point, Ray v,Vector n)
    {
        return new Ray(point.point,v.get_dir(),n);

    }

    /**
     * returns the closest point to the light sourse from the intersection list
     * @param ray
     * @return GeoPoint
     */
    private GeoPoint findCLosestIntersection(Ray ray)
    {
        if (ray == null) {
            return null;
        }

        GeoPoint closestPoint = null;
        double closestDistance = Double.MAX_VALUE;//the biggest number that exist in double
        Point3D ray_p0 = ray.get_p0();
        List<GeoPoint> intersections = _scene.get_geometries().findIntsersections(ray);
        if(intersections.size()==0)
            return null;
        for (GeoPoint geoPoint : intersections) {
            double distance = ray_p0.distance(geoPoint.point);
            if (distance < closestDistance) {
                closestPoint = geoPoint;
                closestDistance = distance;
            }
        }
        return closestPoint;
    }

    /**
     * outer method for calccolor
     * @param gp
     * @param ray
     * @return color in a point
     */
    private Color calcColor(GeoPoint gp, Ray ray)
    {
        Color color = calcColor(gp, ray, MAX_CALC_COLOR_LEVEL, 1.0);
        color = color.add(_scene.get_ambientLight().get_intensity());
        return color;
    }

    /**
     *
     * @param ls
     * @param l
     * @param n
     * @param gp
     * @return
     */
    private double transparency(LightSource ls, ArrayList<Vector> l, Vector n, GeoPoint gp)
    {
        int aic=0;
        if (gp.point.get_x().get()==0)
            aic=1;
        double ktr;
        double avKT=0;
        double counterUNshadow=0;
        double counterShadow=0;
        for(Vector v:l) {
            Vector lightDirection = v.scale(-1); // from point to light source
//        Vector delta = n.scale(n.dotProduct(lightDirection) > 0 ? DELTA : - DELTA);
//        Point3D point = gp.point.add(delta);
            Ray lightRay = new Ray(gp.point, lightDirection, n);
            List<GeoPoint> intersections = _scene.get_geometries().findIntsersections(lightRay);
            if (intersections.size() == 0) {
                counterUNshadow+=1;

            }
            else {
                double lightDistance = ls.getDistance(gp.point);
                ktr = 1.0;
                //מחשב את קבוע השקיפות עבור כל הגאומטריות שנחתכות עבור קרן מסויימת
                for (GeoPoint gp1 : intersections) {
                    double temp = gp1.point.distance(gp.point) - lightDistance;
                    if (alignZero(temp) <= 0) {
                        ktr *= gp1.geometry.get_material().get_kT();
                        if (ktr < MIN_CALC_COLOR_K)
                            ktr*=0;

                    }
                }
                avKT += ktr;
                counterShadow++;
            }
        }
        if(counterShadow!=0)
            avKT/=counterShadow;
//            double present=
        return (counterUNshadow/l.size())*1d+(counterShadow/l.size())*avKT;
    }





    /**
     * Set multithreading <br>
     * - if the parameter is 0 - number of coress less SPARE (2) is taken
     * @param threads number of threads
     * @return the Render object itself
     */
    public Render setMultithreading(int threads) {
        if (threads < 0) throw new IllegalArgumentException("Multithreading must be 0 or higher");
        if (threads != 0) _threads = threads;
        else {
            int cores = Runtime.getRuntime().availableProcessors() - SPARE_THREADS;
            _threads = cores <= 2 ? 1 : cores;
        }
        return this;
    }
    /**
     * Set debug printing on
     * @return the Render object itself
     */
    public Render setDebugPrint() { _print = true; return this; }

}
