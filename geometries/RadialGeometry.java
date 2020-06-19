package geometries;

import primitives.Color;
import primitives.Material;

/**
 * abstract class to define radial geometry' contains a radius
 */
public abstract class RadialGeometry extends Geometry
{
    /**
     * radial geometry value
     */
    protected double _radius;

    /**
     *A constractor that gets radius
     * @param r
     */
    RadialGeometry(double r)
    {
        _radius= r;
    }

    /**
     * constructor with color
     * @param r
     * @param c
     */
    RadialGeometry(double r, Color c)
    {
        this(r);
        _emmission=c;
    }

    /**
     * constructor with color and material
     * @param r
     * @param c
     * @param material
     */
    RadialGeometry(double r, Color c, Material material)
    {
        this(r,c);
        _material=material;
    }

    /**
     *A constractor that gets Radial Geometry
     * @param radGeo
     */
    RadialGeometry(RadialGeometry radGeo)
    {
        _radius= radGeo.getRadius();
    }

    /**
     *
     * @return the radius
     */
    public double getRadius() {
        return _radius;
    }
}
