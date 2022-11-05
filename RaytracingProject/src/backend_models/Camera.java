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
public class Camera {

    private Pnt3 origin;
    private Vec3 horizontal;
    private Vec3 vertical;
    private Pnt3 lowerLeftCorner;
    private Vec3 uVec, vVec, wVec;
    private double lensRadius;

    public Camera(Pnt3 lookFrom, Pnt3 lookAt, Vec3 vup, double vFovDeg, double aspectRatio,
            double aperture, double focusDist) {
        this.lensRadius = aperture / 2;
        this.origin = lookFrom;
        double theta = Math.toRadians(vFovDeg);
        double halfHeight = Math.tan(theta / 2);
        double halfWidth = aspectRatio * halfHeight;

        this.wVec = Vec3.view(lookFrom.minus(lookAt).unitVector());
        this.uVec = vup.cross(this.wVec).unitVector();
        this.vVec = this.wVec.cross(this.uVec);

        this.horizontal = this.uVec.times(2 * halfWidth * focusDist);
        this.vertical = this.vVec.times(2 * halfHeight * focusDist);
        this.lowerLeftCorner = origin
                .minus(horizontal.divide(2))
                .minus(vertical.divide(2))
                .minus(wVec.times(focusDist));
    }

    public Ray getRay(double u, double v) {
        Vec3 rd = Vec3.randomInUnitDisk().times(this.lensRadius);
        Vec3 offset = this.uVec.times(rd.x()).plus(this.vVec.times(rd.y()));

        return new Ray(this.origin.plus(offset),
                Vec3.view(this.lowerLeftCorner
                        .plus(this.horizontal.times(u))
                        .plus(this.vertical.times(v))
                        .minus(this.origin).minus(offset)
                ));

    }
}

