package backend_models;

import java.awt.Color;

/**
 * Colour represents RGB with each component ranging from 0 to 1 inclusive.
 *
 * @author cheng
 */
public class Colour implements Vec3Math<Colour> {

    public double[] e;

    private Colour() {
    }

    // factory and utility functions
    @Override
    public String toString() {
        return "(" + (int) (255.999 * this.x()) + ", " + (int) (255.999 * this.y()) + ", " + (int) (255.999 * this.z()) + ")";
    }

    public static Colour valueOf(Vec3Math other) {
        // makes a Colour representation of other
        Colour newOne = new Colour();
        return newOne.copy(other);

    }

    public static Colour valueOf(double x, double y, double z) {
        // makes a Colour that's (x, y, z)
        Colour newOne = new Colour();
        newOne.e = new double[]{x, y, z};
        return newOne;
    }

    public static Colour view(Vec3Math other) {
        //makes a Colour that *shares* the underlying (x, y, z) with other
        Colour newOne = new Colour();
        newOne.e = other.e();
        return newOne;
    }

    public static Colour zero() {
        //return Colour value of black i.e. (0, 0, 0)
        Colour newOne = new Colour();
        newOne.e = new double[3];
        return newOne;
    }

    public Color toColor() {
        // convert to a java.awt.Color
        int ir = (int) (255.999 * this.x());
        int ig = (int) (255.999 * this.y());
        int ib = (int) (255.999 * this.z());
        return new Color(ir, ig, ib);
    }

    public static double clamp(double x, double min, double max) {
        if (x < min) {
            return min;
        }
        if (x > max) {
            return max;
        }
        return x;
    }

    public Color toColor(int sampleCollected) {
        // convert to a java.awt.Color
        double r = this.x();
        double g = this.y();
        double b = this.z();

        double scale = 1.0 / sampleCollected;
        r *= scale;
        g *= scale;
        b *= scale;

        r = Math.sqrt(r);
        g = Math.sqrt(g);
        b = Math.sqrt(b);
        
        
        int ir = (int) (256 * Colour.clamp(r, 0, 0.9999));
        int ig = (int) (256 * Colour.clamp(g, 0, 0.9999));
        int ib = (int) (256 * Colour.clamp(b, 0, 0.9999));
        return new Color(ir, ig, ib);
    }

    // code gen implement abstract methods
    @Override
    public Colour self() {
        return this;
    }

    @Override

    public double[] e() {
        return this.e;
    }

    @Override
    public Colour setE(double[] dArr) {
        this.e = dArr;
        return this;
    }

    @Override
    public Colour copy() {
        Colour theCopy = new Colour();
        theCopy.copy(this);
        return theCopy;
    }
}
