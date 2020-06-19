package elements;

import geometries.Intersectable;
import geometries.Plane;
import primitives.*;
import renderer.Render;
import renderer.Render.Pixel;

import java.util.ArrayList;
import java.util.Random;

import static primitives.Util.isZero;

public class Camera
{
    Point3D _p0;
    Vector _vUp;
    Vector _vTo;
    Vector _vRight;
    int NUM_RAY_BEAM;
    /**
     *
     * @return the center of the camera
     */
    public Point3D get_p0() {
        return _p0;
    }
    /**
     *
     * @return the axis Y vector
     */
    public Vector get_vUp() {
        return _vUp;
    }

    /**
     *
     * @return the axis z vector
     */
    public Vector get_vTo() {
        return _vTo;
    }

    /**
     *
     * @return the axis x vector
     */
    public Vector get_vRight() {
        return _vRight;
    }

    /**
     * constractor
     * @param _p0
     * @param _vUp
     * @param _vTo
     * @throws IllegalArgumentException
     */
    public Camera(Point3D _p0, Vector _vTo, Vector _vUp)throws IllegalArgumentException
    {
       this(_p0,_vTo,_vUp,50);
    }


    public Camera(Point3D _p0, Vector _vTo, Vector _vUp,int numOfRays)throws IllegalArgumentException
    {
        if(!(isZero( _vUp.dotProduct(_vTo))))
        {
            throw new IllegalArgumentException("the vectors are not orthogonal");
        }
        this._vUp = _vUp.normalized();
        this._vTo = _vTo.normalized();
        this._vRight = this._vTo.crossProduct(this._vUp).normalized();
        this._p0 =_p0;
        NUM_RAY_BEAM=numOfRays;
    }


    /**
     *
     * @param nX
     * @param nY
     * @param j
     * @param i
     * @param screenDistance
     * @param screenWidth
     * @param screenHeight
     * @return list of rays that goes from the same pixle randomly
     */
    public ArrayList<Ray> constructRayThroughPixel (int nX, int nY, int j, int i, double screenDistance, double screenWidth, double screenHeight)
    {
        if (isZero(screenDistance))
        {
            throw new IllegalArgumentException("distance cannot be 0");
        }
        //Pc = P0 + d∙Vto
        Point3D Pc = _p0.add(_vTo.scale(screenDistance));
        //Ratio (pixel width & height)
        double Ry = screenHeight/nY;
        double Rx = screenWidth/nX;

        double yi =  ((i - nY/2d)*Ry + Ry/2d);
        double xj=   ((j - nX/2d)*Rx + Rx/2d);

        Point3D Pij = Pc;

        if (! isZero(xj))
        {
            Pij = Pij.add(_vRight.scale(xj));
        }
        if (! isZero(yi))
        {
            Pij = Pij.add(_vUp.scale(-yi));
        }

        Vector Vij = Pij.subtract(_p0);
        Ray ray_center=new Ray(Vij.normalized(),_p0);

        ArrayList<Ray> rays=new ArrayList<>();
        rays.add(ray_center);///////////////////////////////////////the center ray as the old code

        double rang_miny=(i - nY/2d)*Ry;//setting the range for X and Y
        double rang_maxy=(i - nY/2d)*Ry+Ry;
        double rang_minx=(j - nX/2d)*Rx;
        double rang_maxx=(j - nX/2d)*Rx + Rx;
        Random rand =  new Random();
        for(int k=0;k<NUM_RAY_BEAM;k++)//constructing the rays
        {
            double x = rang_minx+(rang_maxx-rang_minx)*rand.nextDouble();
            double y = rang_miny+(rang_maxy-rang_miny)*rand.nextDouble();
            Pij = Pc;
            if (! isZero(x))
            {
                Pij = Pij.add(_vRight.scale(x));
            }
            if (! isZero(yi))
            {
                Pij = Pij.add(_vUp.scale(-y));
            }

            Vij = Pij.subtract(_p0);
            Ray ray=new Ray(Vij,_p0);
            rays.add(ray);
        }
        return rays;
//        Ray point_left_up= ezer(ray_center,Rx,Ry);
//        Ray point_right_up= ezer(ray_center,-Rx,Ry);
//        Ray point_left_down= ezer(ray_center,Rx,-Ry);
//        Ray point_right_down= ezer(ray_center,-Rx,-Ry);
//        ArrayList<Ray> l= new ArrayList<Ray>();
//        l.add(ray_center);
//        l.add(point_left_up);
//        l.add(point_right_up);
//        l.add(point_left_down);
//        l.add(point_right_down);
//        return l;

    }


















