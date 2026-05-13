package org.neelemv.lotr_craft.worldgen.rng;

public final class RngStream {
    private long s0;
    private long s1;

    RngStream(long s0, long s1) {
        this.s0 = s0;
        this.s1 = s1;
    }

    public long nextLong() {
        long result = s0 + s1;
        s1 ^= s0;
        s0 = Long.rotateLeft(s0, 24) ^ s1 ^ (s1 << 16);
        s1 = Long.rotateLeft(s1, 37);
        return result;
    }

    public int nextInt(int bound) {
        return (int) ((nextLong() >>> 1) % bound);
    }

    public double nextDouble() {
        return (nextLong() >>> 11) * 0x1.0p-53;
    }
}
