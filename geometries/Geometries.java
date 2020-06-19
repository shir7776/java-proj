package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.ArrayList;
import java.util.List;

public class Geometries implements Intersectable
{
    protected ArrayList<Intersectable> interList;

    /**
     *
     * @return list of inersection points
     */
    public List<Intersectable> getInterList() {
        return interList;
    }

    /**
     * constructor
     */
    public Geometries() {
        this.interList = new ArrayList<Intersectable>();//we chose array list because we need to go throgh the list easyly and not add and delete objects frequently
    }
    public Geometries(Intersectable... geometries)
    {
        this.add(geometries);
    }

    /**
     * add geometries to list of geometries
     * @param geometries
     */
    public void add(Intersectable... geometries)
    {
        for (int i=0;i<geometries.length;i++)
        {
            interList.add(geometries[i]);
        }
    }

    /**
     * intsersections for all the geometries in the list
     * @param ray
     * @return list of point3D, for all intsersections
     */
    @Override
    public List<GeoPoint> findIntsersections(Ray ray)
    {

        List<GeoPoint> l = new ArrayList<GeoPoint>();
        List<GeoPoint> l1 = new ArrayList<GeoPoint>();
        for(int i=0;i<this.interList.size();i++)
        {
            l1 =  this.interList.get(i).findIntsersections(ray);
            if(l1!=null)
                for(int j=0;j<l1.size();j++)//copy the list that get
                    l.add(l1.get(j));

        }
        if(l==null)
            return null;
        return l;

    }
}
