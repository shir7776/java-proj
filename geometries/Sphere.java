package geometries;
import static java.lang.StrictMath.sqrt;
import static primitives.Util.*;

import primitives.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *class to define a sphere
 */
public class Sphere extends RadialGeometry
{
    /**
     *sphere value
     */
    protected Point3D _center;

    /**
     *
     * @return point3D of the sphere
     */
    public Point3D get_center()

    {
        return _center;
    }
    public @Override String toString()
    {
        return String.format("sphere: point center: {}", _center.toString());
    }

    /**
     *A constractor that gets point3D
     * @param p
     */
    public Sphere(Point3D p, double radus)
    {
        super(radus);
        _center = new Point3D(p);
    }

    /**
     * constructor with color
     * @param p
     * @param radus
     * @param c
     */
    public Sphere(Point3D p, double radus,Color c)
    {
        this(p,radus,c,new Material(0,0,0));
    }

    /**
     * constructor with color and material
     * @param p
     * @param radus
     * @param c
     * @param material
     */
    public Sphere(Point3D p, double radus,Color c,Material material)
    {
        super(radus,c,material);
        _center = new Point3D(p);
    }

    /**
     * constractor EZER
     * @param c
     * @param material
     * @param radus
     * @param p
     */
    public Sphere(Color c,Material material, double radus,Point3D p)
    {
        this( p,  radus, c, material);
    }

    /**
     * a function to find normal in a given point
     * @param p
     * @return
     */
    @java.lang.Override
    public Vector getNormal(Point3D p)
    {
        return new Vector(p.subtract(_center).normalize());
    }

    /**
     * a func to find intersection point between a ray and a sphere
     * @param ray
     * @return
     */
    @java.lang.Override
//    public List<GeoPoint> findIntsersections(Ray ray)
//    {
//        Point3D p0 = ray.get_p0();
//        Vector v = ray.get_dir();
//        Vector u;
//            try {
//        u = _center.subtract(p0);
//    } catch (IllegalArgumentException e) {
//        return List.of(new GeoPoint(this,(ray.getPoint(_radius))));
//    }
//        double tm = alignZero(v.dotProduct(u));
//        double dSquared;
//            if (tm == 0){
//        dSquared = u.lengthSquared();
//    }
//            else{
//        dSquared = u.lengthSquared() - tm*tm;
//    }
//        double thSquared = alignZero(_radius * _radius - dSquared);
//            if (thSquared <= 0){
//        return null;
//    }
//        double th = alignZero(Math.sqrt(thSquared));
//            if (th == 0){
//        return null;
//    }
//        double t1 = alignZero(tm - th);
//        double t2 = alignZero(tm + th);
//            if (t1 <= 0 && t2 <= 0){
//        return null;
//    }
//            if (t1 > 0 && t2 > 0){
//        return List.of(new GeoPoint(this,(ray.getPoint(t1))), new GeoPoint(this, (ray.getPoint(t2))));
//    }
//            if (t1 > 0) {
//        return List.of(new GeoPoint(this,(ray.getPoint(t1))));
//    }
//            else {
//                return List.of(new GeoPoint(this, (ray.getPoint(t2))));
//        }
//}
    public List<GeoPoint> findIntsersections(Ray ray)
    {
        Vector u;
        try {

         u = new Vector(this.get_center().subtract(ray.get_p0()));
        }
        catch (IllegalArgumentException e)
        {
            double t=this.getRadius();
            GeoPoint p=  new GeoPoint(this,ray.get_p0().add(ray.get_dir().scale(t)));
            List<GeoPoint> l = new ArrayList<GeoPoint>();
            l.add(p);
            return l;

        }
        double tm =ray.get_dir().dotProduct(u);
        double d =sqrt( u.lengthSquared()-(tm*tm));
        if(d>this.getRadius())
            return null;
        double th = sqrt((this.getRadius()*this.getRadius())-(d*d));

        double t1 =tm+th;
        double t2 = tm-th;
        Point3D p1=null;
        if(t1>0)
            p1 = ray.getPoint(t1);
        Point3D p2=null;
        if(t2>0)
            p2 = ray.getPoint(t2);
        List<GeoPoint> l = new ArrayList<GeoPoint>();
        if(p1==null&& p2==null|| p1.equals(p2))
            return null;
        if(p1!=null&& !p1.equals(ray.get_p0()))
            l.add(new GeoPoint(this,p1));
        if(p2!=null&& !p2.equals(ray.get_p0()))
            l.add(new GeoPoint(this,p2));

        return l;
    }
}
