package com.zsotroav.Util;

/**
 * Generic Tuple implementation
 * @param <X> First type stored
 * @param <Y> Second type stored
 */
public class Tuple<X, Y> {
    public final X x; // Left value
    public final Y y; // Right value
    public Tuple(X x, Y y) {
        this.x = x;
        this.y = y;
    }
}