package org.neelemv.lotr_craft.client.render.entity;

import org.neelemv.lotr_craft.Lotr_craft;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;

public class LotrCustomHelmetModel<S extends HumanoidRenderState> extends HumanoidModel<S> {
    private static final CubeDeformation NONE = CubeDeformation.NONE;
    private static final CubeDeformation ARMOR = new CubeDeformation(1.0F);

    public LotrCustomHelmetModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition createLayer(Variant variant) {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        PartDefinition head = root.addOrReplaceChild("head", baseHead(variant), PartPose.ZERO);
        head.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.ZERO);
        root.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.ZERO);
        root.addOrReplaceChild("right_arm", CubeListBuilder.create(), PartPose.offset(-5.0F, 2.0F, 0.0F));
        root.addOrReplaceChild("left_arm", CubeListBuilder.create(), PartPose.offset(5.0F, 2.0F, 0.0F));
        root.addOrReplaceChild("right_leg", CubeListBuilder.create(), PartPose.offset(-1.9F, 12.0F, 0.0F));
        root.addOrReplaceChild("left_leg", CubeListBuilder.create(), PartPose.offset(1.9F, 12.0F, 0.0F));

        switch (variant) {
            case GONDOR -> gondor(head);
            case HIGH_ELVEN -> highElven(head);
            case DORWINION_ELF -> dorwinionElf(head);
            case RHUN_GOLD -> rhunGold(head);
            case BLACK_URUK -> blackUruk(head);
            case URUK -> uruk(head);
            case GUNDABAD_URUK -> gundabadUruk(head);
            case HARAD_TURBAN -> haradTurban(head);
        }
        return LayerDefinition.create(mesh, 64, 32);
    }

    public static Variant variantFor(ItemStack stack) {
        if (stack == null || stack.isEmpty()) {
            return null;
        }
        Identifier id = BuiltInRegistries.ITEM.getKey(stack.getItem());
        if (!Lotr_craft.MOD_ID.equals(id.getNamespace())) {
            return null;
        }
        String path = id.getPath();
        for (Variant variant : Variant.values()) {
            if (variant.itemPath().equals(path)) {
                return variant;
            }
        }
        return null;
    }

    private static CubeListBuilder baseHead(Variant variant) {
        if (variant == Variant.HARAD_TURBAN) {
            return CubeListBuilder.create();
        }
        return CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, ARMOR);
    }

    private static void gondor(PartDefinition head) {
        head.addOrReplaceChild("crest_base", CubeListBuilder.create()
                .texOffs(0, 16).addBox(-1.5F, -9.0F, -3.5F, 3.0F, 1.0F, 7.0F, ARMOR)
                .texOffs(20, 16).addBox(-0.5F, -10.0F, -3.5F, 1.0F, 1.0F, 7.0F, ARMOR),
                PartPose.ZERO);
        head.addOrReplaceChild("front_plate", CubeListBuilder.create()
                .texOffs(24, 0).addBox(-1.5F, -11.5F, -5.5F, 3.0F, 4.0F, 1.0F, NONE)
                .texOffs(24, 5).addBox(-0.5F, -12.5F, -5.5F, 1.0F, 1.0F, 1.0F, NONE)
                .texOffs(28, 5).addBox(-0.5F, -7.5F, -5.5F, 1.0F, 1.0F, 1.0F, NONE),
                PartPose.ZERO);
        head.addOrReplaceChild("back_plate", CubeListBuilder.create()
                .texOffs(32, 0).addBox(-1.5F, -10.5F, 4.5F, 3.0F, 3.0F, 1.0F, NONE)
                .texOffs(32, 4).addBox(-0.5F, -11.5F, 4.5F, 1.0F, 1.0F, 1.0F, NONE)
                .texOffs(36, 4).addBox(-0.5F, -7.5F, 4.5F, 1.0F, 1.0F, 1.0F, NONE),
                PartPose.ZERO);
    }

    private static void highElven(PartDefinition head) {
        head.addOrReplaceChild("front_spike", CubeListBuilder.create()
                .texOffs(0, 0).addBox(-0.5F, -11.0F, -2.0F, 1.0F, 3.0F, 1.0F, NONE)
                .texOffs(0, 4).addBox(-0.5F, -10.0F, 2.0F, 1.0F, 2.0F, 1.0F, NONE),
                PartPose.ZERO);
        head.addOrReplaceChild("crest", CubeListBuilder.create()
                .texOffs(32, 0).addBox(-1.0F, -11.0F, -8.0F, 2.0F, 1.0F, 11.0F, NONE)
                .texOffs(32, 12).addBox(-1.0F, -10.0F, -8.0F, 2.0F, 1.0F, 1.0F, NONE),
                PartPose.rotation(-0.27925268F, 0.0F, 0.0F));
    }

    private static void dorwinionElf(PartDefinition head) {
        head.addOrReplaceChild("tail", CubeListBuilder.create()
                .texOffs(20, 16).addBox(0.0F, -10.0F, 4.0F, 0.0F, 10.0F, 4.0F, NONE),
                PartPose.ZERO);
        head.addOrReplaceChild("outer_shell", CubeListBuilder.create()
                .texOffs(32, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(1.5F)),
                PartPose.ZERO);
        head.addOrReplaceChild("crest", CubeListBuilder.create()
                .texOffs(0, 16).addBox(-1.0F, -11.0F, -6.0F, 2.0F, 5.0F, 8.0F, NONE),
                PartPose.rotation(-0.2617994F, 0.0F, 0.0F));
    }

    private static void rhunGold(PartDefinition head) {
        head.addOrReplaceChild("brim", CubeListBuilder.create()
                .texOffs(0, 16).addBox(-5.5F, -9.5F, -5.5F, 11.0F, 2.0F, 11.0F, NONE)
                .texOffs(32, 8).addBox(-3.5F, -10.5F, -3.5F, 7.0F, 1.0F, 7.0F, NONE)
                .texOffs(50, 16).addBox(0.0F, -11.5F, -5.5F, 0.0F, 3.0F, 4.0F, NONE)
                .texOffs(24, 0).addBox(-1.0F, -9.0F, 5.0F, 2.0F, 4.0F, 1.0F, NONE)
                .texOffs(32, 2).addBox(-6.0F, -13.0F, 6.0F, 12.0F, 4.0F, 0.0F, NONE),
                PartPose.ZERO);
        head.addOrReplaceChild("horn", CubeListBuilder.create()
                .texOffs(44, 16).addBox(-0.5F, -15.0F, -3.0F, 1.0F, 8.0F, 2.0F, NONE),
                PartPose.rotation(0.34906584F, 0.0F, 0.0F));
        head.addOrReplaceChild("back_crest", CubeListBuilder.create()
                .texOffs(32, 0).addBox(-6.0F, -2.0F, 0.0F, 12.0F, 2.0F, 0.0F, NONE),
                PartPose.offsetAndRotation(0.0F, -13.0F, 6.0F, 0.5235988F, 0.0F, 0.0F));
    }

    private static void blackUruk(PartDefinition head) {
        head.addOrReplaceChild("crest", CubeListBuilder.create()
                .texOffs(32, 0).addBox(-8.0F, -16.0F, -3.0F, 16.0F, 10.0F, 0.0F, NONE),
                PartPose.rotation(-0.34906584F, 0.0F, 0.0F));
    }

    private static void uruk(PartDefinition head) {
        head.addOrReplaceChild("crest", CubeListBuilder.create()
                .texOffs(0, 22).addBox(-10.0F, -16.0F, -1.0F, 20.0F, 10.0F, 0.0F, NONE),
                PartPose.rotation(-0.17453292F, 0.0F, 0.0F));
        head.addOrReplaceChild("jaw", CubeListBuilder.create()
                .texOffs(0, 16).addBox(-6.0F, 2.0F, -4.0F, 12.0F, 6.0F, 0.0F, NONE),
                PartPose.rotation(-1.0471976F, 0.0F, 0.0F));
    }

    private static void gundabadUruk(PartDefinition head) {
        head.addOrReplaceChild("right_horn", CubeListBuilder.create()
                .texOffs(32, 0).addBox(-8.0F, -13.0F, -0.5F, 3.0F, 8.0F, 0.0F, NONE),
                PartPose.rotation(0.0F, 0.0F, 0.10471976F));
        head.addOrReplaceChild("left_horn", CubeListBuilder.create().mirror()
                .texOffs(32, 0).addBox(5.0F, -13.0F, -0.5F, 3.0F, 8.0F, 0.0F, NONE),
                PartPose.rotation(0.0F, 0.0F, -0.10471976F));
    }

    private static void haradTurban(PartDefinition head) {
        head.addOrReplaceChild("turban", CubeListBuilder.create()
                .texOffs(0, 0).addBox(-5.0F, -10.0F, -5.0F, 10.0F, 5.0F, 10.0F, NONE)
                .texOffs(0, 0).addBox(-1.0F, -9.0F, -6.0F, 2.0F, 2.0F, 1.0F, NONE),
                PartPose.ZERO);
        head.addOrReplaceChild("shawl", CubeListBuilder.create()
                .texOffs(0, 15).addBox(-4.5F, -5.0F, 1.5F, 9.0F, 6.0F, 4.0F, new CubeDeformation(0.25F)),
                PartPose.rotation(0.2268928F, 0.0F, 0.0F));
    }

    public enum Variant {
        GONDOR("helmet_gondor", "gondor"),
        HIGH_ELVEN("helmet_high_elven", "high_elven"),
        DORWINION_ELF("helmet_dorwinion_elf", "dorwinion_elf"),
        RHUN_GOLD("helmet_rhun_gold", "rhun_gold"),
        BLACK_URUK("helmet_black_uruk", "black_uruk"),
        URUK("helmet_uruk", "uruk"),
        GUNDABAD_URUK("helmet_gundabad_uruk", "gundabad_uruk"),
        HARAD_TURBAN("helmet_harad_robes", "harad_nomad");

        private final String itemPath;
        private final Identifier texture;
        private final ModelLayerLocation layer;

        Variant(String itemPath, String textureName) {
            this.itemPath = itemPath;
            this.texture = Identifier.fromNamespaceAndPath(Lotr_craft.MOD_ID, "textures/entity/equipment/humanoid/" + textureName + ".png");
            this.layer = new ModelLayerLocation(Identifier.fromNamespaceAndPath(Lotr_craft.MOD_ID, "custom_helmet/" + itemPath), "main");
        }

        public String itemPath() {
            return itemPath;
        }

        public Identifier texture() {
            return texture;
        }

        public ModelLayerLocation layer() {
            return layer;
        }
    }
}
