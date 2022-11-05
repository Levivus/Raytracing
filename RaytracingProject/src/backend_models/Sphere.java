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
public class Sphere implements Hittable {

    public Pnt3 center;
    public double radius;
    public Material mat;

    public Sphere() {
        this.center = Pnt3.zero();
    }

    public Sphere(Pnt3 center, double radius, Material mat) {
        this.center = center;
        this.radius = radius;
        this.mat = mat;
    }

    @Override
    public boolean hit(Ray r, double tMin, double tMax, HitRecord rec) {
        Pnt3 oc = r.origin().minus(center);
        double a = r.direction().lengthSquared();
        double half_b = (r.direction().dot(oc));
        double c = oc.lengthSquared() - radius * radius;
        double discrim = half_b * half_b - a * c;

        if (discrim > 0) {

            double sqrtDiscr = Math.sqrt(discrim);
            double quadRoot = (-half_b - sqrtDiscr) / a;
            if (quadRoot > tMin && quadRoot < tMax) {
                rec.t = quadRoot;
                rec.p = r.at(rec.t);

                Vec3 outwardNormal = Vec3.view(
                        rec.p.minus(this.center).divide(this.radius));
                rec.setFaceNormal(r, outwardNormal);
                rec.mat = this.mat;
                return true;
            }
            quadRoot = (-half_b + sqrtDiscr) / a;
            if (quadRoot > tMin && quadRoot < tMax) {
                rec.t = quadRoot;
                rec.p = r.at(rec.t);
                Vec3 outwardNormal = Vec3.view(
                        rec.p.minus(this.center).divide(this.radius));
                rec.setFaceNormal(r, outwardNormal);
                rec.mat = this.mat;
                return true;
            }
        }
        return false;
    }
}
