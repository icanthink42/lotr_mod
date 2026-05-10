package org.neelemv.lotr_craft.client.render.entity;

import org.neelemv.lotr_craft.Lotr_craft;
import org.neelemv.lotr_craft.entity.PassiveKind;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.Identifier;

public class PassiveModel extends EntityModel<PassiveRenderState> {
    private static final CubeDeformation NONE = CubeDeformation.NONE;

    public static ModelLayerLocation layerLocation(PassiveKind kind) {
        return new ModelLayerLocation(Identifier.fromNamespaceAndPath(Lotr_craft.MOD_ID, "passive_" + kind.id()), "main");
    }

    public PassiveModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition createBodyLayer(PassiveKind kind) {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        int textureWidth = 64;
        int textureHeight = 32;

        switch (kind) {
            case BUTTERFLY -> butterfly(root);
            case MIDGES -> midge(root);
            case BIRD -> bird(root);
            case FISH -> fish(root);
            case RABBIT -> rabbit(root);
            case BOAR -> pigLike(root, true);
            case LION, LIONESS -> {
                textureWidth = 128;
                textureHeight = 64;
                lion(root, kind == PassiveKind.LION);
            }
            case GIRAFFE -> {
                textureWidth = 128;
                textureHeight = 64;
                giraffe(root);
            }
            case RHINO -> {
                textureWidth = 128;
                textureHeight = 128;
                rhino(root);
            }
            case CROCODILE -> {
                textureWidth = 128;
                textureHeight = 128;
                crocodile(root);
            }
            case GEMSBOK -> {
                textureWidth = 128;
                textureHeight = 64;
                gemsbok(root);
            }
            case FLAMINGO -> flamingo(root);
            case CAMEL -> {
                textureWidth = 64;
                textureHeight = 64;
                camel(root);
            }
            case ELK -> {
                textureWidth = 128;
                textureHeight = 64;
                elk(root);
            }
            case TERMITE -> termite(root);
            case DIK_DIK -> dikDik(root);
            case SWAN -> {
                textureWidth = 64;
                textureHeight = 64;
                swan(root);
            }
            case DEER -> {
                textureWidth = 64;
                textureHeight = 64;
                deer(root);
            }
            case AUROCHS -> {
                textureWidth = 128;
                textureHeight = 64;
                aurochs(root);
            }
            case BEAR -> {
                textureWidth = 128;
                textureHeight = 64;
                bear(root);
            }
        }

        return LayerDefinition.create(mesh, textureWidth, textureHeight);
    }

