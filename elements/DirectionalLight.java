package elements;

import geometries.Intersectable;
import geometries.Polygon;
import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

import java.util.ArrayList;

import static primitives.Point3D.zero;

public class DirectionalLight extends  LightSource {
    private Vector _direction;
    //private Polygon rec ;
    /**
     * Initialize directional light with it's intensity and direction, direction
     * vector will be normalized.
     *
     * @param colorintensity intensity of the light
     * @param direction      direction vector
     */

    public DirectionalLight(Color colorintensity, Vector direction) {
        super(colorintensity,0);
        _direction = direction.normalized();
    }

    /**
     * @param p the lighted point is not used and is mentioned
     *          only for compatibility with LightSource
     * @return fixed intensity of the directionLight
     */
    @Override
    public Color getIntensity(Point3D p) {
        return _intensity;
    }

    //instead of getDirection()
    @Override
    public ArrayList<Vector> getL(Point3D p) {
        ArrayList<Vector> v= new  ArrayList<Vector>();
         v.add(_direction);
         return v;
    }

    /**
     *
     * @param point
     * @return double- distance the light from the point
     */
    public double getDistance(Point3D point)
    {
        return Double.POSITIVE_INFINITY;
    }
}
