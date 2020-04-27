package elements;

import primitives.*;

import static primitives.Util.isZero;

public class Camera
{
    Point3D _p0;
    Vector _vUp;
    Vector _vTo;
    Vector _vRight;

    /**
     *
     * @return the center of the camera
     */
    public Point3D get_p0() {
        return _p0;
    }
    /**
     *
     * @return the axis Y vector
     */
    public Vector get_vUp() {
        return _vUp;
    }

    /**
     *
     * @return the axis z vector
     */
    public Vector get_vTo() {
        return _vTo;
    }

    /**
     *
     * @return the axis x vector
     */
    public Vector get_vRight() {
        return _vRight;
    }

    /**
     * constractor
     * @param _p0
     * @param _vUp
     * @param _vTo
     * @throws IllegalArgumentException
     */
    public Camera(Point3D _p0, Vector _vTo, Vector _vUp)throws IllegalArgumentException
    {
       if(!(isZero( _vUp.dotProduct(_vTo))))
        {
            throw new IllegalArgumentException("the vectors are not orthogonal");
        }
       this._vUp = _vUp.normalized();
       this._vTo = _vTo.normalized();
       this._vRight = this._vTo.crossProduct(this._vUp).normalized();
       this._p0 =_p0;
    }

    /**
     *
     * @param nX
     * @param nY
     * @param j
     * @param i
     * @param screenDistance
     * @param screenWidth
     * @param screenHeight
     * @return the ray that go through a specific pixle
     */
    public Ray constructRayThroughPixel (int nX, int nY, int j, int i, double screenDistance, double screenWidth, double screenHeight)
    {
        if (isZero(screenDistance))
        {
            throw new IllegalArgumentException("distance cannot be 0");
        }
        //Pc = P0 + d∙Vto
        Point3D Pc = _p0.add(_vTo.scale(screenDistance));
        //Ratio (pixel width & height)
        double Ry = screenHeight/nY;
        double Rx = screenWidth/nX;

        double yi =  ((i - nY/2d)*Ry + Ry/2d);
        double xj=   ((j - nX/2d)*Rx + Rx/2d);

        Point3D Pij = Pc;

        if (! isZero(xj))
        {
            Pij = Pij.add(_vRight.scale(xj));
        }
        if (! isZero(yi))
        {
            Pij = Pij.add(_vUp.scale(-yi));
        }

        Vector Vij = Pij.subtract(_p0);

        return new Ray(Vij.normalized(),_p0);
    }

}
