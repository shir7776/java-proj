package elements;

import geometries.Intersectable;
import geometries.Polygon;
import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

import java.util.ArrayList;

import static primitives.Point3D.zero;

public class SpotLight extends PointLight{
     Vector _direction;



    /**
     * constractor
     * @param colorIntensity
     * @param _position
     * @param _direction
     * @param _kC
     * @param _kL
     * @param _kQ
     */
    public SpotLight(Color colorIntensity, Point3D _position, Vector _direction, double _kC, double _kL, double _kQ) {
        this(colorIntensity,_position,_direction,_kC,_kL,_kQ,10);
        //this._direction = new Vector(_direction).normalized();
//        rec = new ArrayList<>();
//        Vector orthogonal= new Vector(1,0,(_direction.get_head().get_x().get())/_direction.get_head().get_z().get()).normalized().scale(K_REC_SIZE);
//        Vector orthogonal2= new Vector(0,1,(_direction.get_head().get_y().get())/_direction.get_head().get_z().get()).normalized().scale(K_REC_SIZE);
//        rec.add(new Polygon(new Point3D(_position.add(orthogonal)),new Point3D(_position.add(orthogonal2)),new Point3D(_position.add(orthogonal.scale(-1))),new Point3D(_position.add(orthogonal2.scale(-1)))));
     }



    /**
     * constractor
     * @param _intensity
     * @param _position
     * @param _kC
     * @param _kL
     * @param _kQ
     * @param radios
     * @param _direction
     */
    public SpotLight(Color _intensity, Point3D _position,Vector _direction, double _kC, double _kL, double _kQ, double radios) {
        this(_intensity,_position,_direction,_kC,_kL,_kQ,radios,50);
    }

    public SpotLight(Color _intensity, Point3D _position,Vector _direction, double _kC, double _kL, double _kQ, double radios,int numRays) {
        super(_intensity, _position, _kC, _kL, _kQ, radios,numRays);
        this._direction = new Vector(_direction).normalized();
    }


    /**
     * @return spotlight intensity
     */
    @Override
    public Color getIntensity(Point3D p) {

        Color pointLightIntensity = super.getIntensity(p);
        double projection = _direction.dotProduct(getL(p).get(0));

        Color IL = pointLightIntensity.scale(Math.max(0,projection));
        return IL;
    }

//    /**
//     *Get normalized vector in the direction from light source
//     * towards the lighted point
//     * @param p
//     * @return
//     */
//    @Override
////    public Vector getL(Point3D p) {
////        return  p.subtract(_position).normalize();
////    }
//
    /**
     *
     * @param point
     * @return double- distance the light from the point
     */
    public double getDistance(Point3D point)
    {
        return super.getDistance(point);
    }

}
