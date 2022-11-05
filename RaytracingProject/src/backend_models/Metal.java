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
public class Metal implements Material {
    
    public Colour albedo;
    public double fuzz;
    
    public Metal(Colour albedo, double fuzz) {
        this.albedo = albedo;
        
        if (fuzz < 1) {
            this.fuzz = fuzz;
        } else {
            this.fuzz = 1;
            
        }
    }
    
    @Override
    public boolean scatter(Ray rIn, HitRecord rec,
            Colour attenuationOut, Ray rOut
    ) {
        attenuationOut.copy(albedo);
        Vec3 nextRayDir = Vec3.reflect(rIn.direction().unitVector(), rec.normal); //diffuse
        rOut.set(rec.p, nextRayDir.plus(Vec3.randomUnitVector().times(fuzz)));
        return nextRayDir.dot(rec.normal) > 0;
    }
}
