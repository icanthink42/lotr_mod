package org.neelemv.lotr_craft.worldgen.noise;

import org.neelemv.lotr_craft.worldgen.rng.Rng;

public final class Fbm {
    private final GradientNoise noise;
    private final int octaves;
    private final double lacunarity;
    private final double gain;
    private final double normalization;

    public Fbm(Rng rng, int octaves, double lacunarity, double gain) {
        this.noise = new GradientNoise(rng);
        this.octaves = octaves;
        this.lacunarity = lacunarity;
        this.gain = gain;
        double amp = 1.0;
        double total = 0.0;
        for (int i = 0; i < octaves; i++) {
            total += amp;
            amp *= gain;
        }
        this.normalization = 1.0 / total;
    }

    // Returns approximately [-1, 1].
    public double sample(double x, double z) {
        double value = 0.0;
        double amplitude = 1.0;
        double frequency = 1.0;
        for (int i = 0; i < octaves; i++) {
            value += noise.noise(x * frequency, z * frequency) * amplitude;
            amplitude *= gain;
            frequency *= lacunarity;
        }
        return value * normalization;
    }
}