    /**
     * create a pixel on the view plane - made of four corners rayBeams
     * @param nx             the number of columns
     * @param ny             the number of rows
     * @param i              the place of the pixel in rows
     * @param j              the pace of the pixel in columns
     * @param screenDistance
     * @param focusLength
     * @param apertureSize
     * @param screenWidth
     * @param screenHeight
     * @param dofRayBeamSize    - for the blur of the unfocused objects
     * @return a Pixel object uniting the corner rays
     */
    public PixelImp constructPixelCorners(int nx, int ny, int j, int i, double screenDistance, double focusLength,
                                              double apertureSize, double screenWidth, double screenHeight, int dofRayBeamSize) {
        // Pc is the center point of the view plane: P0 + d*vTo
        Point3D pc = _p0.add(_vTo.scale(screenDistance));
        // ratio factors: rx is the width of each pixel
        double rx = screenWidth / nx;
        double ry = screenHeight / ny;
        // Xi and Yj are the coefficients that would take us to the asked point from the Pc point
        // Xi for moving in the X axis direction (right / left)
        double xi = ((i - (nx / 2d)) * rx);
        // Yj for moving in the Y axis direction (down / up)
        double yj = ((j - (ny / 2d)) * ry);
        // in case the both coefficients are zero, the asked point is the Pc point
        Point3D upperLeftPoint = pc;
        if (!Util.isZero(xi)) {
            upperLeftPoint = upperLeftPoint.add(_vRight.scale(xi));
        }
        if (!Util.isZero(yj)) {
            upperLeftPoint = upperLeftPoint.add(_vUp.scale(-yj));
        }
        // find 3 more points for corners of the pixel
        Point3D upperRightPoint = upperLeftPoint.add(_vRight.scale(rx));
        Point3D lowerRightPoint = upperRightPoint.add(_vUp.scale(ry));
        Point3D lowerLeftPoint = upperLeftPoint.add(_vUp.scale(ry));
        // build four rayBeams in the corners of the pixel A,B,C,D
        Ray aCorner = new Ray( upperLeftPoint.subtract(_p0),_p0);
        Ray bCorner = new Ray( upperRightPoint.subtract(_p0),_p0);
        Ray cCorner = new Ray( lowerRightPoint.subtract(_p0),_p0);
        Ray dCorner = new Ray( lowerLeftPoint.subtract(_p0),_p0);
        //create the desired pixel - Hallelujah!

        PixelImp pixel = new PixelImp(upperLeftPoint, aCorner, upperRightPoint, bCorner, lowerRightPoint, cCorner,
                lowerLeftPoint, dCorner, 1);
        return pixel;
    }



