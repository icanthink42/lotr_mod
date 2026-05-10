package org.neelemv.lotr_craft.entity;

public enum HobbitKind {
    HOBBIT("hobbit", null),
    HOBBIT_BARTENDER("hobbit_bartender", "outfit_bartender"),
    HOBBIT_BOUNDER("hobbit_bounder", null),
    HOBBIT_SHIRRIFF("hobbit_shirriff", null),
    HOBBIT_ORCHARDER("hobbit_orcharder", null),
    HOBBIT_FARMER("hobbit_farmer", null),
    HOBBIT_FARMHAND("hobbit_farmhand", null),
    BREE_HOBBIT("bree_hobbit", null),
    BREE_HOBBIT_INNKEEPER("bree_hobbit_innkeeper", "outfit_bartender"),
    BREE_HOBBIT_BAKER("bree_hobbit_baker", "outfit_baker"),
    BREE_HOBBIT_BUTCHER("bree_hobbit_butcher", "outfit_butcher"),
    BREE_HOBBIT_BREWER("bree_hobbit_brewer", "outfit_brewer"),
    BREE_HOBBIT_FLORIST("bree_hobbit_florist", "outfit_florist");

    private final String id;
    private final String outfitTextureName;

    HobbitKind(String id, String outfitTextureName) {
        this.id = id;
        this.outfitTextureName = outfitTextureName;
    }

    public String id() {
        return id;
    }

    public String outfitTextureName() {
        return outfitTextureName;
    }
}
