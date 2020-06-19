package primitives;

public class Material
{
    /**
     * material value
     */
    private double _kD;
    private double _kS;
    private int _nShininess;
    double _kT;
    double _kR;

    /**
     * constractor
     * @param _kD
     * @param _kS
     * @param _nShininess
     * @param _kT
     * @param _kR
     */
    public Material(double _kD, double _kS, int _nShininess, double _kT, double _kR) {
        this._kD = _kD;
        this._kS = _kS;
        this._nShininess = _nShininess;
        this._kT = _kT;
        this._kR = _kR;
    }

    /**
     * getter for constanat tranperancy parameter
     * @return
     */
    public double get_kT() {
        return _kT;
    }

    /**
     * getter for constanat reflection parameter
     * @return
     */
    public double get_kR() {
        return _kR;
    }

    /**
     * constructor
     * @param _kD
     * @param _kS
     * @param _nShininess
     */
    public Material(double _kD, double _kS, int _nShininess) {
        this(_kD,_kS,_nShininess,0,0);
    }

    /**
     *getter for constanat diffuse parameter
     * @return kd
     */
    public double getKd() {
        return _kD;
    }

    /**
     *getter for constanat specular parameter
     * @return ks
     */
    public double getKs() {
        return _kS;
    }

    /**
     *getter for constanat shininess parameter
     * @return shinines
     */
    public int getnShininess() {
        return _nShininess;
    }
}
