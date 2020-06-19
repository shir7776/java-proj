package geometries;

import primitives.*;
import primitives.Util;

import java.util.List;

/**
 *class to define a tube
 */
public class Tube extends RadialGeometry
{


    /**
     * tube value
     */
    protected Ray _axisRay;

    /**
     *
     * @return the ray in the tube
     */
    public Ray get_axisRay()
    {
        return _axisRay;
    }

    /**
     * A constractor that gets ray and radius
     * @param r
     * @param radius
     */
    public Tube(Ray r,double radius)
    {
        super(radius);
        _axisRay =new Ray(r);
        
    }

    /**
     * constractor whit color
     * @param r
     * @param radius
     * @param c
     */
    public Tube(Ray r,double radius,Color c)
    {
        this(r,radius,c,new Material(0,0,0));
    }

    /**
     * constractor whit color and material
     * @param _axisRay
     * @param radius
     * @param c
     * @param material
     */
    public Tube(Ray _axisRay,double radius,Color c,Material material) {
        super(radius,c,material);
        this._axisRay = _axisRay;

    }

    /**
     * a function to find normal in a given point
     * @param p
     * @return
     */
    @Override
    public Vector getNormal(Point3D p) {

        Point3D p0 = _axisRay.get_p0();
        Vector v = _axisRay.get_dir();
        //t = v (P – P0)
        double t = p.subtract(p0).dotProduct(v);
        //System.out.println(t);
        // O = P0 + tv
        Point3D o=null;
        if (!Util.isZero(t))// if it's close to 0, we'll get ZERO vector exception
            o = p0.add(v.scale(t));
        //System.out.println(o);
        Vector n = p.subtract(o).normalize();
        //System.out.println(n);
        return n;

    }

    /**
     * find Intsersections method
     * @param ray
     * @return
     */
    public List<GeoPoint> findIntsersections(Ray ray)
    {
        return null;
    }
}
