package geometries;
import primitives.*;

import java.util.List;

/**
 *class cylinder to define a cylinder
 */
public class Cylinder extends Tube
{
    /**
     *cylinder value
     */
    double _height;

    /**
     * constructor
     * @param r
     * @param radius
     * @param c
     */
    public Cylinder(Ray r, double radius,Color c) {
        super(r, radius,c);
    }

    /**
     * constructor
     * @param r
     * @param radius
     * @param c
     * @param material
     */
    public Cylinder(Ray r, double radius,Color c,Material material) {
        super(r, radius,c,material);
    }

    /**
     *
     * @return the hight of the cylinder
     */
    public double get_height()
    {
        return _height;
    }


    /**
     * a function to find normal in a given point
     * @param point
     * @return
     */
    @Override
    public Vector getNormal(Point3D point)
    {
        return super.getNormal(point);
    }

    /**
     *A constractor that receives height ,ray and radius
     * @param heightX
     * @param axisRayX
     * @param radius
     */
    public Cylinder(double heightX,Ray axisRayX,double radius)
    {
        super(axisRayX,radius);
        _height =heightX;
    }



    /**
     * tostring func
     * @return
     */
    public @Override String toString()
    {
        return String.format("Cylinder: height", _height);
    }
    @Override
    public List<GeoPoint> findIntsersections(Ray ray)
    {return null;}


}
