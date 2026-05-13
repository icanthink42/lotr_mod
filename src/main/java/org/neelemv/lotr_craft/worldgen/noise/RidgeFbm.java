package org.neelemv.lotr_craft.worldgen.noise;

import org.neelemv.lotr_craft.worldgen.rng.Rng;

public final class RidgeFbm {
    private final GradientNoise noise;
    private final int octaves;
    private final double lacunarity;
    private final double gain;
    private final double sharpness;
    private final double normalization;

    // lacunarity: frequency multiplier per octave, typically 2.0
    // gain:       amplitude multiplier per octave, typically 0.5
    // sharpness:  exponent applied to each ridge value -- higher = sharper peaks
    public RidgeFbm(Rng rng, int octaves, double lacunarity, double gain, double sharpness) {
        this.noise = new GradientNoise(rng);
        this.octaves = octaves;
        this.lacunarity = lacunarity;
        this.gain = gain;
        this.sharpness = sharpness;
        double amp = 1.0;
        double total = 0.0;
        for (int i = 0; i < octaves; i++) {
            total += amp;
            amp *= gain;
        }
        this.normalization = 1.0 / total;
    }

    // Returns [0, 1], with 1 at sharp ridges/peaks and 0 in valleys.
    // Higher-frequency octaves are damped in valleys so jagged detail only
    // appears on ridges, not on flat terrain.
    public double sample(double x, double z) {
        double value = 0.0;
        double amplitude = 1.0;
        double frequency = 1.0;
        double weight = 1.0;

        for (int i = 0; i < octaves; i++) {
            double n = noise.noise(x * frequency, z * frequency);
            double ridge = Math.max(0.0, 1.0 - Math.abs(n));
            ridge = Math.pow(ridge, sharpness);

            value += ridge * amplitude * weight;
            weight = Math.max(0.0, Math.min(1.0, ridge));

            amplitude *= gain;
            frequency *= lacunarity;
        }

        return value * normalization;
    }
}
