package lab1;

import java.math.BigInteger;

public class GCD {
    public BigInteger r;
    public BigInteger x;
    public BigInteger y;

    public GCD(BigInteger r, BigInteger x, BigInteger y) {
        this.r = r;
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        String yStr = y.compareTo(BigInteger.ZERO) < 0 ? "- " + y.abs() : "+ " + y;
        return r + " = " + x + " * a " + yStr + " * b";
    }
}
