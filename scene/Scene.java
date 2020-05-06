package scene;

import elements.AmbientLight;
import elements.Camera;
import geometries.Geometries;
import geometries.Intersectable;
import primitives.Color;

public class Scene {
    String _name;
    Color _background;
    AmbientLight _ambientLight;
    Geometries _geometries;
    Camera _camera;
    double _distance;

    /**
     * constructor
     * @param _name
     */
    public Scene(String _name) {
        this._name = _name;
        _geometries=new Geometries();
    }

    /**
     * get name
     *
     * @return
     */
    public String get_name() {
        return _name;
    }
    /**
     * get camera
     *
     * @return
     */
    public Camera get_camera() {
        return _camera;
    }
    /**
     * get background
     *
     * @return
     */
    public Color get_background() {
        return _background;
    }

    /**
     * get ambientLight
     *
     * @return
     */
    public AmbientLight get_ambientLight() {
        return _ambientLight;
    }
    /**
     * get geometries
     *
     * @return
     */

    public Geometries get_geometries() {
        return _geometries;
    }
    /**
     * get distance
     *
     * @return
     */
    public double get_distance() {
        return _distance;
    }

    /**
     * set background
     * @param _background
     */
    public void set_background(Color _background) {
        this._background = _background;
    }

    /**
     * set distance
     * @param _distance
     */
    public void set_distance(double _distance) {
        this._distance = _distance;
    }

    /**
     * set camera
     * @param _camera
     */
    public void set_camera(Camera _camera) {
        this._camera = _camera;
    }

    /**
     * set ambientLight
     * @param _ambientLight
     */
    public void set_ambientLight(AmbientLight _ambientLight) {
        this._ambientLight = _ambientLight;
    }

    /**
     * add geometries to geomtries list
     * @param geometries
     */
    public void addGeometries(Intersectable... geometries)
    {
        for (int i=0;i<geometries.length;i++)
        {
            _geometries.add(geometries[i]);
        }

    }

}
