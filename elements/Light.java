package elements;

import primitives.Color;

public abstract class Light
{
    /**
     * _intensity value, set to protected
     */
    protected Color _intensity;

    /**
     *constrator
     * @param _intensity
     */
    public Light(Color _intensity) {
        this._intensity = _intensity;
    }

    /**
     *
     * @return IL
     */
    public Color getIntensity() {
        return new Color(_intensity);
    }
}
