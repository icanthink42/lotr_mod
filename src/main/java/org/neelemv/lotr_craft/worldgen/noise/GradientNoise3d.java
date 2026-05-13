package org.neelemv.lotr_craft.worldgen.noise;

import org.neelemv.lotr_craft.worldgen.rng.Rng;

public final class GradientNoise3d {
    private static final int[] GRAD_X = {  1, -1,  1, -1,  1, -1,  1, -1,  0,  0,  0,  0,  1, -1,  0,  0 };
    private static final int[] GRAD_Y = {  1,  1, -1, -1,  0,  0,  0,  0,  1, -1,  1, -1,  1,  1, -1, -1 };
    private static final int[] GRAD_Z = {  0,  0,  0,  0,  1,  1, -1, -1,  1,  1, -1, -1,  0,  0,  1, -1 };

    private final int[] perm = new int[512];

    public GradientNoise3d(Rng rng) {
        int[] p = new int[256];
        for (int i = 0; i < 256; i++) p[i] = i;
        var stream = rng.stream();
        for (int i = 255; i > 0; i--) {
            int j = stream.nextInt(i + 1);
            int t = p[i]; p[i] = p[j]; p[j] = t;
        }
        for (int i = 0; i < 512; i++) perm[i] = p[i & 255];
    }

    // Returns approximately [-1, 1].
    public double noise(double x, double y, double z) {
        int x0 = floor(x);
        int y0 = floor(y);
        int z0 = floor(z);
        double xf = x - x0;
        double yf = y - y0;
        double zf = z - z0;
        int xi = x0 & 255;
        int yi = y0 & 255;
        int zi = z0 & 255;
        double u = fade(xf);
        double v = fade(yf);
        double w = fade(zf);

        int g000 = perm[perm[perm[xi]     + yi    ] + zi    ] & 15;
        int g100 = perm[perm[perm[xi + 1] + yi    ] + zi    ] & 15;
        int g010 = perm[perm[perm[xi]     + yi + 1] + zi    ] & 15;
        int g110 = perm[perm[perm[xi + 1] + yi + 1] + zi    ] & 15;
        int g001 = perm[perm[perm[xi]     + yi    ] + zi + 1] & 15;
        int g101 = perm[perm[perm[xi + 1] + yi    ] + zi + 1] & 15;
        int g011 = perm[perm[perm[xi]     + yi + 1] + zi + 1] & 15;
        int g111 = perm[perm[perm[xi + 1] + yi + 1] + zi + 1] & 15;

        double n000 = dot(g000, xf,       yf,       zf      );
        double n100 = dot(g100, xf - 1.0, yf,       zf      );
        double n010 = dot(g010, xf,       yf - 1.0, zf      );
        double n110 = dot(g110, xf - 1.0, yf - 1.0, zf      );
        double n001 = dot(g001, xf,       yf,       zf - 1.0);
        double n101 = dot(g101, xf - 1.0, yf,       zf - 1.0);
        double n011 = dot(g011, xf,       yf - 1.0, zf - 1.0);
        double n111 = dot(g111, xf - 1.0, yf - 1.0, zf - 1.0);

        return lerp(
                lerp(lerp(n000, n100, u), lerp(n010, n110, u), v),
                lerp(lerp(n001, n101, u), lerp(n011, n111, u), v),
                w);
    }

    private static double dot(int g, double x, double y, double z) {
        return GRAD_X[g] * x + GRAD_Y[g] * y + GRAD_Z[g] * z;
    }

    private static double fade(double t) {
        return t * t * t * (t * (t * 6.0 - 15.0) + 10.0);
    }

    private static double lerp(double a, double b, double t) {
        return a + (b - a) * t;
    }

    private static int floor(double x) {
        int xi = (int) x;
        return x < xi ? xi - 1 : xi;
    }
}
