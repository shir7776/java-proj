package unittests;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import primitives.*;
import geometries.*;

import java.util.List;
import elements.Camera;
//import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static org.junit.Assert.assertEquals;


public class CameraIntegrationTest {

    /**
     * Test method for
     * {@link Camera#constructRayThroughPixel(int, int, int, int, double, double, double)}.
     */
    Camera cam1 = new Camera(Point3D.zero, new Vector(0, 0, 1), new Vector(0, -1, 0));
    Camera cam2 = new  Camera(new Point3D(0, 0, -0.5), new Vector(0, 0, 1), new Vector(0, -1, 0));
    // TC01: regular sphere 2 intersection points
    @Test
    void constructRayThroughPixelWithSphere1() {
        //TO DO
        Sphere sph =  new Sphere( new Point3D(0, 0, 3),1);
//        Ray ray = cam1.constructRayThroughPixel(3,3,0,0,1,3,3);
//        List<Point3D> results =  sph.findIntersections(ray);
        List<Point3D> results;
        int count = 0;
        int Nx =3;
        int Ny =3;
        for (int i = 0; i < Ny; ++i) {
            for (int j = 0; j < Nx; ++j) {
                results = sph.findIntsersections(cam1.constructRayThroughPixel(Nx, Ny, j, i, 1, 3, 3));
                if (results != null)
                    count += results.size();
            }
        }

        assertEquals("something wrong",2,count);
        System.out.println("count: "+count);

    }

    // TC02:big sphere 18intersection points
    @Test
    void constructRayThroughPixelWithSphere2() {
        Sphere sph =  new Sphere( new Point3D(0, 0, 2.5),2.5);

        List<Point3D> results;
        int count = 0;
        // TODO explanations
        int Nx =3;
        int Ny =3;

        // TODO explanations
        for (int i = 0; i < Ny; ++i) {
            for (int j = 0; j < Nx; ++j) {
                results = sph.findIntsersections(cam2.constructRayThroughPixel(Nx, Ny, j, i, 1, 3, 3));
                if (results != null)
                    count += results.size();
            }
        }
        assertEquals("something wrong",18,count);
        System.out.println("count: "+count);
    }

    // TC03:part of the view plain is inside the sphere 10 intersection points
    @Test
    void constructRayThroughPixelWithSphere3() {
        Sphere sph =  new Sphere( new Point3D(0, 0, 2),2);

        List<Point3D> results;
        int count = 0;
        // TODO explanations
        int Nx =3;
        int Ny =3;

        // TODO explanations
        for (int i = 0; i < Ny; ++i) {
            for (int j = 0; j < Nx; ++j) {
                results = sph.findIntsersections(cam2.constructRayThroughPixel(Nx, Ny, j, i, 1, 3, 3));
                if (results != null)
                    count += results.size();
            }
        }
        assertEquals("something wrong",10,count);
        System.out.println("count: "+count);
    }


    // TC04:whole view plain is inside the sphere 9 intersection points
    @Test
    void constructRayThroughPixelWithSphere4() {
        Sphere sph =  new Sphere( new Point3D(0, 0, 2.5),4);

        List<Point3D> results;
        int count = 0;
        // TODO explanations
        int Nx =3;
        int Ny =3;

        // TODO explanations
        for (int i = 0; i < Ny; ++i) {
            for (int j = 0; j < Nx; ++j) {
                results = sph.findIntsersections(cam1.constructRayThroughPixel(Nx, Ny, j, i, 1, 3, 3));
                if (results != null)
                    count += results.size();
            }
        }
        assertEquals("something wrong",9,count);
        System.out.println("count: "+count);
    }

    // TC05:sphere behind the view pain 0 intersection points
    @Test
    void constructRayThroughPixelWithSphere5() {
        Sphere sph =  new Sphere( new Point3D(0, 0, -1),0.5);

        List<Point3D> results;
        int count = 0;
        // TODO explanations
        int Nx =3;
        int Ny =3;

        // TODO explanations
        for (int i = 0; i < Ny; ++i) {
            for (int j = 0; j < Nx; ++j) {
                results = sph.findIntsersections(cam1.constructRayThroughPixel(Nx, Ny, j, i, 1, 3, 3));
                if (results != null)
                    count += results.size();
            }
        }
        assertEquals("something wrong",0,count);
        System.out.println("count: "+count);
    }

