package elements;
import primitives.*;


public class AmbientLight {
Color Ia;
double Ka;
Color _intensity;

    /**
     * constractor
     * @param c
     * @param ka
     */
    public AmbientLight(Color c, double ka)
    {
        Ka = ka;
        Ia=c;
        _intensity=Ia.scale(Ka);
    }

    /**
     * get intensity
     * @return
     */
    public Color get_intensity()
    {
        return _intensity;
    }
}
