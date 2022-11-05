package backend_models;

import java.awt.Color;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Colour represents RGB with each component ranging from 0 to 1 inclusive.
 *
 * @author cheng
 */
public class Vec3 implements Vec3Math<Vec3> {

    public double[] e;

    private Vec3() {
    }

    // factory and utility functions
    @Override
    public String toString() {
        return this.asString();
    }

    public static Vec3 valueOf(Vec3Math other) {
        // makes a Colour representation of other
        Vec3 newOne = new Vec3();
        return newOne.copy(other);

    }

    public static Vec3 valueOf(double x, double y, double z) {
        // makes a Colour that's (x, y, z)
        Vec3 newOne = new Vec3();
        newOne.e = new double[]{x, y, z};
        return newOne;
    }

    public static Vec3 view(Vec3Math other) {
        //makes a Colour that *shares* the underlying (x, y, z) with other
        Vec3 newOne = new Vec3();
        newOne.e = other.e();
        return newOne;
    }

    public static Vec3 zero() {
        //return Colour value of black i.e. (0, 0, 0)
        Vec3 newOne = new Vec3();
        newOne.e = new double[3];
        return newOne;
    }

    // code gen implement abstract methods
    @Override
    public Vec3 self() {
        return this;
    }

    @Override

    public double[] e() {
        return this.e;
    }

    @Override
    public Vec3 setE(double[] dArr) {
        this.e = dArr;
        return this;
    }

    @Override
    public Vec3 copy() {
        Vec3 theCopy = new Vec3();
        theCopy.copy(this);
        return theCopy;
    }

    public static Vec3 reflect(Vec3 incidentRay, Vec3 normalVec) {
        final Vec3 v = incidentRay;
        final Vec3 n = normalVec;
        return v.minus(n.times(2 * v.dot(n)));
    }

    public static Vec3 randomInUnitSphere() {
        while (true) {
            Vec3 p = Vec3Math.randomize(Vec3.zero(), -1, 1);
            if (p.lengthSquared() >= 1) {
                continue;
            }
            return p;
        }
    }

    public static Vec3 randomUnitVector() {
        double z = ThreadLocalRandom.current().nextDouble(-1, 1);
        double a = ThreadLocalRandom.current().nextDouble(0, 2 * Math.PI);
        double r = Math.sqrt(1 - z * z);
        double x = r * Math.cos(a);
        double y = r * Math.sin(a);
        return Vec3.valueOf(x, y, z);
    }

    public static Vec3 refract(Vec3 inRay, Vec3 normal, double etaOverEtaPrime) {
        double cosTheta = -1 * inRay.dot(normal);
        Vec3 rOutParallel = inRay.plus(normal.times(cosTheta)).times(etaOverEtaPrime);
        Vec3 rOutPerpendicular = normal.times(-1 * Math.sqrt(1.0 - rOutParallel.lengthSquared()));
        return rOutParallel.plus(rOutPerpendicular);
    }

    public static Vec3 randomInUnitDisk() {
        ThreadLocalRandom rand = ThreadLocalRandom.current();
        while (true) {
            Vec3 p = Vec3.valueOf(rand.nextDouble(-1, 1),rand.nextDouble(-1, 1), 0);
            if (p.lengthSquared() >= 1) {
                continue;
            }
            return p;
        }
    }
}
