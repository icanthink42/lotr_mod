package org.neelemv.lotr_craft.worldgen.rng;

public final class Rng {
    private final long seed;

    public Rng(long seed) {
        this.seed = seed;
    }

    public Rng fork(String name) {
        long h = 0;
        for (int i = 0; i < name.length(); i++) {
            h = h * 31L + name.charAt(i);
        }
        return new Rng(mix(seed ^ h));
    }

    public RngStream stream() {
        return new RngStream(mix(seed), mix(seed ^ 0xff51afd7ed558ccdL));
    }

    private static long mix(long v) {
        v = (v ^ (v >>> 30)) * 0xbf58476d1ce4e5b9L;
        v = (v ^ (v >>> 27)) * 0x94d049bb133111ebL;
        return v ^ (v >>> 31);
    }
}
