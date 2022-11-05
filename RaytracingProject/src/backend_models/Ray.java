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
public class Ray {
    
    public Pnt3 origin;
    public Vec3 direction;
    
    public Ray() {
        this.origin = Pnt3.zero();
        this.direction = Vec3.zero();
    }
    
    public Ray(Pnt3 origin, Vec3 direction) {
        this.origin = origin;
        this.direction = direction;
    }
    
    public Ray set(Pnt3 origin, Vec3 direction) {
        this.origin = origin;
        this.direction = direction;
        return this;
    }
    
    public Pnt3 origin() {
        return this.origin;
    }
    
    public Vec3 direction() {
        return this.direction;
    }
    
    public Pnt3 at(double t) {
        return this.origin.plus(this.direction.times(t));
    }
}
