package unittests;

import org.junit.Test;

import elements.*;
import geometries.*;
import primitives.*;
import renderer.ImageWriter;
import renderer.Render;
import scene.Scene;

/**
 * Test rendering abasic image
 *
 * @author shir and hodaya
 */
public class RenderTests {

    /**
     * Produce a scene with basic 3D model and render it into a jpeg image with a
     * grid
     */
    @Test
    public void basicRenderTwoColorTest() {
        Scene scene = new Scene("Test scene");
        scene.set_camera(new Camera(Point3D.zero, new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.set_distance(100);
        scene.set_background(new Color(75, 127, 90));
        scene.set_ambientLight(new AmbientLight(new Color(255, 191, 191), 1));

        scene.addGeometries(new Sphere(new Point3D(0, 0, 100),50));

        scene.addGeometries(
                new Triangle(new Point3D(100, 0, 100), new Point3D(0, 100, 100), new Point3D(100, 100, 100)),
                new Triangle(new Point3D(100, 0, 100), new Point3D(0, -100, 100), new Point3D(100, -100, 100)),
                new Triangle(new Point3D(-100, 0, 100), new Point3D(0, 100, 100), new Point3D(-100, 100, 100)),
                new Triangle(new Point3D(-100, 0, 100), new Point3D(0, -100, 100), new Point3D(-100, -100, 100),new Color(java.awt.Color.blue)));

        ImageWriter imageWriter = new ImageWriter("base render test", 500, 500, 500, 500);
        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.printGrid(50, new Color(java.awt.Color.YELLOW));
        render.writeToImage();
    }

    /**
     * Produce a scene with basic 3D model and render it into a jpeg image with
     * colorful triangles
     */
    @Test
    public void basicRenderMultiColorTest() {
        Scene scene = new Scene("Test scene");
        scene.set_camera(new Camera(Point3D.zero, new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.set_distance(100);
        scene.set_background(Color.BLACK);
        scene.set_ambientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.2));

        scene.addGeometries(new Sphere( new Point3D(0, 0, 100),50,new Color(java.awt.Color.red)));

        scene.addGeometries(
                new Triangle(
                        new Point3D(100, 0, 100), new Point3D(0, 100, 100), new Point3D(100, 100, 100),new Color(java.awt.Color.cyan)),      // lower right
                new Triangle(
                        new Point3D(100, 0, 100), new Point3D(0, -100, 100), new Point3D(100, -100, 100),new Color(java.awt.Color.pink)),    // upper right
                new Triangle(
                        new Point3D(-100, 0, 100), new Point3D(0, 100, 100), new Point3D(-100, 100, 100),new Color(java.awt.Color.darkGray)),    // lower left
                new Triangle(
                        new Point3D(-100, 0, 100), new Point3D(0, -100, 100), new Point3D(-100, -100, 100),new Color(java.awt.Color.GREEN))); // upper left

        ImageWriter imageWriter = new ImageWriter("color render test", 500, 500, 500, 500);
        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.printGrid(50, new Color(java.awt.Color.WHITE));
        render.writeToImage();
    }
}
