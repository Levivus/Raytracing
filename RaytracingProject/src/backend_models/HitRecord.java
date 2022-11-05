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
public class HitRecord {

    public Pnt3 p;
    public Vec3 normal;
    double t;
    boolean frontFace;
    Material mat;

    public HitRecord() {
        this.p = Pnt3.zero();
        this.normal = Vec3.zero();
    }

    void copy(HitRecord other) {
        this.t = other.t;
        this.p.copy(other.p);
        this.normal.copy(other.normal);
        this.frontFace = other.frontFace;
        this.mat = other.mat;
    }

    public HitRecord setFaceNormal(Ray r, Vec3 outwardNormal) {
        this.frontFace = (r.direction().dot(outwardNormal) <= 0);

        if (this.frontFace) {
            this.normal = outwardNormal;
        } else {
            this.normal = outwardNormal.negativeValue();
        }
        return this;
    }

}
