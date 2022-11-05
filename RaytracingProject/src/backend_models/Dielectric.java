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
public class Dielectric implements Material {

    double refreactiveIdx;

    public Dielectric(double refreactiveIdx) {
        this.refreactiveIdx = refreactiveIdx;
    }

    @Override
    public boolean scatter(Ray rIn, HitRecord rec, Colour attenuationOut, Ray rOut) {
        attenuationOut.copy(Colour.valueOf(1, 1, 1));
        double etaOverEtaPrime;
        if (rec.frontFace) {
            etaOverEtaPrime = 1.0 / this.refreactiveIdx;
        } else {
            etaOverEtaPrime = this.refreactiveIdx;
        }

        Vec3 unitDirection = rIn.direction().unitVector();

        double cosTheta = Math.min(1, unitDirection.negativeValue().dot(rec.normal));
        double sinTheta = Math.sqrt(1.0 - cosTheta * cosTheta);
        if (etaOverEtaPrime * sinTheta > 1) {
            Vec3 nextRayDir = Vec3.reflect(unitDirection, rec.normal); //diffuse
            rOut.set(rec.p, nextRayDir);
            return true;
        }

        double reflectProb = Dielectric.schlick(cosTheta, etaOverEtaPrime);
        if (Math.random() < reflectProb) {
            Vec3 nextRayDir = Vec3.reflect(unitDirection, rec.normal); //diffuse
            rOut.set(rec.p, nextRayDir);
            return true;
        }
        Vec3 nextRayDir = Vec3.refract(rIn.direction().unitVector(), rec.normal, etaOverEtaPrime);
        rOut.set(rec.p, nextRayDir);
        return true;
    }

    public static double schlick(double cosineAngle, double etaOverEtaPrime) {
        double frac = (1 - etaOverEtaPrime) / (1 + etaOverEtaPrime);
        double r0 = frac * frac;
        return r0 + (1 - r0) * Math.pow((1 - cosineAngle), 5);
    }
}
