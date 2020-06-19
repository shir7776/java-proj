package geometries;

import primitives.*;
import static primitives.Util.*;

import java.util.ArrayList;
import java.util.List;

/**
 *class to define a plane
 */
public class Plane extends Geometry
{
    /**
     * plane value
     */
    protected Point3D _p;
    protected Vector _normal;

    /**
     *
     * @return the point3D of the plane
     */
    public Point3D get_p()
    {
        return _p;
    }

    /**
     *
     * @return the vector of the plane
     */
    public Vector get_normal()
    {
        return _normal;
    }

    /**
     * A constractor that gets 3 point3D
     * @param x
     * @param y
     * @param z
     */
    public Plane(Point3D x, Point3D y, Point3D z)
    {
        Vector other1 = new Vector( y.subtract(x));
        Vector other2 = new Vector(z.subtract(y));
        _normal = new Vector(other1.crossProduct(other2)).normalize();
        _p = new Point3D(z);

    }

    /**
     * constructor with color
     * @param x
     * @param y
     * @param z
     * @param c
     */
    public Plane(Point3D x, Point3D y, Point3D z,Color c)
    {
        this(x,y,z);
        _emmission=c;

    }

    /**
     * constractor with material
     * @param x
     * @param y
     * @param z
     * @param c
     * @param material
     */
    public Plane(Point3D x, Point3D y, Point3D z,Color c, Material material)
    {
        this(x,y,z,c);
        _material=material;

    }

    /**
     * A constractor that gets point3D and vector normal
     * @param x
     * @param normalVector
     */
    public Plane(Point3D x,Vector normalVector)
    {
        _normal=new Vector(normalVector);
        _p =new Point3D(x);
    }

    /**
     * A constractor that gets point3D and vector normal and color
     * @param x
     * @param normalVector
     * @param c
     */
    public Plane(Point3D x,Vector normalVector,Color c)
    {
        this(x,normalVector);
        _emmission=c;
    }

    /**
     * A constractor that gets point3D and vector normal and color and material
     * @param x
     * @param normalVector
     * @param c
     * @param m
     */
    public Plane(Point3D x,Vector normalVector,Color c,Material m)
    {
        this(x,normalVector,c);
        _material=m;
    }
    @java.lang.Override
    public Vector getNormal (Point3D p)

    {
        return _normal;
    }

    /**
     * Load a function that returns the normal to the plane
     * @return the vector
     */
    public Vector getNormal ()
    {

        return getNormal(_p);
    }

    public @Override String toString()
    {
        return String.format("plane: vector: {}, point: {}",_normal.toString(), _p.toString());
    }

    /**
     * func to find intersection point between plane and a ray
     * @param ray
     * @return
     */
    @Override
    public List<GeoPoint> findIntsersections(Ray ray)
    {
        if(isZero( this._normal.dotProduct(ray.get_dir()))||isZero(this._normal.dotProduct(this.get_p().subtract(ray.get_p0()))))
            return null;
        double t = this._normal.dotProduct(this.get_p().subtract(ray.get_p0()))/this._normal.dotProduct(ray.get_dir());
        if(t<0)
            return null;
        Point3D p=ray.getPoint(t);
        List<GeoPoint> l = new ArrayList<GeoPoint>();
        l.add(new GeoPoint(this,p));
        return l;
    }
}
