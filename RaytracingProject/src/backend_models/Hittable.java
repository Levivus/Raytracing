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
public interface Hittable {
    boolean hit(Ray r, double tMin, double tMax, HitRecord recOut);
}
