
/**
 *
 */
package unittests;

import geometries.Plane;
import geometries.Polygon;
import org.junit.Test;

import elements.*;
import geometries.Sphere;
import geometries.Triangle;
import primitives.*;
import renderer.*;
import scene.Scene;

import java.util.Random;

/**
 * Tests for reflection and transparency functionality, test for partial shadows
 * (with transparency)
 *
 * @author shir and hodaya
 *
 */
public class ReflectionRefractionTests {

	/**
	 * Produce a picture of a sphere lighted by a spot light
	 */
	@Test
	public void twoSpheres() {
		Scene scene = new Scene("Test scene");
		scene.set_camera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
		scene.set_distance(1000);
		scene.set_background(Color.BLACK);
		scene.set_ambientLight(new AmbientLight(Color.BLACK, 0));

		scene.addGeometries(
				new Sphere(new Color(java.awt.Color.BLUE), new Material(0.4, 0.3, 100, 0.3, 0), 50,
						new Point3D(0, 0, 50)),
				new Sphere(new Color(java.awt.Color.RED), new Material(0.5, 0.5, 100), 25, new Point3D(0, 0, 50)));

		scene.addLights(new SpotLight(new Color(1000, 600, 0), new Point3D(-100, 100, -500), new Vector(-1, 1, 2), 1,
				0.0004, 0.0000006));

		ImageWriter imageWriter = new ImageWriter("twoSpheres", 150, 150, 500, 500);
		Render render = new Render(imageWriter, scene);

		render.renderImage();
		render.writeToImage();
	}

