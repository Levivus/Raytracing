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
public interface Material {
    boolean scatter(Ray rIn, HitRecord rec, Colour attenuationOut, Ray rOut);
}
