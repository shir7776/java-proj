package renderer;

import geometries.Intersectable;
import primitives.Point3D;
import primitives.Ray;
import scene.Scene;
import elements.*;
import primitives.*;

import java.util.List;

public class Render {
    ImageWriter _imageWriter;
    Scene _scene;

    /**
     * constructor
     * @param _scene
     */
    public Render(Scene _scene) {
        this._scene = _scene;
    }

    /**
     * constructor
     * @param imageWriter
     * @param scene
     */
    public Render(ImageWriter imageWriter, Scene scene) {
        this._imageWriter = imageWriter;
        this._scene=   scene;
    }

    /**
     * get_scene
     * @return _scene
     */
    public Scene get_scene() {
        return _scene;
    }

    /**
     * Filling the buffer according to the geometries that are in the scene.
     * This function does not creating the picture file, but create the buffer pf pixels
     *
     */
    public void renderImage() {
        java.awt.Color background = _scene.get_background().getColor();
        Camera camera= _scene.get_camera();
        Intersectable geometries = _scene.get_geometries();
        double  distance = _scene.get_distance();

        //width and height are the number of pixels in the rows
        //and columns of the view plane
        int width = (int) _imageWriter.getWidth();
        int height = (int) _imageWriter.getHeight();

        //Nx and Ny are the width and height of the image.
        int Nx = _imageWriter.getNx();
        int Ny = _imageWriter.getNy();
        Ray ray;
        for (int row = 0; row < Ny; row++) {
            for (int column = 0; column < Nx; column++) {
                ray = camera.constructRayThroughPixel(Nx, Ny, row, column, distance, width, height);
                List<Point3D> intersectionPoints = _scene.get_geometries().findIntsersections(ray);
                if (intersectionPoints.size() == 0) {
                    _imageWriter.writePixel(column, row, background);
                } else {
                    Point3D closestPoint = getClosestPoint(intersectionPoints);
                    _imageWriter.writePixel(column, row, calcColor(closestPoint).getColor());
                }
            }
        }
    }

    /**
     * Finding the closest point to the P0 of the camera.
     * @param  intersectionPoints list of points, the function should find from
     * this list the closet point to P0 of the camera in the scene.
     * @return  the closest point to the camera
     */

    private Point3D getClosestPoint(List<Point3D> intersectionPoints)
    {
        Point3D result = null;
        double mindist = Double.MAX_VALUE;

        Point3D p0 = this._scene.get_camera().get_p0();

        for (Point3D pt: intersectionPoints )
        {
            double distance = p0.distance(pt);
            if (distance < mindist)
            {
                mindist= distance;
                result =pt;
            }
        }
        return  result;
    }

    /**
     * Printing the grid with a fixed interval between lines
     * @param interval The interval between the lines.
     */
    public void printGrid(int interval, Color colorsep)
    {
        double rows = this._imageWriter.getNx();
        double collumns = _imageWriter.getNy();
        //Writing the lines.
        for (int col = 0; col < collumns; col++)
        {
            for (int row = 0; row < rows; row++)
            {
                if (col % interval == 0 || row % interval == 0)
                {
                    _imageWriter.writePixel(row, col, colorsep.getColor());
                }
            }
        }
    }

    /**
     * writing to image
     */
    public void writeToImage()
    {
        _imageWriter.writeToImage();
    }

    /**
     * Calculate the color intensity in a point
     * @param point the point for which the color is required
     * @return the color intensity
     */
    private Color calcColor(Point3D point)
    {
        return _scene.get_ambientLight().get_intensity();
    }

}
