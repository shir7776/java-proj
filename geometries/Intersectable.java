package geometries;

import primitives.*;

import java.util.List;

public interface Intersectable {
    /**
     *Ray-Geometry intersections
     * @param ray
     * @return list of Point3D
     */
    List<GeoPoint> findIntsersections(Ray ray);

    /**
     * class for colored point
     */

    public static class GeoPoint
    {
        public Point3D point;
        public Geometry geometry;
        /**
         * constractor
         * @param geometry
         * @param point
         */
        public GeoPoint( Geometry geometry, Point3D point)
        {
            this.geometry=geometry;
            this.point=point;
        }



        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null) return false;
            if (!(obj instanceof GeoPoint)) return false;
            GeoPoint oth = (GeoPoint) obj;
            return geometry.equals(oth.geometry) && point.equals(oth.point);
        }
    }




}
