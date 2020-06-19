package geometries;

import primitives.*;

/**
 * interface of all geomtry. all geomtry inherit him
 */
public abstract class Geometry implements Intersectable
{
   /**
    * value of geometry
    */
   protected Color _emmission;
   protected Material _material;

   /**
    * constractor
    * @param _emmission
    * @param _material
    */
   public Geometry(Color _emmission, Material _material) {
      this._emmission = _emmission;
      this._material = _material;
   }

   /**
    * constractor
    * @param _emmission
    */
   public Geometry(Color _emmission)
   {
      this(_emmission,new Material(0,0,0));

   }

   /**
    * difullt constractor
    */
   public Geometry() {
      this(Color.BLACK);
   }
   /**
    * getter of material
    * @return
    */
   public Material get_material() {
      return _material;
   }

   /**
    * getter of emission
    * @return
    */
   public Color get_emmission() {
      return _emmission;
   }



   /**
    * return the normal in the point
    * @param p
    * @return
    */
   public abstract Vector getNormal (Point3D p);
}
