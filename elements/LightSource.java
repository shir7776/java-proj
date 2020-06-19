package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

import java.util.ArrayList;

/**
 * Interface for common actions of light sources
 */
public abstract class LightSource extends Light {

    double radios;
    int NUM_SHADOW_BEAM;

    public LightSource(Color _intensity, double radios) {
        this(_intensity,radios,50);
    }

    public LightSource(Color _intensity, double radios,int numRays) {
        super(_intensity);
        this.radios = radios;
        NUM_SHADOW_BEAM = numRays;
    }
   abstract public Color getIntensity(Point3D p);
    abstract public ArrayList<Vector> getL(Point3D p);

    /**
     * returns the distance between  given point and the light source
     * @param point
     * @return
     */
    abstract public double getDistance(Point3D point);





}


