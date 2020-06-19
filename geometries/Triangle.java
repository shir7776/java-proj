package geometries;

import primitives.*;

import java.util.List;

import static primitives.Util.isZero;

/**
 *class to define a triangle
 */
public class Triangle extends Polygon
{
    /**
     *A constractor that gets 3 point3D
     * @param a
     * @param b
     * @param c
     */
    public Triangle(Point3D a,Point3D b,Point3D c)
    {
        super(a,b,c);
    }

    /**
     * constractor whit color
     * @param a
     * @param b
     * @param c
     * @param col
     */
    public Triangle(Point3D a,Point3D b,Point3D c,Color col)
    {
        this(a,b,c,col,new Material(0,0,0));
    }

    /**
     * constractor whit color and matriel
     * @param a
     * @param b
     * @param c
     * @param col
     * @param material
     */
    public Triangle(Point3D a,Point3D b,Point3D c,Color col,Material material)
    {
        super(col,material,a,b,c);
    }

    /**
     * constructor EZER
     * @param col
     * @param material
     * @param a
     * @param b
     * @param c
     */
    public Triangle(Color col,Material material, Point3D a,Point3D b,Point3D c)
    {
        this( a, b, c, col, material);
    }
    public @Override String toString ()
    {
        return String.format("point 1: {}\npoint2: {}\npoint3: {})", _vertices.get(0),_vertices.get(1),_vertices.get(2));
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
        //return null;
    }

    /**
     * a func to find intersection with a triangle
     * @param ray
     * @return
     */
    @java.lang.Override
    public List<GeoPoint> findIntsersections(Ray ray)
    {
        List<GeoPoint> intersections = _plane.findIntsersections(ray);
        if (intersections == null)
            return null;

        Point3D p0 = ray.get_p0();
        Vector v = ray.get_dir();

        Vector v1 = _vertices.get(0).subtract(p0);
        Vector v2 = _vertices.get(1).subtract(p0);
        Vector v3 = _vertices.get(2).subtract(p0);

        double s1 = v.dotProduct(v1.crossProduct(v2));
        if (isZero(s1)) return null;
        double s2 = v.dotProduct(v2.crossProduct(v3));
        if (isZero(s2)) return null;
        double s3 = v.dotProduct(v3.crossProduct(v1));
        if (isZero(s3)) return null;

        return ((s1 > 0 && s2 > 0 && s3 > 0) || (s1 < 0 && s2 < 0 && s3 < 0)) ? intersections : null;
    }
}
