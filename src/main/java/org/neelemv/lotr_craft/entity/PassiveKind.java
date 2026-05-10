package org.neelemv.lotr_craft.entity;

public enum PassiveKind {
    BUTTERFLY("butterfly", "butterfly/common/0.png", 0x261F1D, 0xFF83E6, 0.35F, 0.25F, 4.0, 0.18),
    MIDGES("midges", "midge.png", 0x5641B0, 0x1E1802, 0.35F, 0.25F, 4.0, 0.16),
    RABBIT("rabbit", "rabbit/0.png", 0x966BD2, 0x54402A, 0.45F, 0.45F, 8.0, 0.26),
    BOAR("boar", "boar/boar.png", 0x65432A, 0x3E1A02, 0.9F, 0.85F, 16.0, 0.23),
    LION("lion", "lion/lion.png", 0xCBA34A, 0xA56228, 0.9F, 0.9F, 24.0, 0.26),
    LIONESS("lioness", "lion/lioness.png", 0xCBA94A, 0xAB7E42, 0.9F, 0.85F, 22.0, 0.27),
    GIRAFFE("giraffe", "giraffe/giraffe.png", 0xCFAB67, 0x6A4630, 1.3F, 3.1F, 30.0, 0.22),
    RHINO("rhino", "rhino/rhino.png", 0x5D5A51, 0xB9B09D, 1.45F, 1.25F, 34.0, 0.18),
    CROCODILE("crocodile", "crocodile.png", 0x2C3333, 0x0F0B06, 1.7F, 0.45F, 18.0, 0.16),
    GEMSBOK("gemsbok", "gemsbok.png", 0xB36F3F, 0xF2E1D7, 1.0F, 1.35F, 18.0, 0.24),
    FLAMINGO("flamingo", "flamingo/flamingo.png", 0xF5749E, 0xF9D6E3, 0.45F, 1.25F, 10.0, 0.22),
    BIRD("bird", "bird/common/0.png", 0x71BFC0, 0x71BFC0, 0.35F, 0.35F, 6.0, 0.20),
    CAMEL("camel", "camel/camel.png", 0xC8A16D, 0x8C7158, 1.3F, 2.1F, 26.0, 0.22),
    ELK("elk", "elk/elk/0.png", 0xEBE199, 0xB69574, 1.2F, 1.6F, 24.0, 0.24),
    TERMITE("termite", "termite.png", 0xC28139, 0x793A25, 0.35F, 0.25F, 4.0, 0.18),
    DIK_DIK("dik_dik", "dikdik/0.png", 0xB77B5B, 0x684C29, 0.45F, 0.75F, 8.0, 0.26),
    SWAN("swan", "swan.png", 0xF5F5F5, 0xED9B49, 0.55F, 0.7F, 10.0, 0.20),
    DEER("deer", "deer/0.png", 0x5B386D, 0xB6A28A, 0.9F, 1.25F, 16.0, 0.25),
    AUROCHS("aurochs", "aurochs/0.png", 0x724CAC, 0x311A0F, 1.35F, 1.45F, 26.0, 0.20),
    BEAR("bear", "bear/dark.png", 0x725580, 0x3D29A2, 1.35F, 1.35F, 30.0, 0.22),
    FISH("fish", "fish/common.png", 0x6B9AD3, 0xB5CDE5, 0.5F, 0.3F, 6.0, 0.14);

    private final String id;
    private final String texturePath;
    private final int eggBaseColor;
    private final int eggSpotColor;
    private final float width;
    private final float height;
    private final double health;
    private final double speed;

    PassiveKind(String id, String texturePath, int eggBaseColor, int eggSpotColor, float width, float height, double health, double speed) {
        this.id = id;
        this.texturePath = texturePath;
        this.eggBaseColor = eggBaseColor;
        this.eggSpotColor = eggSpotColor;
        this.width = width;
        this.height = height;
        this.health = health;
        this.speed = speed;
    }

    public String id() {
        return id;
    }

    public String texturePath() {
        return texturePath;
    }

    public int eggBaseColor() {
        return eggBaseColor;
    }

    public int eggSpotColor() {
        return eggSpotColor;
    }

    public float width() {
        return width;
    }

    public float height() {
        return height;
    }

    public double health() {
        return health;
    }

    public double speed() {
        return speed;
    }
}