    private static void butterfly(PartDefinition root) {
        PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create()
                .texOffs(0, 0).addBox(-1.0F, -6.0F, -1.0F, 2.0F, 12.0F, 2.0F, NONE),
                PartPose.offsetAndRotation(0.0F, 11.0F, 0.0F, 0.7853982F, 0.0F, 0.0F));
        body.addOrReplaceChild("right_wing", CubeListBuilder.create()
                .texOffs(10, 0).addBox(-12.0F, -10.5F, 0.0F, 12.0F, 21.0F, 0.0F, NONE),
                PartPose.rotation(0.0F, 0.45F, 0.0F));
        body.addOrReplaceChild("left_wing", CubeListBuilder.create().mirror()
                .texOffs(10, 0).addBox(0.0F, -10.5F, 0.0F, 12.0F, 21.0F, 0.0F, NONE),
                PartPose.rotation(0.0F, -0.45F, 0.0F));
    }

    private static void midge(PartDefinition root) {
        PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create()
                .texOffs(0, 0).addBox(-0.5F, -1.5F, -0.5F, 1.0F, 5.0F, 1.0F, NONE),
                PartPose.offsetAndRotation(0.0F, 9.0F, 0.0F, 0.7853982F, 0.0F, 0.0F));
        body.addOrReplaceChild("right_wing", CubeListBuilder.create()
                .texOffs(0, 6).addBox(-5.0F, -2.5F, 0.0F, 5.0F, 5.0F, 1.0F, NONE),
                PartPose.rotation(0.0F, 0.45F, 0.0F));
        body.addOrReplaceChild("left_wing", CubeListBuilder.create().mirror()
                .texOffs(0, 6).addBox(0.0F, -2.5F, 0.0F, 5.0F, 5.0F, 1.0F, NONE),
                PartPose.rotation(0.0F, -0.45F, 0.0F));
    }

    private static void bird(PartDefinition root) {
        PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create()
                .texOffs(0, 7).addBox(-1.5F, -2.0F, -2.0F, 3.0F, 3.0F, 5.0F, NONE)
                .texOffs(8, 0).addBox(-1.0F, -1.5F, 3.0F, 2.0F, 1.0F, 3.0F, NONE)
                .texOffs(8, 4).addBox(-1.0F, -0.5F, 3.0F, 2.0F, 1.0F, 2.0F, NONE),
                PartPose.offset(0.0F, 21.0F, 0.0F));
        body.addOrReplaceChild("head", CubeListBuilder.create()
                .texOffs(0, 0).addBox(-1.0F, -1.5F, -1.5F, 2.0F, 2.0F, 2.0F, NONE)
                .texOffs(0, 4).addBox(-0.5F, -0.5F, -2.5F, 1.0F, 1.0F, 1.0F, NONE)
                .texOffs(15, 0).addBox(-0.5F, -0.5F, -3.5F, 1.0F, 1.0F, 2.0F, NONE),
                PartPose.offset(0.0F, -2.0F, -2.0F));
        body.addOrReplaceChild("right_wing", CubeListBuilder.create()
                .texOffs(16, 7).addBox(0.0F, 0.0F, -2.0F, 0.0F, 5.0F, 4.0F, NONE),
                PartPose.offsetAndRotation(-1.5F, -1.5F, 0.5F, 0.0F, 0.0F, 1.15F));
        body.addOrReplaceChild("left_wing", CubeListBuilder.create().mirror()
                .texOffs(16, 7).addBox(0.0F, 0.0F, -2.0F, 0.0F, 5.0F, 4.0F, NONE),
                PartPose.offsetAndRotation(1.5F, -1.5F, 0.5F, 0.0F, 0.0F, -1.15F));
        body.addOrReplaceChild("right_leg", CubeListBuilder.create()
                .texOffs(0, 16).addBox(-1.0F, 0.0F, -1.5F, 1.0F, 2.0F, 2.0F, NONE),
                PartPose.offset(-0.3F, 1.0F, 0.5F));
        body.addOrReplaceChild("left_leg", CubeListBuilder.create().mirror()
                .texOffs(0, 16).addBox(0.0F, 0.0F, -1.5F, 1.0F, 2.0F, 2.0F, NONE),
                PartPose.offset(0.3F, 1.0F, 0.5F));
    }

    private static void fish(PartDefinition root) {
        PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create()
                .texOffs(0, 0).addBox(-0.5F, -2.0F, -3.0F, 1.0F, 3.0F, 6.0F, NONE),
                PartPose.offset(0.0F, 22.0F, -1.0F));
        body.addOrReplaceChild("top_fin", CubeListBuilder.create()
                .texOffs(14, 0).addBox(0.0F, -2.0F, 0.0F, 0.0F, 2.0F, 4.0F, NONE),
                PartPose.offset(0.0F, 0.0F, -1.5F));
        body.addOrReplaceChild("right_fin", CubeListBuilder.create()
                .texOffs(22, 0).addBox(-0.5F, -1.0F, 0.0F, 0.0F, 2.0F, 3.0F, NONE),
                PartPose.offset(0.0F, 0.0F, -1.0F));
        body.addOrReplaceChild("left_fin", CubeListBuilder.create().mirror()
                .texOffs(22, 0).addBox(0.5F, -1.0F, 0.0F, 0.0F, 2.0F, 3.0F, NONE),
                PartPose.offset(0.0F, 0.0F, -1.0F));
        body.addOrReplaceChild("tail_fin", CubeListBuilder.create()
                .texOffs(0, 9).addBox(0.0F, -5.0F, 0.0F, 0.0F, 5.0F, 5.0F, NONE),
                PartPose.offset(0.0F, -0.5F, 1.5F));
    }

    private static void rabbit(PartDefinition root) {
        PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create()
                .texOffs(0, 19).addBox(-2.5F, -4.0F, -2.5F, 5.0F, 8.0F, 5.0F, NONE)
                .texOffs(0, 14).addBox(-1.5F, -6.0F, -1.5F, 3.0F, 2.0F, 3.0F, NONE),
                PartPose.offsetAndRotation(0.0F, 18.5F, 0.0F, 1.0F, 0.0F, 0.0F));
        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create()
                .texOffs(0, 0).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, NONE)
                .texOffs(0, 8).addBox(-1.5F, 0.0F, -3.0F, 3.0F, 2.0F, 2.0F, NONE),
                PartPose.offsetAndRotation(0.0F, -7.0F, 0.0F, -0.8F, 0.0F, 0.0F));
        head.addOrReplaceChild("right_ear", CubeListBuilder.create()
                .texOffs(16, 0).addBox(-1.2F, -4.5F, -0.5F, 2.0F, 5.0F, 1.0F, NONE),
                PartPose.offsetAndRotation(-1.0F, -1.5F, 0.0F, -0.349F, 0.0F, 0.0F));
        head.addOrReplaceChild("left_ear", CubeListBuilder.create().mirror()
                .texOffs(16, 0).addBox(-0.8F, -4.5F, -0.5F, 2.0F, 5.0F, 1.0F, NONE),
                PartPose.offsetAndRotation(1.0F, -1.5F, 0.0F, -0.349F, 0.0F, 0.0F));
        body.addOrReplaceChild("tail", CubeListBuilder.create()
                .texOffs(32, 30).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, NONE),
                PartPose.offsetAndRotation(0.0F, 4.5F, 2.5F, -0.785F, 0.0F, 0.0F));
        body.addOrReplaceChild("right_arm", CubeListBuilder.create()
                .texOffs(32, 0).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 4.0F, 1.0F, NONE),
                PartPose.offsetAndRotation(-1.5F, -2.0F, -2.5F, -1.1F, 0.0F, 0.0F));
        body.addOrReplaceChild("left_arm", CubeListBuilder.create().mirror()
                .texOffs(32, 0).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 4.0F, 1.0F, NONE),
                PartPose.offsetAndRotation(1.5F, -2.0F, -2.5F, -1.1F, 0.0F, 0.0F));
        root.addOrReplaceChild("right_leg", CubeListBuilder.create()
                .texOffs(32, 8).addBox(-1.0F, -2.0F, -2.0F, 2.0F, 4.0F, 4.0F, NONE)
                .texOffs(32, 16).addBox(-1.0F, 2.0F, -3.5F, 2.0F, 1.0F, 3.0F, NONE),
                PartPose.offsetAndRotation(-3.0F, 21.5F, 1.0F, 0.262F, 0.0F, 0.0F));
        root.addOrReplaceChild("left_leg", CubeListBuilder.create().mirror()
                .texOffs(32, 8).addBox(-1.0F, -2.0F, -2.0F, 2.0F, 4.0F, 4.0F, NONE)
                .texOffs(32, 16).addBox(-1.0F, 2.0F, -3.5F, 2.0F, 1.0F, 3.0F, NONE),
                PartPose.offsetAndRotation(3.0F, 21.5F, 1.0F, 0.262F, 0.0F, 0.0F));
    }

    private static void pigLike(PartDefinition root, boolean boar) {
        PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create()
                .texOffs(0, 0).addBox(-4.0F, -4.0F, -8.0F, 8.0F, 8.0F, 8.0F, NONE)
                .texOffs(16, 16).addBox(-2.0F, 0.0F, -9.0F, 4.0F, 3.0F, 1.0F, NONE)
                .texOffs(24, 0).addBox(-3.0F, 0.0F, -10.0F, 6.0F, 4.0F, 2.0F, NONE)
                .texOffs(40, 0).addBox(-5.0F, -5.0F, -6.0F, 1.0F, 2.0F, 2.0F, NONE)
                .texOffs(40, 0).mirror().addBox(4.0F, -5.0F, -6.0F, 1.0F, 2.0F, 2.0F, NONE),
                PartPose.offset(0.0F, 12.0F, -6.0F));
        if (boar) {
            head.addOrReplaceChild("tusks", CubeListBuilder.create()
                    .texOffs(0, 0).addBox(-4.0F, 2.0F, -11.0F, 1.0F, 1.0F, 2.0F, NONE)
                    .texOffs(1, 1).addBox(-4.0F, 1.0F, -11.5F, 1.0F, 1.0F, 1.0F, NONE)
                    .texOffs(0, 0).mirror().addBox(3.0F, 2.0F, -11.0F, 1.0F, 1.0F, 2.0F, NONE)
                    .texOffs(1, 1).mirror().addBox(3.0F, 1.0F, -11.5F, 1.0F, 1.0F, 1.0F, NONE),
                    PartPose.ZERO);
        }
        root.addOrReplaceChild("body", CubeListBuilder.create()
                .texOffs(28, 8).addBox(-5.0F, -10.0F, -7.0F, 10.0F, 16.0F, 8.0F, NONE),
                PartPose.offsetAndRotation(0.0F, 11.0F, 2.0F, 1.5707964F, 0.0F, 0.0F));
        leg(root, "right_hind_leg", 0, 16, -3.0F, 18.0F, 7.0F, 4, 6, 4);
        leg(root, "left_hind_leg", 0, 16, 3.0F, 18.0F, 7.0F, 4, 6, 4);
        leg(root, "right_front_leg", 0, 16, -3.0F, 18.0F, -5.0F, 4, 6, 4);
        leg(root, "left_front_leg", 0, 16, 3.0F, 18.0F, -5.0F, 4, 6, 4);
    }

    private static void lion(PartDefinition root, boolean mane) {
        root.addOrReplaceChild("head", CubeListBuilder.create()
                .texOffs(48, 0).addBox(-5.0F, -6.0F, -10.0F, 10.0F, 10.0F, 10.0F, NONE)
                .texOffs(78, 0).addBox(-3.0F, -1.0F, -14.0F, 6.0F, 5.0F, 4.0F, NONE)
                .texOffs(98, 0).addBox(-1.0F, -2.0F, -14.2F, 2.0F, 2.0F, 5.0F, NONE)
                .texOffs(0, 0).addBox(-4.0F, -9.0F, -7.5F, 3.0F, 3.0F, 1.0F, NONE)
                .texOffs(0, 0).mirror().addBox(1.0F, -9.0F, -7.5F, 3.0F, 3.0F, 1.0F, NONE),
                PartPose.offset(0.0F, 3.0F, -10.0F));
        if (mane) {
            root.addOrReplaceChild("mane", CubeListBuilder.create()
                    .texOffs(0, 0).addBox(-8.0F, -10.0F, -6.0F, 16.0F, 16.0F, 8.0F, NONE),
                    PartPose.offset(0.0F, 3.0F, -10.0F));
        }
        root.addOrReplaceChild("body", CubeListBuilder.create()
                .texOffs(0, 24).addBox(-7.0F, -6.5F, -11.0F, 14.0F, 14.0F, 24.0F, NONE),
                PartPose.offset(0.0F, 6.0F, 1.0F));
        root.addOrReplaceChild("tail", CubeListBuilder.create()
                .texOffs(100, 50).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 12.0F, NONE)
                .texOffs(86, 57).addBox(-1.5F, -1.5F, 12.0F, 3.0F, 3.0F, 4.0F, NONE),
                PartPose.offsetAndRotation(0.0F, 4.0F, 13.0F, -1.047F, 0.0F, 0.0F));
        splitLeg(root, "leg1", 52, 24, 106, 24, -4.0F, 4.0F, 11.0F, true);
        splitLeg(root, "leg2", 52, 24, 106, 24, 4.0F, 4.0F, 11.0F, false);
        splitLeg(root, "leg3", 80, 24, 106, 24, -4.0F, 5.0F, -5.0F, true);
        splitLeg(root, "leg4", 80, 24, 106, 24, 4.0F, 5.0F, -5.0F, false);
    }

    private static void giraffe(PartDefinition root) {
        root.addOrReplaceChild("body", CubeListBuilder.create()
                .texOffs(0, 0).addBox(-6.0F, -8.0F, -13.0F, 12.0F, 16.0F, 26.0F, NONE),
                PartPose.offset(0.0F, -11.0F, 0.0F));
        root.addOrReplaceChild("neck", CubeListBuilder.create()
                .texOffs(0, 44).addBox(-4.5F, -13.0F, -4.5F, 9.0F, 11.0F, 9.0F, NONE)
                .texOffs(78, 0).addBox(-3.0F, -37.0F, -3.0F, 6.0F, 40.0F, 6.0F, NONE),
                PartPose.offset(0.0F, -14.0F, -7.0F));
        root.addOrReplaceChild("head", CubeListBuilder.create()
                .texOffs(96, 48).addBox(-3.0F, -43.0F, -6.0F, 6.0F, 6.0F, 10.0F, NONE)
                .texOffs(10, 0).addBox(-4.0F, -45.0F, 1.5F, 1.0F, 3.0F, 2.0F, NONE)
                .texOffs(17, 0).addBox(3.0F, -45.0F, 1.5F, 1.0F, 3.0F, 2.0F, NONE)
                .texOffs(0, 0).addBox(-2.5F, -47.0F, 0.0F, 1.0F, 4.0F, 1.0F, NONE)
                .texOffs(5, 0).addBox(1.5F, -47.0F, 0.0F, 1.0F, 4.0F, 1.0F, NONE)
                .texOffs(76, 56).addBox(-2.0F, -41.0F, -11.0F, 4.0F, 3.0F, 5.0F, NONE),
                PartPose.offset(0.0F, -14.0F, -7.0F));
        root.addOrReplaceChild("tail", CubeListBuilder.create()
                .texOffs(104, 0).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 24.0F, 1.0F, NONE),
                PartPose.offset(0.0F, -12.0F, 13.0F));
        leg(root, "leg1", 112, 0, -3.9F, -3.0F, 8.0F, 4, 27, 4);
        leg(root, "leg2", 112, 0, 3.9F, -3.0F, 8.0F, 4, 27, 4);
        leg(root, "leg3", 112, 0, -3.9F, -3.0F, -7.0F, 4, 27, 4);
        leg(root, "leg4", 112, 0, 3.9F, -3.0F, -7.0F, 4, 27, 4);
    }

    private static void rhino(PartDefinition root) {
        root.addOrReplaceChild("head", CubeListBuilder.create()
                .texOffs(0, 0).addBox(-5.0F, -2.0F, -22.0F, 10.0F, 10.0F, 16.0F, NONE)
                .texOffs(0, 0).addBox(-4.0F, -4.0F, -10.0F, 1.0F, 2.0F, 2.0F, NONE)
                .texOffs(0, 0).mirror().addBox(3.0F, -4.0F, -10.0F, 1.0F, 2.0F, 2.0F, NONE)
                .texOffs(36, 0).addBox(-1.0F, -14.0F, -20.0F, 2.0F, 8.0F, 2.0F, NONE)
                .texOffs(44, 0).addBox(-1.0F, -3.0F, -17.0F, 2.0F, 4.0F, 2.0F, NONE),
                PartPose.offset(0.0F, 3.0F, -12.0F));
        root.addOrReplaceChild("neck", CubeListBuilder.create()
                .texOffs(52, 0).addBox(-7.0F, -4.0F, -7.0F, 14.0F, 13.0F, 8.0F, NONE),
                PartPose.offset(0.0F, 3.0F, -12.0F));
        root.addOrReplaceChild("body", CubeListBuilder.create()
                .texOffs(0, 26).addBox(-8.0F, -7.0F, -13.0F, 16.0F, 16.0F, 34.0F, NONE),
                PartPose.offset(0.0F, 5.0F, 0.0F));
        root.addOrReplaceChild("tail", CubeListBuilder.create()
                .texOffs(100, 63).addBox(-1.5F, -1.0F, -1.0F, 3.0F, 8.0F, 2.0F, NONE),
                PartPose.offset(0.0F, 7.0F, 21.0F));
        splitLeg(root, "leg1", 30, 76, 0, 95, -8.0F, 3.0F, 14.0F, true);
        splitLeg(root, "leg2", 30, 76, 0, 95, 8.0F, 3.0F, 14.0F, false);
        splitLeg(root, "leg3", 0, 76, 0, 95, -8.0F, 4.0F, -6.0F, true);
        splitLeg(root, "leg4", 0, 76, 0, 95, 8.0F, 4.0F, -6.0F, false);
    }

    private static void crocodile(PartDefinition root) {
        root.addOrReplaceChild("body", CubeListBuilder.create()
                .texOffs(18, 83).addBox(-8.0F, -5.0F, 0.0F, 16.0F, 9.0F, 36.0F, NONE),
                PartPose.offset(0.0F, 17.0F, -16.0F));
        root.addOrReplaceChild("tail1", CubeListBuilder.create()
                .texOffs(0, 28).addBox(-7.0F, 0.0F, 0.0F, 14.0F, 7.0F, 19.0F, NONE),
                PartPose.offset(0.0F, 13.0F, 18.0F));
        root.addOrReplaceChild("tail2", CubeListBuilder.create()
                .texOffs(0, 55).addBox(-6.0F, 1.5F, 17.0F, 12.0F, 5.0F, 16.0F, NONE),
                PartPose.offset(0.0F, 13.0F, 18.0F));
        root.addOrReplaceChild("tail3", CubeListBuilder.create()
                .texOffs(0, 77).addBox(-5.0F, 3.0F, 31.0F, 10.0F, 3.0F, 14.0F, NONE),
                PartPose.offset(0.0F, 13.0F, 18.0F));
        root.addOrReplaceChild("jaw", CubeListBuilder.create()
                .texOffs(58, 18).addBox(-6.5F, 0.3F, -19.0F, 13.0F, 4.0F, 19.0F, NONE),
                PartPose.offset(0.0F, 17.0F, -16.0F));
        root.addOrReplaceChild("head", CubeListBuilder.create()
                .texOffs(0, 0).addBox(-7.5F, -6.0F, -21.0F, 15.0F, 6.0F, 21.0F, NONE),
                PartPose.offset(0.0F, 18.5F, -16.0F));
        root.addOrReplaceChild("spines", CubeListBuilder.create()
                .texOffs(46, 45).addBox(-5.0F, 0.0F, 0.0F, 10.0F, 4.0F, 32.0F, NONE),
                PartPose.offsetAndRotation(0.0F, 9.5F, -14.0F, -0.035F, 0.0F, 0.0F));
        root.addOrReplaceChild("leg_front_left", CubeListBuilder.create().texOffs(2, 104).addBox(0.0F, 0.0F, -3.0F, 16.0F, 3.0F, 6.0F, NONE), PartPose.offsetAndRotation(6.0F, 15.0F, -11.0F, 0.0F, 0.0F, 0.436F));
        root.addOrReplaceChild("leg_back_left", CubeListBuilder.create().texOffs(2, 104).addBox(0.0F, 0.0F, -3.0F, 16.0F, 3.0F, 6.0F, NONE), PartPose.offsetAndRotation(6.0F, 15.0F, 15.0F, 0.0F, 0.0F, 0.436F));
        root.addOrReplaceChild("leg_front_right", CubeListBuilder.create().mirror().texOffs(2, 104).addBox(-16.0F, 0.0F, -3.0F, 16.0F, 3.0F, 6.0F, NONE), PartPose.offsetAndRotation(-6.0F, 15.0F, -11.0F, 0.0F, 0.0F, -0.436F));
        root.addOrReplaceChild("leg_back_right", CubeListBuilder.create().mirror().texOffs(2, 104).addBox(-16.0F, 0.0F, -3.0F, 16.0F, 3.0F, 6.0F, NONE), PartPose.offsetAndRotation(-6.0F, 15.0F, 15.0F, 0.0F, 0.0F, -0.436F));
    }

    private static void gemsbok(PartDefinition root) {
        root.addOrReplaceChild("head", CubeListBuilder.create()
                .texOffs(28, 0).addBox(-3.0F, -10.0F, -6.0F, 6.0F, 7.0F, 12.0F, NONE)
                .texOffs(0, 0).addBox(-2.8F, -9.5F, 5.8F, 1.0F, 1.0F, 13.0F, NONE)
                .texOffs(0, 0).mirror().addBox(1.8F, -9.5F, 5.8F, 1.0F, 1.0F, 13.0F, NONE)
                .texOffs(28, 19).addBox(-3.8F, -12.0F, 3.0F, 1.0F, 3.0F, 2.0F, NONE)
                .texOffs(34, 19).addBox(2.8F, -12.0F, 3.0F, 1.0F, 3.0F, 2.0F, NONE),
                PartPose.offset(0.0F, 4.0F, -9.0F));
        root.addOrReplaceChild("neck", CubeListBuilder.create()
                .texOffs(0, 14).addBox(-2.5F, -6.0F, -5.0F, 5.0F, 8.0F, 9.0F, NONE),
                PartPose.offset(0.0F, 4.0F, -9.0F));
        root.addOrReplaceChild("body", CubeListBuilder.create()
                .texOffs(0, 31).addBox(-7.0F, -10.0F, -7.0F, 13.0F, 10.0F, 23.0F, NONE),
                PartPose.offset(0.5F, 12.0F, -3.0F));
        root.addOrReplaceChild("tail", CubeListBuilder.create()
                .texOffs(0, 0).addBox(0.0F, 0.0F, 0.0F, 2.0F, 12.0F, 2.0F, NONE),
                PartPose.offset(-1.0F, 3.0F, 11.0F));
        leg(root, "leg1", 0, 38, -4.0F, 12.0F, 10.0F, 4, 12, 4);
        leg(root, "leg2", 0, 38, 4.0F, 12.0F, 10.0F, 4, 12, 4);
        leg(root, "leg3", 0, 38, -4.0F, 12.0F, -7.0F, 4, 12, 4);
        leg(root, "leg4", 0, 38, 4.0F, 12.0F, -7.0F, 4, 12, 4);
    }

    private static void flamingo(PartDefinition root) {
        root.addOrReplaceChild("head", CubeListBuilder.create()
                .texOffs(8, 24).addBox(-2.0F, -17.0F, -2.0F, 4.0F, 4.0F, 4.0F, NONE)
                .texOffs(24, 27).addBox(-1.5F, -16.0F, -5.0F, 3.0F, 2.0F, 3.0F, NONE)
                .texOffs(36, 30).addBox(-1.0F, -14.0F, -5.0F, 2.0F, 1.0F, 1.0F, NONE)
                .texOffs(0, 16).addBox(-1.0F, -15.0F, -1.0F, 2.0F, 14.0F, 2.0F, NONE),
                PartPose.offset(0.0F, 5.0F, -2.0F));
        root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, 0.0F, -4.0F, 6.0F, 7.0F, 8.0F, NONE), PartPose.offset(0.0F, 3.0F, 0.0F));
        root.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(42, 23).addBox(-2.5F, 0.0F, 0.0F, 5.0F, 3.0F, 6.0F, NONE), PartPose.offsetAndRotation(0.0F, 4.0F, 3.0F, -0.25F, 0.0F, 0.0F));
        root.addOrReplaceChild("left_wing", CubeListBuilder.create().texOffs(36, 0).addBox(-1.0F, 0.0F, -3.0F, 1.0F, 8.0F, 6.0F, NONE), PartPose.offset(-3.0F, 3.0F, 0.0F));
        root.addOrReplaceChild("right_wing", CubeListBuilder.create().texOffs(50, 0).addBox(0.0F, 0.0F, -3.0F, 1.0F, 8.0F, 6.0F, NONE), PartPose.offset(3.0F, 3.0F, 0.0F));
        root.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(30, 0).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 16.0F, 1.0F, NONE).texOffs(30, 17).addBox(-1.5F, 14.9F, -3.5F, 3.0F, 1.0F, 3.0F, NONE), PartPose.offset(-2.0F, 8.0F, 0.0F));
        root.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(30, 0).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 16.0F, 1.0F, NONE).texOffs(30, 17).addBox(-1.5F, 14.9F, -3.5F, 3.0F, 1.0F, 3.0F, NONE), PartPose.offset(2.0F, 8.0F, 0.0F));
    }

    private static void camel(PartDefinition root) {
        root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 16).addBox(-4.5F, -5.0F, -10.0F, 9.0F, 10.0F, 22.0F, NONE), PartPose.offset(0.0F, 10.0F, 0.0F));
        root.addOrReplaceChild("humps", CubeListBuilder.create().texOffs(34, 0).addBox(-3.0F, -9.0F, -8.0F, 6.0F, 4.0F, 6.0F, NONE).addBox(-3.0F, -9.0F, 5.0F, 6.0F, 4.0F, 6.0F, NONE), PartPose.offset(0.0F, 10.0F, 0.0F));
        root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -13.0F, -10.5F, 6.0F, 5.0F, 11.0F, NONE).addBox(-2.5F, -15.0F, -1.0F, 2.0F, 2.0F, 1.0F, NONE).mirror().addBox(0.5F, -15.0F, -1.0F, 2.0F, 2.0F, 1.0F, NONE).texOffs(0, 16).addBox(-2.5F, -9.0F, -5.0F, 5.0F, 14.0F, 5.0F, NONE), PartPose.offset(0.0F, 6.0F, -10.0F));
        root.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(54, 52).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 10.0F, 2.0F, NONE), PartPose.offset(0.0F, 7.0F, 12.0F));
        camelLeg(root, "leg1", -4.5F, 7.0F, 8.0F, true);
        camelLeg(root, "leg2", 4.5F, 7.0F, 8.0F, false);
        camelLeg(root, "leg3", -4.5F, 7.0F, -5.0F, true);
        camelLeg(root, "leg4", 4.5F, 7.0F, -5.0F, false);
    }

    private static void elk(PartDefinition root) {
        root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, -4.0F, -21.0F, 12.0F, 11.0F, 26.0F, NONE).texOffs(0, 54).addBox(-1.0F, -5.0F, 2.0F, 2.0F, 2.0F, 8.0F, NONE), PartPose.offset(0.0F, 4.0F, 9.0F));
        root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(50, 0).addBox(-2.0F, -10.0F, -4.0F, 4.0F, 12.0F, 8.0F, NONE).texOffs(74, 0).addBox(-3.0F, -16.0F, -8.0F, 6.0F, 6.0F, 13.0F, NONE).texOffs(0, 0).addBox(10.0F, -19.0F, 2.5F, 1.0F, 12.0F, 1.0F, NONE).mirror().addBox(-11.0F, -19.0F, 2.5F, 1.0F, 12.0F, 1.0F, NONE), PartPose.offset(0.0F, 4.0F, -10.0F));
        splitLeg(root, "leg1", 42, 37, 26, 37, -4.0F, 3.0F, 8.0F, true);
        splitLeg(root, "leg2", 42, 37, 26, 37, 4.0F, 3.0F, 8.0F, false);
        splitLeg(root, "leg3", 0, 37, 26, 37, -4.0F, 4.0F, -6.0F, true);
        splitLeg(root, "leg4", 0, 37, 26, 37, 4.0F, 4.0F, -6.0F, false);
    }

    private static void termite(PartDefinition root) {
        root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(10, 5).addBox(0.0F, 0.0F, 0.0F, 6.0F, 6.0F, 21.0F, NONE), PartPose.offset(-3.0F, 17.0F, -5.0F));
        root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, 0.0F, 0.0F, 8.0F, 8.0F, 7.0F, NONE), PartPose.offset(-4.0F, 14.0F, -10.0F));
        for (int i = 0; i < 3; i++) {
            root.addOrReplaceChild("leg_l" + i, CubeListBuilder.create().texOffs(34, 0).addBox(-12.0F, -1.0F, -1.0F, 13.0F, 2.0F, 2.0F, NONE), PartPose.offset(-2.0F, 19.0F, 1.0F - i));
            root.addOrReplaceChild("leg_r" + i, CubeListBuilder.create().texOffs(34, 0).addBox(-1.0F, -1.0F, -1.0F, 13.0F, 2.0F, 2.0F, NONE), PartPose.offset(2.0F, 19.0F, 1.0F - i));
        }
        root.addOrReplaceChild("right_feeler", CubeListBuilder.create().texOffs(50, 18).addBox(0.0F, 0.0F, -8.0F, 1.0F, 1.0F, 6.0F, NONE), PartPose.offsetAndRotation(-3.0F, 15.0F, -8.0F, 0.0F, -0.35F, 0.0F));
        root.addOrReplaceChild("left_feeler", CubeListBuilder.create().texOffs(50, 18).addBox(0.0F, 0.0F, -8.0F, 1.0F, 1.0F, 6.0F, NONE), PartPose.offsetAndRotation(2.0F, 15.0F, -8.0F, 0.0F, 0.35F, 0.0F));
    }

    private static void dikDik(PartDefinition root) {
        root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(42, 23).addBox(-2.0F, -9.0F, -3.0F, 4.0F, 4.0F, 5.0F, NONE).texOffs(18, 28).addBox(-1.0F, -7.3F, -5.0F, 2.0F, 2.0F, 2.0F, NONE).texOffs(0, 27).addBox(-2.8F, -11.0F, 0.5F, 1.0F, 3.0F, 2.0F, NONE).texOffs(8, 27).addBox(1.8F, -11.0F, 0.5F, 1.0F, 3.0F, 2.0F, NONE).texOffs(0, 21).addBox(-1.5F, -11.0F, 0.0F, 1.0F, 2.0F, 1.0F, NONE).mirror().addBox(0.5F, -11.0F, 0.0F, 1.0F, 2.0F, 1.0F, NONE).texOffs(28, 22).addBox(-1.5F, -8.0F, -2.0F, 3.0F, 7.0F, 3.0F, NONE), PartPose.offset(0.0F, 11.0F, -4.5F));
        root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, 0.0F, 0.0F, 6.0F, 6.0F, 14.0F, NONE), PartPose.offset(0.0F, 9.0F, -7.0F));
        leg(root, "leg1", 56, 0, -1.7F, 14.0F, 5.0F, 2, 10, 2);
        leg(root, "leg2", 56, 0, 1.7F, 14.0F, 5.0F, 2, 10, 2);
        leg(root, "leg3", 56, 0, -1.7F, 14.0F, -5.0F, 2, 10, 2);
        leg(root, "leg4", 56, 0, 1.7F, 14.0F, -5.0F, 2, 10, 2);
    }

    private static void swan(PartDefinition root) {
        PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -3.0F, -7.0F, 8.0F, 6.0F, 14.0F, NONE), PartPose.offset(0.0F, 18.0F, 0.0F));
        body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(24, 20).addBox(-3.0F, -1.5F, -1.0F, 6.0F, 4.0F, 4.0F, NONE).texOffs(24, 28).addBox(-2.0F, -1.0F, 3.0F, 4.0F, 2.0F, 3.0F, NONE), PartPose.offset(0.0F, -2.0F, 7.0F));
        PartDefinition neck = body.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(44, 11).addBox(-1.0F, -11.0F, -3.0F, 2.0F, 13.0F, 2.0F, NONE), PartPose.offset(0.0F, 0.0F, -5.5F));
        neck.addOrReplaceChild("head", CubeListBuilder.create().texOffs(44, 0).addBox(-1.5F, -2.0F, -4.0F, 3.0F, 3.0F, 4.0F, NONE).texOffs(44, 7).addBox(-1.0F, -0.5F, -7.0F, 2.0F, 1.0F, 3.0F, NONE), PartPose.offset(0.0F, -10.0F, -2.0F));
        root.addOrReplaceChild("right_wing", CubeListBuilder.create().texOffs(0, 20).addBox(-1.0F, -3.5F, -1.0F, 1.0F, 7.0F, 8.0F, NONE).texOffs(0, 35).addBox(-1.0F, -4.5F, 7.0F, 1.0F, 6.0F, 3.0F, NONE).texOffs(8, 35).addBox(-1.0F, -5.5F, 10.0F, 1.0F, 5.0F, 3.0F, NONE), PartPose.offset(-4.0F, 18.0F, -5.0F));
        root.addOrReplaceChild("left_wing", CubeListBuilder.create().mirror().texOffs(0, 20).addBox(0.0F, -3.5F, -1.0F, 1.0F, 7.0F, 8.0F, NONE).texOffs(0, 35).addBox(0.0F, -4.5F, 7.0F, 1.0F, 6.0F, 3.0F, NONE).texOffs(8, 35).addBox(0.0F, -5.5F, 10.0F, 1.0F, 5.0F, 3.0F, NONE), PartPose.offset(4.0F, 18.0F, -5.0F));
        root.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(24, 33).addBox(-1.5F, 0.0F, -3.0F, 3.0F, 3.0F, 3.0F, NONE), PartPose.offset(-2.0F, 21.0F, 1.0F));
        root.addOrReplaceChild("left_leg", CubeListBuilder.create().mirror().texOffs(24, 33).addBox(-1.5F, 0.0F, -3.0F, 3.0F, 3.0F, 3.0F, NONE), PartPose.offset(2.0F, 21.0F, 1.0F));
    }

    private static void deer(PartDefinition root) {
        root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-3.5F, -4.0F, -7.0F, 7.0F, 7.0F, 15.0F, NONE), PartPose.offset(0.0F, 14.0F, 0.0F));
        root.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(20, 58).addBox(-1.5F, -8.0F, 3.0F, 3.0F, 2.0F, 4.0F, NONE), PartPose.offset(0.0F, 14.0F, 0.0F));
        root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 22).addBox(-2.5F, -8.0F, -6.0F, 5.0F, 4.0F, 7.0F, NONE).texOffs(24, 22).addBox(-2.0F, -4.0F, -4.0F, 4.0F, 7.0F, 4.0F, NONE).texOffs(0, 33).addBox(-2.5F, -15.0F, 0.5F, 1.0F, 9.0F, 1.0F, NONE).mirror().addBox(1.5F, -15.0F, 0.5F, 1.0F, 9.0F, 1.0F, NONE), PartPose.offset(0.0F, 11.0F, -5.0F));
        splitSmallLeg(root, "leg1", 12, 46, 12, 56, -4.0F, 14.0F, 5.0F);
        splitSmallLeg(root, "leg2", 12, 46, 12, 56, 4.0F, 14.0F, 5.0F);
        splitSmallLeg(root, "leg3", 0, 47, 0, 56, -3.0F, 14.0F, -4.0F);
        splitSmallLeg(root, "leg4", 0, 47, 0, 56, 3.0F, 14.0F, -4.0F);
    }

    private static void aurochs(PartDefinition root) {
        root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -6.0F, -11.0F, 16.0F, 16.0F, 26.0F, NONE).texOffs(28, 42).addBox(-8.0F, -8.0F, -8.0F, 16.0F, 2.0F, 10.0F, NONE).texOffs(84, 31).addBox(-3.0F, 10.0F, 4.0F, 6.0F, 1.0F, 6.0F, NONE), PartPose.offset(0.0F, 2.0F, -1.0F));
        root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(58, 0).addBox(-5.0F, -4.0F, -12.0F, 10.0F, 10.0F, 11.0F, NONE).texOffs(89, 0).addBox(-3.0F, 1.0F, -15.0F, 6.0F, 4.0F, 4.0F, NONE).texOffs(105, 0).addBox(-8.0F, -2.5F, -7.0F, 3.0F, 2.0F, 1.0F, NONE).mirror().addBox(5.0F, -2.5F, -7.0F, 3.0F, 2.0F, 1.0F, NONE).texOffs(98, 21).addBox(-6.0F, -5.0F, -6.5F, 12.0F, 3.0F, 3.0F, NONE).texOffs(112, 27).addBox(-11.0F, -4.5F, -6.0F, 6.0F, 2.0F, 2.0F, NONE).mirror().addBox(5.0F, -4.5F, -6.0F, 6.0F, 2.0F, 2.0F, NONE), PartPose.offset(0.0F, -1.0F, -10.0F));
        root.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(20, 42).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 12.0F, 1.0F, NONE), PartPose.offset(0.0F, 1.0F, 14.0F));
        leg(root, "leg1", 0, 42, -5.0F, 12.0F, 9.0F, 5, 12, 5);
        leg(root, "leg2", 0, 42, 5.0F, 12.0F, 9.0F, 5, 12, 5);
        leg(root, "leg3", 0, 42, -5.0F, 12.0F, -7.0F, 5, 12, 5);
        leg(root, "leg4", 0, 42, 5.0F, 12.0F, -7.0F, 5, 12, 5);
    }

    private static void bear(PartDefinition root) {
        root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(32, 0).addBox(-4.0F, -5.0F, -4.0F, 8.0F, 9.0F, 6.0F, NONE).texOffs(0, 0).addBox(-4.5F, -5.5F, -11.0F, 9.0F, 10.0F, 7.0F, NONE).texOffs(0, 17).addBox(-2.5F, -2.0F, -17.0F, 5.0F, 6.0F, 6.0F, NONE).texOffs(0, 29).addBox(-1.5F, -2.5F, -17.5F, 3.0F, 3.0F, 7.0F, NONE).texOffs(23, 17).addBox(-4.0F, -8.0F, -6.0F, 3.0F, 3.0F, 1.0F, NONE).mirror().addBox(1.0F, -8.0F, -6.0F, 3.0F, 3.0F, 1.0F, NONE), PartPose.offsetAndRotation(0.0F, 8.0F, -9.0F, 0.175F, 0.0F, 0.0F));
        root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(40, 0).addBox(-6.0F, -8.0F, -9.0F, 12.0F, 14.0F, 28.0F, NONE).texOffs(92, 0).addBox(-2.5F, -6.0F, 19.0F, 5.0F, 5.0F, 2.0F, NONE), PartPose.offset(0.0F, 10.0F, -2.0F));
        splitLeg(root, "leg1", 56, 44, 86, 44, -4.0F, 6.0F, 10.0F, true);
        splitLeg(root, "leg2", 56, 44, 86, 44, 4.0F, 6.0F, 10.0F, false);
        splitLeg(root, "leg3", 0, 44, 28, 44, -3.0F, 6.0F, -5.0F, true);
        splitLeg(root, "leg4", 0, 44, 28, 44, 3.0F, 6.0F, -5.0F, false);
    }

    private static void leg(PartDefinition root, String name, int u, int v, float x, float y, float z, int w, int h, int d) {
        root.addOrReplaceChild(name, CubeListBuilder.create()
                .texOffs(u, v).addBox(-w / 2.0F, 0.0F, -d / 2.0F, w, h, d, NONE),
                PartPose.offset(x, y, z));
    }

    private static void splitLeg(PartDefinition root, String name, int upperU, int upperV, int lowerU, int lowerV, float x, float y, float z, boolean leftSide) {
        float sx = leftSide ? -6.0F : 0.0F;
        float lx = leftSide ? -5.5F : 0.5F;
        root.addOrReplaceChild(name, CubeListBuilder.create()
                .texOffs(upperU, upperV).addBox(sx, -2.0F, -3.5F, 6.0F, 10.0F, 8.0F, NONE)
                .texOffs(lowerU, lowerV).addBox(lx, 8.0F, -2.5F, 5.0F, 12.0F, 5.0F, NONE),
                PartPose.offset(x, y, z));
    }

    private static void splitSmallLeg(PartDefinition root, String name, int upperU, int upperV, int lowerU, int lowerV, float x, float y, float z) {
        root.addOrReplaceChild(name, CubeListBuilder.create()
                .texOffs(upperU, upperV).addBox(-1.5F, -2.0F, -1.5F, 3.0F, 6.0F, 3.0F, NONE)
                .texOffs(lowerU, lowerV).addBox(-1.0F, 4.0F, -1.0F, 2.0F, 6.0F, 2.0F, NONE),
                PartPose.offset(x, y, z));
    }

    private static void camelLeg(PartDefinition root, String name, float x, float y, float z, boolean leftSide) {
        float sx = leftSide ? -4.0F : 0.0F;
        float lx = leftSide ? -3.5F : 0.5F;
        float fx = leftSide ? -4.0F : 0.0F;
        root.addOrReplaceChild(name, CubeListBuilder.create()
                .texOffs(0, 52).addBox(sx, -1.0F, -2.5F, 4.0F, 7.0F, 5.0F, NONE)
                .texOffs(18, 53).addBox(lx, 6.0F, -1.5F, 3.0F, 8.0F, 3.0F, NONE)
                .texOffs(30, 57).addBox(fx, 14.0F, -2.0F, 4.0F, 3.0F, 4.0F, NONE),
                PartPose.offset(x, y, z));
    }
}
