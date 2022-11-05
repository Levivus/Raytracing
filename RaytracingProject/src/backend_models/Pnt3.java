package backend_models;

import java.awt.Color;

/**
 * Colour represents RGB with each component ranging from 0 to 1 inclusive.
 *
 * @author cheng
 */
public class Pnt3 implements Vec3Math<Pnt3> {

    public double[] e;

    private Pnt3() {
    }

    // factory and utility functions
    @Override
    public String toString() {
        return this.asString();
    }

    public static Pnt3 valueOf(Vec3Math other) {
        // makes a Colour representation of other
        Pnt3 newOne = new Pnt3();
        return newOne.copy(other);

    }

    public static Pnt3 valueOf(double x, double y, double z) {
        // makes a Colour that's (x, y, z)
        Pnt3 newOne = new Pnt3();
        newOne.e = new double[]{x, y, z};
        return newOne;
    }

    public static Pnt3 view(Vec3Math other) {
        //makes a Colour that *shares* the underlying (x, y, z) with other
        Pnt3 newOne = new Pnt3();
        newOne.e = other.e();
        return newOne;
    }

    public static Pnt3 zero() {
        //return Colour value of black i.e. (0, 0, 0)
        Pnt3 newOne = new Pnt3();
        newOne.e = new double[3];
        return newOne;
    }

    // code gen implement abstract methods
    @Override
    public Pnt3 self() {
        return this;
    }

    @Override

    public double[] e() {
        return this.e;
    }

    @Override
    public Pnt3 setE(double[] dArr) {
        this.e = dArr;
        return this;
    }

    @Override
    public Pnt3 copy() {
        Pnt3 theCopy = new Pnt3();
        theCopy.copy(this);
        return theCopy;
    }
}
