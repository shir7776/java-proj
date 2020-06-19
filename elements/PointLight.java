package elements;

import geometries.Polygon;
import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

import java.util.ArrayList;
import java.util.Random;

public class PointLight extends LightSource {
    protected Point3D _position;
    protected double _kC;
    protected double _kL;
    protected double _kQ;
//    protected ArrayList<Polygon> rec ;
//    double K_REC_SIZE=10;

    /**
     * construכctor
     * @param _intensity
     * @param _position
     * @param _kC
     * @param _kL
     * @param _kQ
     * @param radios
     */
    public PointLight(Color _intensity,Point3D _position,double _kC, double _kL, double _kQ, double radios) {
        this(_intensity,_position,_kC,_kL,_kQ,10,50);
    }

    public PointLight(Color _intensity,Point3D _position,double _kC, double _kL, double _kQ, double radios,int numRays) {
        super(_intensity, radios,numRays);
        this._position = new Point3D(_position);
        this._kC = _kC;
        this._kL = _kL;
        this._kQ = _kQ;
        double x=_position.get_x().get();
        double y=_position.get_y().get();
        double z=_position.get_z().get();
    }

    /**
     * constructor
     * @param colorIntensity
     * @param _position
     * @param _kC
     * @param _kL
     * @param _kQ
     */
    public PointLight(Color colorIntensity, Point3D _position, double _kC, double _kL, double _kQ ) {
        this(colorIntensity,_position,_kC,_kL,_kQ,10);

   }

    /**
     *Get light source intensity as it reaches a point
     * @param p
     * @return IL
     */
    @Override
    public Color getIntensity(Point3D p) {
        double dsquared = p.distanceSquared(_position);
        double d = p.distance(_position);

        Color IL = _intensity.reduce(_kC + d*_kL +dsquared* _kQ);

        return IL;
    }

    /**
     *Get normalized vector in the direction from light source
     * towards the lighted point
     * @param p
     * @return list of rays that goes from the light in the range of the radious to the intersection point
     */
    @Override
    public ArrayList<Vector> getL(Point3D p) {
        ArrayList<Vector> listv=new ArrayList<>();
        if (p.equals(_position)) {
            return null;
        } else {
            listv.add(p.subtract(_position).normalized());
        }////////////////////////////////////////////////////the old code
        double rang_min=-1;//setting range for X
        double rang_max=1;
        Random rand =  new Random();
        for(int i=0;i<NUM_SHADOW_BEAM;i++)
        {
            double x = rang_min+(rang_max-rang_min)*rand.nextDouble();
            double y=Math.sqrt(1-x*x);//the Y dependant on the value of X
            double randRadius = -radios+(radios-(-radios))*rand.nextDouble();//random number from the radius to scale the X,Y varaible
            x=x*randRadius;
            y=y*randRadius;
            Vector temp=p.subtract(_position);
            listv.add(new Vector(temp.get_head().get_x().get()+x,temp.get_head().get_y().get()+y,temp.get_head().get_z().get()).normalized());//creating the ray with adding the X,Y to the vector
        }
        return listv;

    }
    /**
     *
     * @param point
     * @return double- distance the light from the point
     */
    public double getDistance(Point3D point)
    {
        return point.distance(this._position);
    }
}
