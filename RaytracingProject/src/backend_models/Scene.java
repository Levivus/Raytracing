/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend_models;

import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author 903082003
 */
public class Scene {

    public static HittableList randomScene() {
        HittableList world = new HittableList();

        world.add(new Sphere(
                Pnt3.valueOf(0, -1000, 0), 1000, new Lambertian(Colour.valueOf(0.5, 0.5, 0.5))));

        for (int a = -11; a < 11; a++) {
            for (int b = -11; b < 11; b++) {
                double chooseMaterial = Math.random();
                Pnt3 center = Pnt3.valueOf(a + 0.9 * Math.random(), 0.2, b + 0.9 * Math.random());
                if ((center.minus(Vec3.valueOf(4, 0.2, 0))).length() > 0.9) {
                    if (chooseMaterial < 0.8) {
                        // diffuse 80%
                        Colour albedo = Vec3Math.randomize(Colour.zero()).times(Vec3Math.randomize(Colour.zero()));
                        world.add(
                                new Sphere(center, 0.2, new Lambertian(albedo)));
                    } else if (chooseMaterial < 0.95) {
                        // metal 15%
                        Colour albedo = Vec3Math.randomize(Colour.zero(), 0.5, 1);
                        double fuzz = ThreadLocalRandom.current().nextDouble(0, .5);
                        world.add(
                                new Sphere(center, 0.2, new Metal(albedo, fuzz)));
                    } else {
                        // glass 5%
                        world.add(new Sphere(center, 0.2, new Dielectric(1.5)));
                    }
                }
            }
        }

        world.add(new Sphere(Pnt3.valueOf(0, 1, 0), 1.0, new Dielectric(1.5)));

        world.add(
                new Sphere(Pnt3.valueOf(-4, 1, 0), 1.0, new Lambertian(Colour.valueOf(.4, .2, .1))));

        world.add(
                new Sphere(Pnt3.valueOf(4, 1, 0), 1.0, new Metal(Colour.valueOf(.7, .6, .5), 0.0)));

        return world;
    }
}
