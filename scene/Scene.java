package scene;

import elements.AmbientLight;
import elements.Camera;
import elements.LightSource;
import geometries.Geometries;
import geometries.Intersectable;
import primitives.Color;

import java.util.LinkedList;
import java.util.List;

public class Scene {
    /**
     * value scene
     */
    String _name;
    Color _background;
    AmbientLight _ambientLight;
    Geometries _geometries;
    Camera _camera;
    double _distance;
    List<LightSource> _lights;
    double _focusLength=100;
    /**the size of the camera aperture - determines how blur the object not in focus would be (zero by default)*/
    double _apertureSize=5;
    /**the size of rayBeam - determines how smooth blur the object not in focus would be*/
    int _dofRayBeamSize=0;
    // *****************
    /**
     * get lights
     *
     * @return
     */
    public List<LightSource> get_lights() {
        return _lights;
    }

    /**
     * constructor
     * @param _name
     */
    public Scene(String _name) {
        this._name = _name;
        _geometries=new Geometries();
        _lights=new LinkedList<LightSource>();
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
    /**
     * @return aperture size - how blur the unfocused objects are
     */
    public double getApertureSize() {
        return _apertureSize;
    }

    /**
     * @return focus length of the scene - how far the focused objects would be
     */
    public double getFocusLength() {
        return _focusLength;
    }

    /**
     *
     * @return the amount of rays for the unfocused blur
     */
    public int getdofRayBeamSize() {
        return _dofRayBeamSize;
    }
    /**
     * add liight to the list light
     * @param lights
     */
    public void addLights(LightSource... lights)
    {
        for(int i =0;i<lights.length;i++)
        {
            _lights.add(lights[i]);

        }
    }

}
