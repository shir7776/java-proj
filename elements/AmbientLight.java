package elements;
import primitives.*;


public class AmbientLight extends Light{
Color Ia;
double Ka;


    /**
     * constractor
     * @param c
     * @param ka
     */
    public AmbientLight(Color c, double ka)
    {
        super(c.scale(ka));
        Ka = ka;
        Ia=c;
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