    // TC06: regular triangle 1 intersection points
    @Test
    void constructRayThroughPixelWithTriangle1() {
        Triangle triangle =  new Triangle(new Point3D(0,-1,2),new Point3D(1,1,2),new Point3D(-1,1,2));

        List<Point3D> results;
        int count = 0;

        int Nx =3;
        int Ny =3;

        for (int i = 0; i < Ny; ++i) {
            for (int j = 0; j < Nx; ++j) {
                results = triangle.findIntsersections(cam1.constructRayThroughPixel(Nx, Ny, j, i, 1, 3, 3));
                if (results != null)
                    count += results.size();
            }
        }
        assertEquals("something wrong",1,count);
        System.out.println("count: "+count);
    }

    // TC07: a long triangle 2 intersection points
    @Test
    void constructRayThroughPixelWithTriangle2() {
        Triangle triangle =  new Triangle(new Point3D(0,-20,2),new Point3D(1,1,2),new Point3D(-1,1,2));

        List<Point3D> results;
        int count = 0;

        int Nx =3;
        int Ny =3;

        for (int i = 0; i < Ny; ++i) {
            for (int j = 0; j < Nx; ++j) {
                results = triangle.findIntsersections(cam1.constructRayThroughPixel(Nx, Ny, j, i, 1, 3, 3));
                if (results != null)
                    count += results.size();
            }
        }
        assertEquals("something wrong",2,count);
        System.out.println("count: "+count);
    }


    // TC08: regular plane 9 intersection points
    @Test
    void constructRayThroughPixelWithPlane1() {
        Plane plane = new Plane(new Point3D(0, -20, 2), new Point3D(1, 1, 2), new Point3D(-1, 1, 2));

        List<Point3D> results;
        int count = 0;
        // TODO explanations
        int Nx = 3;
        int Ny = 3;

        // TODO explanations
        for (int i = 0; i < Ny; ++i) {
            for (int j = 0; j < Nx; ++j) {
                results = plane.findIntsersections(cam1.constructRayThroughPixel(Nx, Ny, j, i, 1, 3, 3));
                if (results != null)
                    count += results.size();
            }
        }
        assertEquals("something wrong", 9, count);
        System.out.println("count: " + count);
    }


    // TC09:a little to the side plane 9 intersection points
    @Test
    void constructRayThroughPixelWithPlane2() {
        Plane plane = new Plane(new Point3D(0, -20, 5), new Point3D(1, 1, 2), new Point3D(-1, 1, 2));

        List<Point3D> results;
        int count = 0;
        // TODO explanations
        int Nx = 3;
        int Ny = 3;

        // TODO explanations
        for (int i = 0; i < Ny; ++i) {
            for (int j = 0; j < Nx; ++j) {
                results = plane.findIntsersections(cam1.constructRayThroughPixel(Nx, Ny, j, i, 1, 3, 3));
                if (results != null)
                    count += results.size();
            }
        }
        assertEquals("something wrong", 9, count);
        System.out.println("count: " + count);
    }

    // TC10:a lot to the side plane 6 intersection points
    @Test
    void constructRayThroughPixelWithPlane3() {
        Plane plane = new Plane(new Point3D(0, -20, 50), new Point3D(1, 1, 2), new Point3D(-1, 1, 2));

        List<Point3D> results;
        int count = 0;
        // TODO explanations
        int Nx = 3;
        int Ny = 3;

        // TODO explanations
        for (int i = 0; i < Ny; ++i) {
            for (int j = 0; j < Nx; ++j) {
                results = plane.findIntsersections(cam1.constructRayThroughPixel(Nx, Ny, j, i, 1, 3, 3));
                if (results != null)
                    count += results.size();
            }
        }
        assertEquals("something wrong", 6, count);
        System.out.println("count: " + count);
    }

}
