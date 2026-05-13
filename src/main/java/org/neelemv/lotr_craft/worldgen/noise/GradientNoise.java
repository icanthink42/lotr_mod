package org.neelemv.lotr_craft.worldgen.noise;

import org.neelemv.lotr_craft.worldgen.rng.Rng;
import org.neelemv.lotr_craft.worldgen.rng.RngStream;

public final class GradientNoise {
    private static final int[] GRAD_X = {  1, -1,  1, -1,  1, -1,  0,  0 };
    private static final int[] GRAD_Z = {  1,  1, -1, -1,  0,  0,  1, -1 };

    private final int[] perm = new int[512];

    public GradientNoise(Rng rng) {
        RngStream stream = rng.stream();
        int[] p = new int[256];
        for (int i = 0; i < 256; i++) p[i] = i;
        for (int i = 255; i > 0; i--) {
            int j = stream.nextInt(i + 1);
            int t = p[i]; p[i] = p[j]; p[j] = t;
        }
        for (int i = 0; i < 512; i++) perm[i] = p[i & 255];
    }

    // Returns approximately [-1, 1].
    public double noise(double x, double z) {
        int x0 = floor(x);
        int z0 = floor(z);
        double xf = x - x0;
        double zf = z - z0;
        int xi = x0 & 255;
        int zi = z0 & 255;
        double u = fade(xf);
        double v = fade(zf);

        int g00 = perm[perm[xi]     + zi    ] & 7;
        int g10 = perm[perm[xi + 1] + zi    ] & 7;
        int g01 = perm[perm[xi]     + zi + 1] & 7;
        int g11 = perm[perm[xi + 1] + zi + 1] & 7;

        double n00 = GRAD_X[g00] * xf       + GRAD_Z[g00] * zf;
        double n10 = GRAD_X[g10] * (xf - 1) + GRAD_Z[g10] * zf;
        double n01 = GRAD_X[g01] * xf       + GRAD_Z[g01] * (zf - 1);
        double n11 = GRAD_X[g11] * (xf - 1) + GRAD_Z[g11] * (zf - 1);

        return lerp(lerp(n00, n10, u), lerp(n01, n11, u), v);
    }

    private static double fade(double t) {
        return t * t * t * (t * (t * 6.0 - 15.0) + 10.0);
    }

    static double lerp(double a, double b, double t) {
        return a + (b - a) * t;
    }

    static int floor(double x) {
        int xi = (int) x;
        return x < xi ? xi - 1 : xi;
    }
}
