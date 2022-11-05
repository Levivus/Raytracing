/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend_models;

import java.awt.Color;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 903082003
 */
public class Renderer {

    public final static double ASPECT_RATIO = 16.0 / 9;
    public final static int IMAGE_WIDTH = 600;
    public static int image_HEIGHT = (int) (IMAGE_WIDTH / ASPECT_RATIO);

    public static String status = "";

    public static Colour rayColour(Ray r, HittableList world, int depth) {
        if (depth <= 0) {
            return Colour.zero();
        }
        HitRecord rec = new HitRecord();
        if (world.hit(r, 0.001, Double.POSITIVE_INFINITY, rec)) {

            Colour attenuationOut = Colour.zero();
            Ray nextRay = new Ray();
            boolean scatterValid = rec.mat.scatter(r, rec, attenuationOut, nextRay);

            if (scatterValid) {
                Colour nextColour = Renderer.rayColour(nextRay, world, depth - 1);
                return nextColour.times(attenuationOut);
            } else {
                return Colour.zero();
            }

        }

        Vec3 unitDirection = r.direction().unitVector();
        double t = 0.5 * (unitDirection.y() + 1.0);

        return Colour.valueOf(1, 1, 1).times(1 - t).plus(Colour.valueOf(0.5, 0.7, 1).times(t));
    }

    public static void render(PicFile targetPic) {
        if (targetPic.getHeight() < Renderer.image_HEIGHT || targetPic.getWidth() < Renderer.IMAGE_WIDTH) {
            throw new RuntimeException("Image Width or height not as expected");
        }

        final int imageHeight = Renderer.image_HEIGHT;
        final int imageWidth = Renderer.IMAGE_WIDTH;
        final int samplesPerPixel = 100;
        final int maxDepth = 50;

        double verticalFovDeg = 20;
        Vec3 vup = Vec3.valueOf(0, 1, 0);
        Pnt3 lookFrom = Pnt3.valueOf(13, 2, 3);
        Pnt3 lookAt = Pnt3.valueOf(0, 0, 0);
        double distToFocus = 10;
        double aperture = 0.1;

        Camera cam = new Camera(lookFrom, lookAt, vup, verticalFovDeg, Renderer.ASPECT_RATIO, aperture, distToFocus);
        //World
        HittableList theWorld = Scene.randomScene();

        ArrayList<Thread> threads = new ArrayList<>(); // add this line. But you'll have to import ArrayList appropriately!
        for (int y = 0; y < imageHeight; ++y) {
            Renderer.status = "Scanlines remaining: " + (imageHeight - y);
            for (int x = 0; x < imageWidth; ++x) {
                final int fx = x;
                final int fy = y;
                Thread t = new Thread(() -> {
                    renderPixel(samplesPerPixel, fx, imageWidth, imageHeight, fy, cam, theWorld, maxDepth, targetPic);
                });
                t.start();
                threads.add(t);
            }
            for (Thread t : threads) {
                try {
                    t.join(); // ensures that thread t is finished working before loop will continue
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }
            Renderer.status = "Done";
        }
    }

    public static void renderPixel(final int samplesPerPixel, int x, final int imageWidth, final int imageHeight, int y, Camera cam, HittableList theWorld, final int maxDepth, PicFile targetPic) {
        Colour pixelColour = Colour.zero();
        for (int i = 0; i < samplesPerPixel; i++) {
            double u = (double) (Math.random() + x) / (imageWidth - 1);
            double v = (double) (Math.random() + imageHeight - 1 - y) / (imageHeight - 1);

            Ray r = cam.getRay(u, v);

            pixelColour.plusEqual(Renderer.rayColour(r, theWorld, maxDepth));
        }

        targetPic.setColor(x, y, pixelColour.toColor(samplesPerPixel));
    }

}
