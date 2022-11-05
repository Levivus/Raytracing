package backend_models;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author cheng
 * @param <VecType>
 */
public interface Vec3Math<VecType extends Vec3Math> {

    VecType self(); // must return this;

    double[] e(); // return this.e;

    VecType setE(double[] dArr); // this.e = dArr; return this;

    default VecType copy(double[] other) {
        // copies other into this object's e array
        // returns this object
        if (this.e() == null) {

            this.setE(Arrays.copyOf(other, 3));
        } else {
            System.arraycopy(other, 0, this.e(), 0, 3);
        }
        return this.self();
    }

    default VecType copy(Vec3Math other) {
        // same as copy above
        return this.copy(other.e());
    }

    VecType copy(); // deep copy of self

    default double x() {
        return this.e()[0];
    }

    default double y() {
        return this.e()[1];
    }

    default double z() {
        return this.e()[2];
    }

    default double get(int i) {
        return this.e()[i];
    }

    default VecType set(int i, double v) {
        this.e()[i] = v;
        return this.self();
    }

    default VecType plusEqual(Vec3Math other) {
        // vector +=
        double[] e = this.e();
        double[] oE = other.e();
        e[0] += oE[0];
        e[1] += oE[1];
        e[2] += oE[2];
        return this.self();
    }

    default VecType minusEqual(Vec3Math other) {
        // vector -=
        double[] e = this.e();
        double[] oE = other.e();
        e[0] -= oE[0];
        e[1] -= oE[1];
        e[2] -= oE[2];
        return this.self();
    }

    default VecType timesEqual(Vec3Math other) {
        // component-wise multiplication
        double[] e = this.e();
        double[] oE = other.e();
        e[0] *= oE[0];
        e[1] *= oE[1];
        e[2] *= oE[2];
        return this.self();
    }

    default VecType timesEqual(double dNum) {
        // vector-scaler multiplication
        double[] e = this.e();
        e[0] *= dNum;
        e[1] *= dNum;
        e[2] *= dNum;
        return this.self();
    }

    default VecType divideEqual(double dNum) {
        // vector-scaler division
        return this.timesEqual(1 / dNum);
    }

    default double lengthSquared() {
        // x^2 + y^2 + z^2
        return this.x() * this.x() + this.y() * this.y() + this.z() * this.z();
    }

    default double length() {
        return Math.sqrt(this.lengthSquared());
    }

    // Various Utility Functions
    default String asString() {
        // pretty String representation like (x, y, z)
        return "(" + this.x() + ", " + this.y() + ", " + this.z() + ")";
    }

    default VecType negativeValue() {
        // negative of this vector without changing this vector
        VecType theCopy = this.copy();
        theCopy.timesEqual(-1);
        return theCopy;
    }

    default VecType plus(Vec3Math other) {
        // thisVector + otherVector without modifying thisVector
        VecType theCopy = this.copy();
        theCopy.plusEqual(other);
        return theCopy;
    }

    default VecType minus(Vec3Math other) {
        // thisVector - otherVector without modifying thisVector
        VecType theCopy = this.copy();
        theCopy.minusEqual(other);
        return theCopy;
    }

    default VecType times(Vec3Math other) {
        // thisVector * otherVector component-wise, without modifying thisVector
        VecType theCopy = this.copy();
        theCopy.timesEqual(other);
        return theCopy;
    }

    default VecType times(double dNum) {
        // thisVector * number, without modifying thisVector
        VecType theCopy = this.copy();
        theCopy.timesEqual(dNum);
        return theCopy;
    }

    static <VecType extends Vec3Math<VecType>>
            VecType times(double dNum, VecType vec) {
        // used as Vec3Math.times(someNum, someVec)
        // rather than someVec.times(someNum)
        return vec.times(dNum);
    }

    default VecType divide(double dNum) {
        return this.times(1 / dNum);
    }

    default double dot(Vec3Math other) {
        double[] e = this.e();
        double[] oE = other.e();
        return e[0] * oE[0] + e[1] * oE[1]
                + e[2] * oE[2];
    }

    default VecType crossEqual(Vec3Math other) {
        // thisVector = thisVector x otherVector
        double[] e = this.e();
        double[] oE = other.e();
        double x = e[1] * oE[2] - e[2] * oE[1];
        double y = e[2] * oE[0] - e[0] * oE[2];
        double z = e[0] * oE[1] - e[1] * oE[0];
        e[0] = x;
        e[1] = y;
        e[2] = z;
        return this.self();
    }

    default VecType cross(Vec3Math other) {
        // thisVector x otherVector without modifying thisVector
        VecType theCopy = this.copy();
        theCopy.crossEqual(other);
        return theCopy;
    }

    default VecType unitVector() {
        // unit vector of this vector
        return this.divide(this.length());
    }

    static <T extends Vec3Math> T randomize(T vec) {
        double[] e = vec.e();
        e[0] = Math.random();
        e[1] = Math.random();
        e[2] = Math.random();
        return vec;
    }
    
    static <T extends Vec3Math> T randomize(T vec, double min, double max) {
        double[] e = vec.e();
        e[0] = ThreadLocalRandom.current().nextDouble(min, max);
        e[1] = ThreadLocalRandom.current().nextDouble(min, max);
        e[2] = ThreadLocalRandom.current().nextDouble(min, max);
        return vec;
    }

}
