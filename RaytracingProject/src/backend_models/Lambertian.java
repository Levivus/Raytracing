/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend_models;

/**
 *
 * @author Leviv
 */
public class Lambertian implements Material {

    public Colour albedo;

    public Lambertian(Colour albedo) {
        this.albedo = albedo;
    }

    @Override
    public boolean scatter(Ray rIn, HitRecord rec, Colour attenuationOut, Ray rOut) {
        attenuationOut.copy(albedo);
        Vec3 nextRayDir = rec.normal.plus(Vec3.randomUnitVector()); //diffuse
        rOut.set(rec.p, nextRayDir);
        return true;
    }
}