    /**
     * help method for calculating the raybeam of the DOF model for a specific original ray
     * @param originalRay - the ray to be replaced by a beam of intesecting rays
     * @param focusLength
     * @param apertureSize
     * @param dofRayBeamSize
     * @return a list of the rays for the focus calculation
     */
    private ArrayList<Ray> calcDOFRays(Ray originalRay, double focusLength, double apertureSize, int dofRayBeamSize) {
        ArrayList<Ray> raysBeam = new ArrayList<Ray>();
        //in case the aperture is zero - no focus or unfocus is needed
        if (Util.isZero(apertureSize) || dofRayBeamSize == 0) {
            raysBeam.add(originalRay);
            return raysBeam;
        }

        Point3D basePoint = originalRay.get_p0();
        // calculate focalPoint by building a plane for focus distance
        Point3D focalPlaneCenter = basePoint.add(_vTo.scale(focusLength));
        Plane focalPlane = new Plane(focalPlaneCenter, _vTo);
        ArrayList<Intersectable.GeoPoint> itersections = (ArrayList)focalPlane.findIntsersections(originalRay);
        Point3D focalPoint = itersections.get(0).point;

        // create rays randomly within the range of the aperture size, directed to the
        // focal point
        double halfAperture = apertureSize / 2d;
        for (int count = 0; count < dofRayBeamSize; count++) {
            //shift the point of the ray stat randomly within the range of the aperture size
            Point3D shiftedPoint = basePoint.add(_vRight.scale(Util.getNotZeroRandom() * halfAperture));
            shiftedPoint = shiftedPoint.add(_vUp.scale(Util.getNotZeroRandom() * halfAperture));
            Ray ray = new Ray( focalPoint.subtract(shiftedPoint),shiftedPoint);
            raysBeam.add(ray);
        }
        return raysBeam;
    }

    /**
     * divide a pixel on the view plane into four subpixels made of the corners and additional 5 rayBeams shot inside
     * @param mainPixel - the pixel to divide
     * @param focusLength
     * @param apertureSize
     * @param dofRayBeamSize
     * @return a list of the 4 new subPixels
     */
    public ArrayList<PixelImp> dividePixel(PixelImp mainPixel, double focusLength, double apertureSize, int dofRayBeamSize){
        //caculate the width and hight of the pixel
        double pixWidth = mainPixel.aPoint.distance(mainPixel.bPoint);
        double pixHeight = mainPixel.aPoint.distance(mainPixel.dPoint);
        //save the vectors that shift a point half way to right and to down
        Vector halfWidthRightShifter = _vRight.scale(pixWidth / 2d);
        Vector halfHeightDownShifter = _vUp.scale(pixHeight / 2d);
        //find the 5 new points on the pixel we will shoot rays from
        Point3D abMiddlePoint = mainPixel.aPoint.add(halfWidthRightShifter);
        Point3D dcMiddlePoint = mainPixel.dPoint.add(halfWidthRightShifter);
        Point3D adMiddlePoint = mainPixel.aPoint.add(halfHeightDownShifter);
        Point3D bcMiddlePoint = mainPixel.bPoint.add(halfHeightDownShifter);
        Point3D pixCenterPoint = abMiddlePoint.add(halfHeightDownShifter);

        //make 5 ray beams by the points found
        Ray abMiddle = new Ray( abMiddlePoint.subtract(_p0),_p0);
        Ray dcMiddle = new Ray( dcMiddlePoint.subtract(_p0),_p0);
        Ray adMiddle = new Ray( adMiddlePoint.subtract(_p0),_p0);
        Ray bcMiddle = new Ray(bcMiddlePoint.subtract(_p0),_p0);
        Ray pixCenter = new Ray( pixCenterPoint.subtract(_p0),_p0);

        //the rank grows times 4
        int subdivisionRank = mainPixel.getRank() * 4;
        ArrayList<PixelImp> subPixels = new ArrayList<PixelImp>();
        subPixels.add(new PixelImp(mainPixel.aPoint, mainPixel.aCornerRays.rayBeam, abMiddlePoint, abMiddle, pixCenterPoint, pixCenter, adMiddlePoint, adMiddle,subdivisionRank));
        subPixels.add(new PixelImp(abMiddlePoint, abMiddle, mainPixel.bPoint, mainPixel.bCornerRays.rayBeam, bcMiddlePoint, bcMiddle, pixCenterPoint, pixCenter, subdivisionRank));
        subPixels.add(new PixelImp(adMiddlePoint, adMiddle, pixCenterPoint, pixCenter, dcMiddlePoint, dcMiddle, mainPixel.dPoint, mainPixel.dCornerRays.rayBeam, subdivisionRank));
        subPixels.add(new PixelImp(pixCenterPoint, pixCenter, bcMiddlePoint, bcMiddle, mainPixel.cPoint, mainPixel.cCornerRays.rayBeam, dcMiddlePoint, dcMiddle,subdivisionRank));
        return subPixels;
    }









    }