	/**
	 * Produce a picture of a sphere lighted by a spot light
	 */
	@Test
	public void twoSpheresOnMirrors() {
		Scene scene = new Scene("Test scene");
		scene.set_camera(new Camera(new Point3D(0, 0, -10000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
		scene.set_distance(10000);
		scene.set_background(Color.BLACK);
		scene.set_ambientLight(new AmbientLight(new Color(255, 255, 255), 0.1));

		scene.addGeometries(
				new Sphere(new Color(0, 0, 100), new Material(0.25, 0.25, 20, 0.5, 0), 400, new Point3D(-950, 900, 1000)),
				new Sphere(new Color(100, 20, 20), new Material(0.25, 0.25, 20), 200, new Point3D(-950, 900, 1000)),
				new Triangle(new Color(20, 20, 20), new Material(0, 0, 0, 0, 1), new Point3D(1500, 1500, 1500),
						new Point3D(-1500, -1500, 1500), new Point3D(670, -670, -3000)),
				new Triangle(new Color(20, 20, 20), new Material(0, 0, 0, 0, 0.5), new Point3D(1500, 1500, 1500),
						new Point3D(-1500, -1500, 1500), new Point3D(-1500, 1500, 2000)));

		scene.addLights(new SpotLight(new Color(1020, 400, 400),  new Point3D(-750, 750, 150),
				new Vector(-1, 1, 4), 1, 0.00001, 0.000005));

		ImageWriter imageWriter = new ImageWriter("twoSpheresMirrored", 2500, 2500, 500, 500);
		Render render = new Render(imageWriter, scene);

		render.renderImage();
		render.writeToImage();
	}

	/**
	 * Produce a picture of a two triangles lighted by a spot light with a partially transparent Sphere
	 *  producing partial shadow
	 */
	@Test
	public void trianglesTransparentSphere() {
		Scene scene = new Scene("Test scene");
		scene.set_camera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
		scene.set_distance(1000);
		scene.set_background(Color.BLACK);
		scene.set_ambientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

		scene.addGeometries( //
				new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
						new Point3D(-150, 150, 115), new Point3D(150, 150, 135), new Point3D(75, -75, 150)), //
				new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
						new Point3D(-150, 150, 115), new Point3D(-70, -70, 140), new Point3D(75, -75, 150)), //
				new Sphere(new Color(java.awt.Color.BLUE), new Material(0.2, 0.2, 30, 0.6, 0), // )
						30, new Point3D(60, -50, 50)));

		scene.addLights(new SpotLight(new Color(700, 400, 400), //
				new Point3D(60, -50, 0), new Vector(0, 0, 1), 1, 4E-5, 2E-7));

		ImageWriter imageWriter = new ImageWriter("shadow with transparency", 200, 200, 600, 600);
		Render render = new Render(imageWriter, scene);

		render.renderImage();
		render.writeToImage();
	}


	/**
	 * picture of ours
	 */
	@Test
	public void mickey() {
		Scene scene = new Scene("Test scene");
		scene.set_camera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
		scene.set_distance(500);
		scene.set_background(new Color(255,255,255));
		scene.set_ambientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

		scene.addGeometries( new Sphere(new Color(java.awt.Color.BLUE), new Material(0.2, 0.2, 100, 0.6, 0), // )
				50, new Point3D(0,0,0)),
				new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
						new Point3D(0, 0, -50), new Point3D(5, 12, -50), new Point3D(-5, 12, -50)), //
				new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
						new Point3D(-15, 17, -50), new Point3D(15, 17, -50), new Point3D(0, 35, -50)), //
				new Sphere(new Color(java.awt.Color.BLUE), new Material(0.2, 0.2, 30, 0.6, 0), // )
						30, new Point3D(60, -50, 50)),
				new Sphere(new Color(java.awt.Color.BLUE), new Material(0.2, 0.2, 30, 0.6, 0), // )
						30, new Point3D(-60, -50, 50)),


				new Sphere(new Color(java.awt.Color.BLUE), new Material(0.2, 0.2, 30, 0, 0.1), // )
						10, new Point3D(15, -15, 35)),
				new Sphere(new Color(java.awt.Color.BLUE), new Material(0.2, 0.2, 30, 0, 0.1), // )
						10, new Point3D(-15, -15, 35)),

				new Sphere(Color.BLACK, new Material(0.2, 0.2, 30, 0, 0), // )
						10, new Point3D(-32, -32, -30)),
				new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
						new Point3D(-32, -32, -30), new Point3D(-10, -30, -50), new Point3D(-15, -60, -30)), //
				new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
						new Point3D(-32, -32, -30), new Point3D(-50, -25, -50), new Point3D(-60, -55, -30)),

				new Polygon(Color.BLACK, new Material(0.5, 0.5, 60),new Point3D(550,550,200),new Point3D(-550,550,200),new Point3D(-550,-550,1000),new Point3D(550,-550,1000))
		);

		scene.addLights(//new SpotLight(new Color(700, 400, 400), //
//				new Point3D(60, -50, 0), new Vector(0, 0, 1), 1, 4E-5, 2E-7),
//				new SpotLight(new Color(700, 400, 400), //
//						new Point3D(-60, -50, 0), new Vector(0, 0, 1), 1, 4E-5, 2E-7),
				new SpotLight(new Color(700, 400, 400), //
						new Point3D(0, 0, -200), new Vector(0, 0, 10), 1, 4E-5, 2E-7)
				);

		ImageWriter imageWriter = new ImageWriter("mickey", 200, 200, 600, 600);
		Render render = new Render(imageWriter, scene);

		render.renderImage();
		render.writeToImage();
	}






	/**
	 *another picture of ours
	 */
	@Test
	public void not_transperantmickey() {
		Scene scene = new Scene("Test scene");
		scene.set_camera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
		scene.set_distance(500);
		scene.set_background(new Color(255,255,255));
		scene.set_ambientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

		scene.addGeometries( new Sphere(new Color(java.awt.Color.BLUE), new Material(0.2, 0.2, 100, 0, 0), // )
						50, new Point3D(0,0,0)),
				new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
						new Point3D(0, 0, -50), new Point3D(5, 12, -50), new Point3D(-5, 12, -50)), //
				new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
						new Point3D(-15, 17, -50), new Point3D(15, 17, -50), new Point3D(0, 35, -50)), //
				new Sphere(new Color(java.awt.Color.BLUE), new Material(0.2, 0.2, 30, 0, 0), // )
						30, new Point3D(60, -50, 50)),
				new Sphere(new Color(java.awt.Color.BLUE), new Material(0.2, 0.2, 30, 0, 0), // )
						30, new Point3D(-60, -50, 50)),


				new Sphere(new Color(java.awt.Color.BLUE), new Material(0.2, 0.2, 30, 0, 0), // )
						10, new Point3D(15, -15, -50)),
				new Sphere(new Color(java.awt.Color.BLUE), new Material(0.2, 0.2, 30, 0, 0), // )
						10, new Point3D(-15, -15, -50)),

				new Sphere(Color.BLACK, new Material(0.2, 0.2, 30, 0, 0), // )
						10, new Point3D(-32, -32, -30)),
				new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
						new Point3D(-32, -32, -30), new Point3D(-10, -30, -50), new Point3D(-15, -60, -30)), //
				new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
						new Point3D(-32, -32, -30), new Point3D(-50, -25, -50), new Point3D(-60, -55, -30)),

				new Polygon(Color.BLACK, new Material(0.5, 0.5, 60),new Point3D(550,550,200),new Point3D(-550,550,200),new Point3D(-550,-550,1000),new Point3D(550,-550,1000))
		);

		scene.addLights(//new SpotLight(new Color(700, 400, 400), //
//				new Point3D(60, -50, 0), new Vector(0, 0, 1), 1, 4E-5, 2E-7),
//				new SpotLight(new Color(700, 400, 400), //
//						new Point3D(-60, -50, 0), new Vector(0, 0, 1), 1, 4E-5, 2E-7),
				new SpotLight(new Color(700, 400, 400), //
						new Point3D(0, 0, -200), new Vector(0, 0, 10), 1, 4E-5, 2E-7)
		);

		ImageWriter imageWriter = new ImageWriter("not_transperantmickey", 200, 200, 600, 600);
		Render render = new Render(imageWriter, scene);

		render.renderImage();
		render.writeToImage();
	}




	/**
	 *another picture of ours
	 */
	@Test
	public void abstractmickey() {
		Scene scene = new Scene("Test scene");
		scene.set_camera(new Camera(new Point3D(-950, 0, -1611), new Vector(950, 0, 1611), new Vector(0, -1, 0)));
//		scene.set_camera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
		scene.set_distance(500);
		scene.set_background(new Color(255,255,255));
		scene.set_ambientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

		scene.addGeometries( new Sphere(new Color(java.awt.Color.BLUE), new Material(0.2, 0.2, 100, 0, 0), // )
						50, new Point3D(0,0,0)),
//				new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
//						new Point3D(0, 0, -50), new Point3D(5, 12, -50), new Point3D(-5, 12, -50)), //
//				new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
//						new Point3D(-15, 17, -50), new Point3D(15, 17, -50), new Point3D(0, 35, -50)), //
				new Sphere(new Color(java.awt.Color.BLUE), new Material(0.2, 0.2, 30, 0, 0), // )
						30, new Point3D(60, -50, 10)),
				new Sphere(new Color(java.awt.Color.BLUE), new Material(0.2, 0.2, 30, 0, 0), // )
						30, new Point3D(-60, -50, 10)),

				new Triangle(  //
						new Point3D(11.95, 7.3, -180), new Point3D(2.98, 7.3, -180), new Point3D(9, 15, -180),Color.BLACK),
				new Sphere( // )
						 new Point3D(9.4, 7, -180),2.1,new Color(java.awt.Color.black)),
				new Sphere(  // )
						 new Point3D(5.2, 7, -180),2.1,new Color(java.awt.Color.black)),

				new Sphere(new Color(java.awt.Color.BLUE), new Material(0.2, 0.2, 30, 0, 0), // )
						10, new Point3D(-32, -32, -30)),
				new Triangle(new Color(java.awt.Color.BLUE), new Material(0.5, 0.5, 60), //
						new Point3D(-32, -32, -30), new Point3D(-10, -30, -50), new Point3D(-15, -60, -30)), //
				new Triangle(new Color(java.awt.Color.BLUE), new Material(0.5, 0.5, 60), //
						new Point3D(-32, -32, -30), new Point3D(-50, -25, -50), new Point3D(-60, -55, -30)),

				new Polygon(new Color(31,27,180), new Material(0.5, 0.5, 60),new Point3D(550,550,200),new Point3D(-550,550,200),new Point3D(-550,-550,1000),new Point3D(550,-550,1000))
		);

		scene.addLights(//new SpotLight(new Color(700, 400, 400), //
//				new Point3D(60, -50, 0), new Vector(0, 0, 1), 1, 4E-5, 2E-7),
//				new SpotLight(new Color(700, 400, 400), //
//						new Point3D(-60, -50, 0), new Vector(0, 0, 1), 1, 4E-5, 2E-7),
				new SpotLight(new Color(700, 400, 400), //
						new Point3D(0, 0, -200), new Vector(0, 0, 10), 1, 4E-5, 2E-7,3)
		);

		ImageWriter imageWriter = new ImageWriter("abstractmickey", 200, 200, 6000, 6000);
		Render render = new Render(imageWriter, scene);

		render.renderImage();
		render.writeToImage();
	}



	/**
	 *another picture of ours
	 */
	@Test
	public void eye_mickey() {
		Scene scene = new Scene("Test scene");
//		scene.set_camera(new Camera(new Point3D(-950, 0, -1611), new Vector(950, 0, 1611), new Vector(0, -1, 0)));
		scene.set_camera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
		scene.set_distance(500);
		scene.set_background(new Color(255,255,255));
		scene.set_ambientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

		scene.addGeometries( new Sphere(new Color(197 ,148,103), new Material(0.2, 0.2, 100, 0, 0), // )
						50, new Point3D(0,0,0)),
//				new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
//						new Point3D(0, 0, -50), new Point3D(5, 12, -50), new Point3D(-5, 12, -50)), //
//				new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
//						new Point3D(-15, 17, -50), new Point3D(15, 17, -50), new Point3D(0, 35, -50)), //
				new Sphere(new Color(java.awt.Color.black), new Material(0.2, 0.2, 30, 0, 0), // )
						30, new Point3D(60, -50, 10)),
				new Sphere(new Color(java.awt.Color.black), new Material(0.2, 0.2, 30, 0, 0), // )
						30, new Point3D(-60, -50, 10)),

				new Sphere(  // )
						 new Point3D(15, -15, -53),5,new Color(java.awt.Color.black)),
				new Sphere( new Point3D(-15, -15, -53),5,new Color(java.awt.Color.black)),

				new Triangle(  //
						new Point3D(11.95, 7.3, -180), new Point3D(2.98, 7.3, -180), new Point3D(9, 15, -180),Color.BLACK),
				new Sphere( // )
						new Point3D(9.4, 7, -180),2.1,new Color(java.awt.Color.black)),
				new Sphere(  // )
						new Point3D(5.2, 7, -180),2.1,new Color(java.awt.Color.black)),

				new Sphere(new Color(java.awt.Color.BLUE), new Material(0.2, 0.2, 30, 0, 0), // )
						10, new Point3D(-32, -32, -30)),
				new Triangle(new Color(java.awt.Color.BLUE), new Material(0.5, 0.5, 60), //
						new Point3D(-32, -32, -30), new Point3D(-10, -30, -50), new Point3D(-15, -60, -30)), //
				new Triangle(new Color(java.awt.Color.BLUE), new Material(0.5, 0.5, 60), //
						new Point3D(-32, -32, -30), new Point3D(-50, -25, -50), new Point3D(-60, -55, -30)),

				new Polygon(new Color(31,27,180), new Material(0.5, 0.5, 60),new Point3D(550,550,200),new Point3D(-550,550,200),new Point3D(-550,-550,1000),new Point3D(550,-550,1000))
		);

		scene.addLights(//new SpotLight(new Color(700, 400, 400), //
//				new Point3D(60, -50, 0), new Vector(0, 0, 1), 1, 4E-5, 2E-7),
//				new SpotLight(new Color(700, 400, 400), //
//						new Point3D(-60, -50, 0), new Vector(0, 0, 1), 1, 4E-5, 2E-7),
				new SpotLight(new Color(700, 400, 400), //
						new Point3D(0, 0, -200), new Vector(0, 0, 10), 1, 4E-5, 2E-7)
		);

		ImageWriter imageWriter = new ImageWriter("eye_mickey", 200, 200, 600, 600);
		Render render = new Render(imageWriter, scene);

		render.renderImage();
		render.writeToImage();
	}





	/**
	 *another picture of ours
	 */
	@Test
	public void only_sphere() {
		Scene scene = new Scene("Test scene");
//		scene.set_camera(new Camera(new Point3D(-950, 0, -1611), new Vector(950, 0, 1611), new Vector(0, -1, 0)));
		scene.set_camera(new Camera(new Point3D(0, 0, -500), new Vector(0, 0, 1), new Vector(0, -1, 0)));
		scene.set_distance(500);
		scene.set_background(new Color(255,255,255));
		scene.set_ambientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

		scene.addGeometries( new Sphere(new Color(0 ,0,255), new Material(0.2, 0.2, 100, 0, 0), // )
						50, new Point3D(0,0,0))
//new Polygon(new Color(31,27,180), new Material(0.5, 0.5, 60),new Point3D(550,550,200),new Point3D(-550,550,200),new Point3D(-550,-550,1000),new Point3D(550,-550,1000))
		);

		scene.addLights(//new SpotLight(new Color(700, 400, 400), //
//				new Point3D(60, -50, 0), new Vector(0, 0, 1), 1, 4E-5, 2E-7),
//				new SpotLight(new Color(700, 400, 400), //
//						new Point3D(-60, -50, 0), new Vector(0, 0, 1), 1, 4E-5, 2E-7),
				new SpotLight(new Color(700, 400, 400), //
						new Point3D(0, 0, -200), new Vector(0, 0, 10), 1, 4E-5, 2E-7)
		);

		ImageWriter imageWriter = new ImageWriter("only_sphere", 200, 200, 600, 600);
		Render render = new Render(imageWriter, scene);

		render.renderImage();
		render.writeToImage();

	}
	@Test
	public void abstractmickey2() {
		Scene scene = new Scene("Test scene");
		scene.set_camera(new Camera(new Point3D(-950, 0, -1611), new Vector(950, 0, 1611), new Vector(0, -1, 0)));
//		scene.set_camera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
		scene.set_distance(500);
		scene.set_background(new Color(java.awt.Color.black));
		scene.set_ambientLight(new AmbientLight(new Color(226,46,46), 0.15));

		scene.addGeometries( new Sphere(new Color(255,218,194), new Material(0.2, 0.2, 100, 0, 0), // )
						50, new Point3D(0,0,0)),
//				new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
//						new Point3D(0, 0, -50), new Point3D(5, 12, -50), new Point3D(-5, 12, -50)), //
//				new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
//						new Point3D(-15, 17, -50), new Point3D(15, 17, -50), new Point3D(0, 35, -50)), //
				new Sphere(new Color(java.awt.Color.black), new Material(0.2, 0.2, 30, 0, 0), // )
						30, new Point3D(60, -50, 10)),
				new Sphere(new Color(java.awt.Color.black), new Material(0.2, 0.2, 30, 0, 0), // )
						30, new Point3D(-60, -50, 10)),

				new Triangle(  //
						new Point3D(11.95, 7.3, -180), new Point3D(2.98, 7.3, -180), new Point3D(9, 15, -180),new Color(java.awt.Color.red)),
				new Sphere( // )
						new Point3D(9.4, 7, -180),2.1,new Color(java.awt.Color.red)),
				new Sphere(  // )
						new Point3D(5.2, 7, -180),2.1,new Color(java.awt.Color.red)),

				new Sphere(new Color(java.awt.Color.black), new Material(0.2, 0.2, 30, 0, 0), // )
						10, new Point3D(-32, -32, -30)),
				new Triangle(new Color(java.awt.Color.red), new Material(0.5, 0.5, 60), //
						new Point3D(-32, -32, -30), new Point3D(-10, -30, -50), new Point3D(-15, -60, -30)), //
				new Triangle(new Color(java.awt.Color.red), new Material(0.5, 0.5, 60), //
						new Point3D(-32, -32, -30), new Point3D(-50, -25, -50), new Point3D(-60, -55, -30)),
				//eyes
				new Sphere(  // )
						new Color(java.awt.Color.white),new Material(0.2, 0.2, 30, 0, 0),8,new Point3D(15, -15, -30)),
				new Sphere( new Color(java.awt.Color.white),new Material(0.2, 0.2, 30, 0, 0),8,new Point3D(-15, -15, -30)),
				new Sphere(  // )
						new Color(java.awt.Color.black),new Material(0.2, 0.2, 30, 0, 0),4,new Point3D(15, -15, -35)),
				new Sphere( new Color(java.awt.Color.black),new Material(0.2, 0.2, 30, 0, 0),4,new Point3D(-15, -15, -35)),
				//end eyes
				new Polygon(new Color(java.awt.Color.lightGray), new Material(0.5, 0.5, 60),new Point3D(550,550,200),new Point3D(-550,550,200),new Point3D(-550,-550,1000),new Point3D(550,-550,1000))
		);

		scene.addLights(//new SpotLight(new Color(700, 400, 400), //
//				new Point3D(60, -50, 0), new Vector(0, 0, 1), 1, 4E-5, 2E-7),
//				new SpotLight(new Color(700, 400, 400), //
//						new Point3D(-60, -50, 0), new Vector(0, 0, 1), 1, 4E-5, 2E-7),
				new SpotLight(new Color(700, 400, 400), //
						new Point3D(0, 0, -200), new Vector(0, 0, 10), 1, 4E-5, 2E-7,3)
		);

		ImageWriter imageWriter = new ImageWriter("abstractmickey2", 200, 200, 600, 600);
		Render render = new Render(imageWriter, scene);

		render.renderImage();
		render.writeToImage();
	}




	@Test
	public void abstractmickey3() {
		Scene scene = new Scene("Test scene");
		scene.set_camera(new Camera(new Point3D(-950, 0, -1611), new Vector(950, 0, 1611), new Vector(0, -1, 0)));
//		scene.set_camera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
		scene.set_distance(500);
		scene.set_background(new Color(java.awt.Color.black));
		scene.set_ambientLight(new AmbientLight(new Color(226,46,46), 0.15));
		double z=-50;
		Material material=new Material(0.5, 0.5, 60);
		Color color=new Color(java.awt.Color.pink);
		scene.addGeometries( new Sphere(new Color(206,190,134), new Material(0.2, 0.2, 100, 0, 0), // )
						50, new Point3D(0,0,0)),
//				new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
//						new Point3D(0, 0, -50), new Point3D(5, 12, -50), new Point3D(-5, 12, -50)), //
//				new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
//						new Point3D(-15, 17, -50), new Point3D(15, 17, -50), new Point3D(0, 35, -50)), //
				new Sphere(new Color(java.awt.Color.black), new Material(0.2, 0.2, 30, 0, 0), // )
						30, new Point3D(60, -50, 10)),
				new Sphere(new Color(java.awt.Color.black), new Material(0.2, 0.2, 30, 0, 0), // )
						30, new Point3D(-60, -50, 10)),

				//new Triangle(  //
				//		new Point3D(11.95, 7.3, -180), new Point3D(2.98, 7.3, -180), new Point3D(9, 15, -180),new Color(java.awt.Color.red)),
				//new Sphere( // )
				//		new Point3D(9.4, 7, -180),2.1,new Color(java.awt.Color.red)),
				//new Sphere(  // )
				//		new Point3D(5.2, 7, -180),2.1,new Color(java.awt.Color.red)),

				new Sphere(new Color(java.awt.Color.black), new Material(0.2, 0.2, 30, 0, 0), // )
						10, new Point3D(-32, -32, -30)),
				new Triangle(new Color(java.awt.Color.red), new Material(0.5, 0.5, 60), //
						new Point3D(-32, -32, -30), new Point3D(-10, -30, -50), new Point3D(-15, -60, -30)), //
				new Triangle(new Color(java.awt.Color.red), new Material(0.5, 0.5, 60), //
						new Point3D(-32, -32, -30), new Point3D(-50, -25, -50), new Point3D(-60, -55, -30)),
				//eyes
				new Sphere(  // )
						new Color(java.awt.Color.white),new Material(0.2, 0.2, 30, 0, 0),8,new Point3D(15, -15, -40)),
				new Sphere( new Color(java.awt.Color.white),new Material(0.2, 0.2, 30, 0, 0),8,new Point3D(-15, -15, -40)),
				new Sphere(  // )
						new Color(java.awt.Color.black),new Material(0.2, 0.2, 30, 0, 0),4,new Point3D(15, -15, -45)),
				new Sphere( new Color(java.awt.Color.black),new Material(0.2, 0.2, 30, 0, 0),4,new Point3D(-15, -15, -45)),
				//end eyes
				//אף
				new Sphere(new Color(java.awt.Color.black), new Material(0.2, 0.2, 100, 0, 0), // )
						12, new Point3D(0,0,-40)),
				//פה

				new Polygon(color, material,new Point3D(-20.8,17.369,z),new Point3D(-22.9,19.017,z),new Point3D(-21.3,21.037,z),new Point3D(-19.1,19.577,z)),
				new Polygon(color, material,new Point3D(-21.3,21.037,z),new Point3D(-19.1,19.577,z),new Point3D(-15.9,22.777,z),new Point3D(-18,24.743,z)),
				new Polygon(color, material,new Point3D(-15.9,22.777,z),new Point3D(-18,24.743,z),new Point3D(-16.4,26.135,z),new Point3D(-14.4,24.075,z)),
				new Polygon(color, material,new Point3D(-16.4,26.135,z),new Point3D(-14.4,24.075,z),new Point3D(-12.6,25.464,z),new Point3D(-14.4,28.07,z)),
				new Polygon(color, material,new Point3D(-12.6,25.464,z),new Point3D(-14.4,28.07,z),new Point3D(-13.1,29.097,z),new Point3D(-11.4,26.287,z)),
//				new Polygon(color, material,new Point3D(-13.1,29.097,z),new Point3D(-11.4,26.287,z),new Point3D(-9.7,27.312,z),new Point3D(-11.2,30.416,z)),
//				new Polygon(color, material,new Point3D(-9.7,27.312,z),new Point3D(-11.2,30.416,z),new Point3D(-8,28.171,z),new Point3D(-9.6,27.367,z)),
//				new Polygon(color, material,new Point3D(-8,28.171,z),new Point3D(-9.4,31.475,z),new Point3D(-7.9,32.217,z),new Point3D(-6.5,28.793,z)),
//				new Polygon(color, material,new Point3D(-7.9,32.217,z),new Point3D(-6.5,28.793,z),new Point3D(-4.8,29.342,z),new Point3D(-6,32.971,z)),
//				new Polygon(color, material,new Point3D(-4.8,29.342,z),new Point3D(-6,32.971,z),new Point3D(-4.3,33.472,z),new Point3D(-3.3,29.707,z)),
//				new Polygon(color, material,new Point3D(-4.3,33.472,z),new Point3D(-3.3,29.707,z),new Point3D(-2.3,29.849,z),new Point3D(-2.7,33.792,z)),
//				new Polygon(color, material,new Point3D(-2.3,29.849,z),new Point3D(-2.7,33.792,z),new Point3D(-1.5,33.936,z),new Point3D(-1.2,29.959,z)),
//				new Polygon(color, material,new Point3D(-1.5,33.936,z),new Point3D(-1.2,29.959,z),new Point3D(0,-30,z),new Point3D(0,34,z)),
//				new Polygon(color, material,new Point3D(-1.2,29.959,z),new Point3D(0,-30,z),new Point3D(0,34,z),new Point3D(1.5,33.936,z)),
//				new Polygon(color, material,new Point3D(0,34,z),new Point3D(1.5,33.936,z),new Point3D(1.5,29.936,z),new Point3D(2.7,29.792,z)),
//				new Polygon(color, material,new Point3D(1.5,29.936,z),new Point3D(2.7,29.792,z),new Point3D(3,33.743,z),new Point3D(4,33.543,z)),
//				new Polygon(color, material,new Point3D(3,33.743,z),new Point3D(4,33.543,z),new Point3D(4,29.543,z),new Point3D(5.3,29.197,z),new Point3D(4,29.543,z)),
//				new Polygon(color, material,new Point3D(5.3,29.197,z),new Point3D(5.5,33.136,z),new Point3D(7.4,32.435,z),new Point3D(5.5,33.136,z)),
//				new Polygon(color, material,new Point3D(7.4,32.435,z),new Point3D(6.9,28.64,z),new Point3D(8.7,27.837,z),new Point3D(6.9,28.64,z)),
				new Polygon(color, material,new Point3D(8.7,27.837,z),new Point3D(9.6,31.367,z),new Point3D(12.3,29.677,z),new Point3D(9.6,31.367,z)),
				new Polygon(color, material,new Point3D(12.3,29.677,z),new Point3D(11.1,26.48,z),new Point3D(13.4,24.87,z),new Point3D(11.1,26.48,z)),
				new Polygon(color, material,new Point3D(13.4,24.87,z),new Point3D(15,27.571,z),new Point3D(17.6,25.15,z),new Point3D(15,27.571,z)),
				new Polygon(color, material,new Point3D(17.6,25.15,z),new Point3D(-15.9,22.777,z),new Point3D(18.708,20,z),new Point3D(-15.9,22.777,z)),
				new Polygon(color, material,new Point3D(18.708,20,z),new Point3D(20.8,21.639,z),new Point3D(22.5,19.536,z),new Point3D(20.3,18.226,z)),

				new Polygon(new Color(java.awt.Color.lightGray), new Material(0.5, 0.5, 60),new Point3D(550,550,200),new Point3D(-550,550,200),new Point3D(-550,-550,1000),new Point3D(550,-550,1000))
		);

		scene.addLights(//new SpotLight(new Color(700, 400, 400), //
//				new Point3D(60, -50, 0), new Vector(0, 0, 1), 1, 4E-5, 2E-7),
//				new SpotLight(new Color(700, 400, 400), //
//						new Point3D(-60, -50, 0), new Vector(0, 0, 1), 1, 4E-5, 2E-7),
				new SpotLight(new Color(700, 400, 400), //
						new Point3D(0, 0, -200), new Vector(0, 0, 10), 1, 4E-5, 2E-7,3)
		);

		ImageWriter imageWriter = new ImageWriter("abstractmickey3", 200, 200, 600, 600);
		Render render = new Render(imageWriter, scene);

		render.renderImage();
		render.writeToImage();
	}



	@Test
	public void glossyMicky() {

		Scene scene = new Scene("Test scene");
//		scene.set_camera(new Camera(new Point3D(-950, 0, -1611), new Vector(950, 0, 1611), new Vector(0, -1, 0)));
		scene.set_camera(new Camera(new Point3D(-1300, 0, -10500), new Vector(0, 0, 1), new Vector(0, -1, 0)));
		scene.set_distance(500);
		scene.set_background(new Color(169,205,250));
		scene.set_ambientLight(new AmbientLight(new Color(226, 46, 46), 0.15));
		double z = -50;
		double x2=-1300;
		double x3=-2700;
		double jump=250;
		double jump2=-1380;
		Material material = new Material(0.5, 0.5, 60);
		Color color = new Color(java.awt.Color.pink);
		scene.addGeometries(
				//the colorful scene

				//small micky
				new Sphere(new Color(java.awt.Color.black), new Material(0.2, 0.2, 100, 0, 0), // )
						50, new Point3D(0, 0, 0)),
//				new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
//						new Point3D(0, 0, -50), new Point3D(5, 12, -50), new Point3D(-5, 12, -50)), //
//				new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
//						new Point3D(-15, 17, -50), new Point3D(15, 17, -50), new Point3D(0, 35, -50)), //
				new Sphere(new Color(java.awt.Color.black), new Material(0.2, 0.2, 30, 0, 0), // )
						30, new Point3D(10, -50, 60)),
				new Sphere(new Color(java.awt.Color.black), new Material(0.2, 0.2, 30, 0, 0), // )
						30, new Point3D(-10, -50, -40)),
				//big micky
				new Sphere(new Color(java.awt.Color.darkGray), new Material(0.2, 0.8, 250, 0, 0.8), // )
						150, new Point3D(240, -100, 0)),
				new Sphere(new Color(java.awt.Color.darkGray), new Material(0.2, 0.8, 250, 0, 0.8), // )
						90, new Point3D(200, -250, 105)),
				new Sphere(new Color(java.awt.Color.darkGray), new Material(0.2, 0.8, 250, 0, 0.8), // )
						90, new Point3D(300, -250, -105)),
				//anoter big mickey
				new Sphere(new Color(4,58,60), new Material(0.2, 0.8, 100000, 0, 0.8), // )
						180, new Point3D(-240, -130, 0)),
				new Sphere(new Color(4,58,60), new Material(0.2, 0.8, 100000, 0, 0.8), // )
						105, new Point3D(-200, -295, 105)),
				new Sphere(new Color(4,58,60), new Material(0.2, 0.8, 100000, 0, 0.8), // )
						105, new Point3D(-300, -295, -105)),
				//floor
		new Polygon(new Color(java.awt.Color.gray), new Material(0.3,0,0,0.8,0), new Point3D(-635, 120, -1500), new Point3D(450, 120, -1500), new Point3D(800, 30, 300), new Point3D(-500, 30, 300)),
				new Polygon(new Color(77,26,75), new Material(0.3,0,0,0.8,0), new Point3D(-500, -1000, 310), new Point3D(800, -1000, 310), new Point3D(800, 30, 310), new Point3D(-500, 30, 310)),

				//black and white still 3D
				//small micky

				new Sphere(new Color(java.awt.Color.black), new Material(0.2, 0.2, 100, 0, 0), // )
						50, new Point3D(0+x2, 0, 0)),
//				new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
//						new Point3D(0, 0, -50), new Point3D(5, 12, -50), new Point3D(-5, 12, -50)), //
//				new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
//						new Point3D(-15, 17, -50), new Point3D(15, 17, -50), new Point3D(0, 35, -50)), //
				new Sphere(new Color(java.awt.Color.black), new Material(0.2, 0.2, 30, 0, 0), // )
						30, new Point3D(10+x2, -50, 60)),
				new Sphere(new Color(java.awt.Color.black), new Material(0.2, 0.2, 30, 0, 0), // )
						30, new Point3D(-10+x2, -50, -40)),
				//big micky
				new Sphere(new Color(java.awt.Color.darkGray), new Material(0.2, 0.8, 250, 0, 0.8), // )
						150, new Point3D(240+x2, -100, 0)),
				new Sphere(new Color(java.awt.Color.darkGray), new Material(0.2, 0.8, 250, 0, 0.8), // )
						90, new Point3D(200+x2, -250, 105)),
				new Sphere(new Color(java.awt.Color.darkGray), new Material(0.2, 0.8, 250, 0, 0.8), // )
						90, new Point3D(300+x2, -250, -105)),
				//anoter big mickey
				new Sphere(new Color(java.awt.Color.black), new Material(0.2, 0.8, 100000, 0, 0.8), // )
						180, new Point3D(-240+x2, -130, 0)),
				new Sphere(new Color(java.awt.Color.black), new Material(0.2, 0.8, 100000, 0, 0.8), // )
						105, new Point3D(-200+x2, -295, 105)),
				new Sphere(new Color(java.awt.Color.black), new Material(0.2, 0.8, 100000, 0, 0.8), // )
						105, new Point3D(-300+x2, -295, -105)),
				//floor
				new Polygon(new Color(160,160,160), new Material(0.3,0,0,0.8,0), new Point3D(-500+x2, 120, -1500), new Point3D(580+x2, 120, -1500), new Point3D(700+x2, 30, 300), new Point3D(-600+x2, 30, 300)),
				new Polygon(new Color(java.awt.Color.darkGray), new Material(0.3,0,0,0.8,0), new Point3D(-600+x2, -1000, 310), new Point3D(700+x2, -1000, 310), new Point3D(700+x2, 30, 310), new Point3D(-600+x2, 30, 310)),


				//black and white 2D
				//small micky

				new Sphere(new Color(java.awt.Color.black), new Material(0, 0.8, 100000, 0, 0.1), // )
						50, new Point3D(0+x3, 0, 0)),

				new Sphere(new Color(java.awt.Color.black), new Material(0, 0.8, 100000, 0, 0.1), // )
						30, new Point3D(10+x3, -50, 60)),
				new Sphere(new Color(java.awt.Color.black), new Material(0, 0.8, 100000, 0, 0.1), // )
						30, new Point3D(-10+x3, -50, -40)),
				//big micky
				new Sphere(new Color(java.awt.Color.lightGray), new Material(0.01, 0.2, 8, 0, 0.1), // )
						150, new Point3D(240+x3, -100, 0)),
				new Sphere(new Color(java.awt.Color.lightGray), new Material(0.01, 0.2, 2, 0, 0.1), // )
						90, new Point3D(200+x3, -250, 105)),
				new Sphere(new Color(java.awt.Color.lightGray), new Material(0.01, 0.2, 2, 0, 0.1), // )
						90, new Point3D(300+x3, -250, -105)),
				//anoter big mickey
				new Sphere(new Color(53,53,53), new Material(0, 0.8, 100000, 0, 0.1), // )
						180, new Point3D(-240+x3, -130, 0)),
				new Sphere(new Color(53,53,53), new Material(0, 0.8, 100000, 0, 0.1), // )
						105, new Point3D(-200+x3, -295, 105)),
				new Sphere(new Color(53,53,53), new Material(0, 0.8, 100000, 0, 0.1), // )
						105, new Point3D(-300+x3, -295, -105)),
				//floor
				new Polygon(new Color(200,200,200), new Material(0.2,0,0,0.8,0), new Point3D(-350+x3, 120, -1500), new Point3D(-1880, 120, -1500), new Point3D(700+x3, 30, 300), new Point3D(-700+x3, 30, 300)),
				new Polygon(new Color(100,100,100), new Material(0.2,0,0,0.8,0), new Point3D(-700+x3, -1000, 310), new Point3D(-2000, -1000, 310), new Point3D(700+x3, 30, 310), new Point3D(-700+x3, 30, 310)),


				//lines
				new Polygon(new Color(100,100,100), new Material(0.2,0,0,0.8,0), new Point3D(-100000, -1000, -310), new Point3D(-100000, -1200, -310), new Point3D(100000, -1200, -310), new Point3D(100000, -1000, -310)),
				new Polygon(new Color(100,100,100), new Material(0.2,0,0,0.8,0), new Point3D(-100000, 380, -310), new Point3D(-100000, 180, -310), new Point3D(100000, 180, -310), new Point3D(100000, 380, -310)),

				//up line
				new Polygon(new Color(152,196,248), new Material(0,0,0,0,0), new Point3D(-2000,210+jump2, -315), new Point3D(-1800,210+jump2, -315), new Point3D(-1800,350+jump2, -315), new Point3D(-2000,350+jump2, -315)),
				new Polygon(new Color(152,196,248), new Material(0,0,0,0,0), new Point3D(-2000+2*jump,210+jump2, -315), new Point3D(-1800+2*jump,210+jump2, -315), new Point3D(-1800+2*jump,350+jump2, -315), new Point3D(-2000+2*jump,350+jump2, -315)),
				new Polygon(new Color(152,196,248), new Material(0,0,0,0,0), new Point3D(-2000+3*jump,210+jump2, -315), new Point3D(-1800+3*jump,210+jump2, -315), new Point3D(-1800+3*jump,350+jump2, -315), new Point3D(-2000+3*jump,350+jump2, -315)),
				new Polygon(new Color(152,196,248), new Material(0,0,0,0,0), new Point3D(-2000+4*jump,210+jump2, -315), new Point3D(-1800+4*jump,210+jump2, -315), new Point3D(-1800+4*jump,350+jump2, -315), new Point3D(-2000+4*jump,350+jump2, -315)),
				new Polygon(new Color(152,196,248), new Material(0,0,0,0,0), new Point3D(-2000+5*jump,210+jump2, -315), new Point3D(-1800+5*jump,210+jump2, -315), new Point3D(-1800+5*jump,350+jump2, -315), new Point3D(-2000+5*jump,350+jump2, -315)),
				new Polygon(new Color(152,196,248), new Material(0,0,0,0,0), new Point3D(-2000+6*jump,210+jump2, -315), new Point3D(-1800+6*jump,210+jump2, -315), new Point3D(-1800+6*jump,350+jump2, -315), new Point3D(-2000+6*jump,350+jump2, -315)),
				new Polygon(new Color(152,196,248), new Material(0,0,0,0,0), new Point3D(-2000+7*jump,210+jump2, -315), new Point3D(-1800+7*jump,210+jump2, -315), new Point3D(-1800+7*jump,350+jump2, -315), new Point3D(-2000+7*jump,350+jump2, -315)),
				new Polygon(new Color(152,196,248), new Material(0,0,0,0,0), new Point3D(-2000+8*jump,210+jump2, -315), new Point3D(-1800+8*jump,210+jump2, -315), new Point3D(-1800+8*jump,350+jump2, -315), new Point3D(-2000+8*jump,350+jump2, -315)),
				new Polygon(new Color(152,196,248), new Material(0,0,0,0,0), new Point3D(-2000+9*jump,210+jump2, -315), new Point3D(-1800+9*jump,210+jump2, -315), new Point3D(-1800+9*jump,350+jump2, -315), new Point3D(-2000+9*jump,350+jump2, -315)),
				new Polygon(new Color(152,196,248), new Material(0,0,0,0,0), new Point3D(-2000+10*jump,210+jump2, -315), new Point3D(-1800+10*jump,210+jump2, -315), new Point3D(-1800+10*jump,350+jump2, -315), new Point3D(-2000+10*jump,350+jump2, -315)),
				new Polygon(new Color(152,196,248), new Material(0,0,0,0,0), new Point3D(-2000+11*jump,210+jump2, -315), new Point3D(-1800+11*jump,210+jump2, -315), new Point3D(-1800+11*jump,350+jump2, -315), new Point3D(-2000+11*jump,350+jump2, -315)),
				new Polygon(new Color(152,196,248), new Material(0,0,0,0,0), new Point3D(-2000-jump,210+jump2, -315), new Point3D(-1800-jump,210+jump2, -315), new Point3D(-1800-jump,350+jump2, -315), new Point3D(-2000-jump,350+jump2, -315)),
				new Polygon(new Color(152,196,248), new Material(0,0,0,0,0), new Point3D(-2000-2*jump,210+jump2, -315), new Point3D(-1800-2*jump,210+jump2, -315), new Point3D(-1800-2*jump,350+jump2, -315), new Point3D(-2000-2*jump,350+jump2, -315)),
				new Polygon(new Color(152,196,248), new Material(0,0,0,0,0), new Point3D(-2000-3*jump,210+jump2, -315), new Point3D(-1800-3*jump,210+jump2, -315), new Point3D(-1800-3*jump,350+jump2, -315), new Point3D(-2000-3*jump,350+jump2, -315)),
				new Polygon(new Color(152,196,248), new Material(0,0,0,0,0), new Point3D(-2000-4*jump,210+jump2, -315), new Point3D(-1800-4*jump,210+jump2, -315), new Point3D(-1800-4*jump,350+jump2, -315), new Point3D(-2000-4*jump,350+jump2, -315)),
				new Polygon(new Color(152,196,248), new Material(0,0,0,0,0), new Point3D(-2000-5*jump,210+jump2, -315), new Point3D(-1800-5*jump,210+jump2, -315), new Point3D(-1800-5*jump,350+jump2, -315), new Point3D(-2000-5*jump,350+jump2, -315)),
				new Polygon(new Color(152,196,248), new Material(0,0,0,0,0), new Point3D(-2000-6*jump,210+jump2, -315), new Point3D(-1800-6*jump,210+jump2, -315), new Point3D(-1800-6*jump,350+jump2, -315), new Point3D(-2000-6*jump,350+jump2, -315)),
				new Polygon(new Color(152,196,248), new Material(0,0,0,0,0), new Point3D(-2000+jump,210+jump2, -315), new Point3D(-1800+jump,210+jump2, -315), new Point3D(-1800+jump,350+jump2, -315), new Point3D(-2000+jump,350+jump2, -315)),
				//down line
				new Polygon(new Color(152,196,248), new Material(0,0,0,0,0), new Point3D(-2000,210, -315), new Point3D(-1800,210, -315), new Point3D(-1800,350, -315), new Point3D(-2000,350, -315)),
				new Polygon(new Color(152,196,248), new Material(0,0,0,0,0), new Point3D(-2000+2*jump,210, -315), new Point3D(-1800+2*jump,210, -315), new Point3D(-1800+2*jump,350, -315), new Point3D(-2000+2*jump,350, -315)),
				new Polygon(new Color(152,196,248), new Material(0,0,0,0,0), new Point3D(-2000+3*jump,210, -315), new Point3D(-1800+3*jump,210, -315), new Point3D(-1800+3*jump,350, -315), new Point3D(-2000+3*jump,350, -315)),
				new Polygon(new Color(152,196,248), new Material(0,0,0,0,0), new Point3D(-2000+4*jump,210, -315), new Point3D(-1800+4*jump,210, -315), new Point3D(-1800+4*jump,350, -315), new Point3D(-2000+4*jump,350, -315)),
				new Polygon(new Color(152,196,248), new Material(0,0,0,0,0), new Point3D(-2000+5*jump,210, -315), new Point3D(-1800+5*jump,210, -315), new Point3D(-1800+5*jump,350, -315), new Point3D(-2000+5*jump,350, -315)),
				new Polygon(new Color(152,196,248), new Material(0,0,0,0,0), new Point3D(-2000+6*jump,210, -315), new Point3D(-1800+6*jump,210, -315), new Point3D(-1800+6*jump,350, -315), new Point3D(-2000+6*jump,350, -315)),
				new Polygon(new Color(152,196,248), new Material(0,0,0,0,0), new Point3D(-2000+7*jump,210, -315), new Point3D(-1800+7*jump,210, -315), new Point3D(-1800+7*jump,350, -315), new Point3D(-2000+7*jump,350, -315)),
				new Polygon(new Color(152,196,248), new Material(0,0,0,0,0), new Point3D(-2000+8*jump,210, -315), new Point3D(-1800+8*jump,210, -315), new Point3D(-1800+8*jump,350, -315), new Point3D(-2000+8*jump,350, -315)),
				new Polygon(new Color(152,196,248), new Material(0,0,0,0,0), new Point3D(-2000+9*jump,210, -315), new Point3D(-1800+9*jump,210, -315), new Point3D(-1800+9*jump,350, -315), new Point3D(-2000+9*jump,350, -315)),
				new Polygon(new Color(152,196,248), new Material(0,0,0,0,0), new Point3D(-2000+10*jump,210, -315), new Point3D(-1800+10*jump,210, -315), new Point3D(-1800+10*jump,350, -315), new Point3D(-2000+10*jump,350, -315)),
				new Polygon(new Color(152,196,248), new Material(0,0,0,0,0), new Point3D(-2000+11*jump,210, -315), new Point3D(-1800+11*jump,210, -315), new Point3D(-1800+11*jump,350, -315), new Point3D(-2000+11*jump,350, -315)),
				new Polygon(new Color(152,196,248), new Material(0,0,0,0,0), new Point3D(-2000-jump,210, -315), new Point3D(-1800-jump,210, -315), new Point3D(-1800-jump,350, -315), new Point3D(-2000-jump,350, -315)),
				new Polygon(new Color(152,196,248), new Material(0,0,0,0,0), new Point3D(-2000-2*jump,210, -315), new Point3D(-1800-2*jump,210, -315), new Point3D(-1800-2*jump,350, -315), new Point3D(-2000-2*jump,350, -315)),
				new Polygon(new Color(152,196,248), new Material(0,0,0,0,0), new Point3D(-2000-3*jump,210, -315), new Point3D(-1800-3*jump,210, -315), new Point3D(-1800-3*jump,350, -315), new Point3D(-2000-3*jump,350, -315)),
				new Polygon(new Color(152,196,248), new Material(0,0,0,0,0), new Point3D(-2000-4*jump,210, -315), new Point3D(-1800-4*jump,210, -315), new Point3D(-1800-4*jump,350, -315), new Point3D(-2000-4*jump,350, -315)),
				new Polygon(new Color(152,196,248), new Material(0,0,0,0,0), new Point3D(-2000-5*jump,210, -315), new Point3D(-1800-5*jump,210, -315), new Point3D(-1800-5*jump,350, -315), new Point3D(-2000-5*jump,350, -315)),
				new Polygon(new Color(152,196,248), new Material(0,0,0,0,0), new Point3D(-2000-6*jump,210, -315), new Point3D(-1800-6*jump,210, -315), new Point3D(-1800-6*jump,350, -315), new Point3D(-2000-6*jump,350, -315)),
				new Polygon(new Color(152,196,248), new Material(0,0,0,0,0), new Point3D(-2000+jump,210, -315), new Point3D(-1800+jump,210, -315), new Point3D(-1800+jump,350, -315), new Point3D(-2000+jump,350, -315))
//

		);

		scene.addLights(//new SpotLight(new Color(700, 400, 400), //
//				new Point3D(60, -50, 0), new Vector(0, 0, 1), 1, 4E-5, 2E-7),
//				new SpotLight(new Color(700, 400, 400), //
//						new Point3D(-60, -50, 0), new Vector(0, 0, 1), 1, 4E-5, 2E-7),
				new SpotLight(new Color(java.awt.Color.white), //
						new Point3D(0, 0, -200), new Vector(0, 0, 10), 1, 4E-5, 2E-7, 3),
				new SpotLight(new Color(java.awt.Color.white), //
						new Point3D(0+x2, 0, -200), new Vector(0, 0, 10), 1, 4E-5, 2E-7, 3),
				new SpotLight(new Color(java.awt.Color.white), //
						new Point3D(0+x3, 0, -200), new Vector(0, 0, 10), 1, 4E-5, 2E-7, 3),
				new SpotLight(new Color(java.awt.Color.white), //
						new Point3D(100, -200, 0), new Vector(0, 1, 0), 1, 4E-5, 2E-7, 3),
//				new DirectionalLight(new Color(java.awt.Color.white),new Vector(0,0,1)),
				new DirectionalLight(new Color(java.awt.Color.white),new Vector(0,1,0))
		);

		ImageWriter imageWriter = new ImageWriter("glossyMicky", 200, 200, 1000, 1000);
		Render render = new Render(imageWriter, scene,true);

		render.renderImage();
		render.writeToImage();
	}

}
