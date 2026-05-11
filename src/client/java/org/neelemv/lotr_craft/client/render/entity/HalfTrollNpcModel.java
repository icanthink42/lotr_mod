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
import net.minecraft.resources.Identifier;

public class HalfTrollNpcModel extends HumanoidModel<HumanoidNpcRenderState> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(Identifier.fromNamespaceAndPath(Lotr_craft.MOD_ID, "half_troll_npc"), "main");

    private final ModelPart mohawk;
    private final ModelPart hornRight1;
    private final ModelPart hornRight2;
    private final ModelPart hornLeft1;
    private final ModelPart hornLeft2;

    public HalfTrollNpcModel(ModelPart root) {
        super(root);
        this.mohawk = this.head.getChild("mohawk");
        this.hornRight1 = this.head.getChild("horn_right_1");
        this.hornRight2 = this.head.getChild("horn_right_2");
        this.hornLeft1 = this.head.getChild("horn_left_1");
        this.hornLeft2 = this.head.getChild("horn_left_2");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        CubeDeformation deformation = CubeDeformation.NONE;

        PartDefinition head = root.addOrReplaceChild("head",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-5.0F, -10.0F, -5.0F, 10.0F, 10.0F, 10.0F, deformation)
                        .texOffs(40, 5).addBox(-4.0F, -3.0F, -7.0F, 8.0F, 3.0F, 2.0F, deformation),
                PartPose.offset(0.0F, -8.0F, 0.0F));
        head.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.ZERO);
        head.addOrReplaceChild("nose",
                CubeListBuilder.create().texOffs(30, 0).addBox(-1.0F, -4.5F, -8.0F, 2.0F, 3.0F, 3.0F, deformation),
                PartPose.rotation(-0.34906584F, 0.0F, 0.0F));
        head.addOrReplaceChild("teeth",
                CubeListBuilder.create()
                        .texOffs(60, 7).addBox(-3.5F, -7.5F, -5.0F, 1.0F, 2.0F, 1.0F, deformation)
                        .texOffs(60, 7).mirror().addBox(2.5F, -7.5F, -5.0F, 1.0F, 2.0F, 1.0F, deformation),
                PartPose.rotation(0.5235988F, 0.0F, 0.0F));
        head.addOrReplaceChild("ear_right",
                CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -6.0F, -2.0F, 1.0F, 3.0F, 3.0F, deformation),
                PartPose.rotation(0.0F, -0.61086524F, 0.0F));
        head.addOrReplaceChild("ear_left",
                CubeListBuilder.create().texOffs(0, 0).mirror().addBox(4.0F, -6.0F, -2.0F, 1.0F, 3.0F, 3.0F, deformation),
                PartPose.rotation(0.0F, 0.61086524F, 0.0F));
        head.addOrReplaceChild("mohawk",
                CubeListBuilder.create().texOffs(40, 10).addBox(-1.0F, -12.5F, -1.5F, 2.0F, 10.0F, 8.0F, deformation),
                PartPose.ZERO);
        head.addOrReplaceChild("horn_right_1",
                CubeListBuilder.create().texOffs(40, 0).addBox(-10.0F, -8.0F, 1.0F, 3.0F, 2.0F, 2.0F, deformation),
                PartPose.rotation(0.0F, 0.0F, 0.34906584F));
        head.addOrReplaceChild("horn_right_2",
                CubeListBuilder.create().texOffs(50, 2).addBox(-14.5F, -4.0F, 1.5F, 3.0F, 1.0F, 1.0F, deformation),
                PartPose.rotation(0.0F, 0.0F, 0.6981317F));
        head.addOrReplaceChild("horn_left_1",
                CubeListBuilder.create().texOffs(40, 0).mirror().addBox(7.0F, -8.0F, 1.0F, 3.0F, 2.0F, 2.0F, deformation),
                PartPose.rotation(0.0F, 0.0F, -0.34906584F));
        head.addOrReplaceChild("horn_left_2",
                CubeListBuilder.create().texOffs(50, 2).mirror().addBox(11.5F, -4.0F, 1.5F, 3.0F, 1.0F, 1.0F, deformation),
                PartPose.rotation(0.0F, 0.0F, -0.6981317F));

        root.addOrReplaceChild("body",
                CubeListBuilder.create().texOffs(0, 20).addBox(-6.0F, 0.0F, -4.0F, 12.0F, 16.0F, 8.0F, deformation),
                PartPose.offset(0.0F, -8.0F, 0.0F));
        root.addOrReplaceChild("right_arm",
                CubeListBuilder.create()
                        .texOffs(20, 50).addBox(-3.5F, -2.0F, -3.0F, 6.0F, 8.0F, 6.0F, deformation)
                        .texOffs(0, 49).addBox(-3.0F, 6.0F, -2.5F, 5.0F, 10.0F, 5.0F, deformation),
                PartPose.offset(-8.5F, -6.0F, 0.0F));
        root.addOrReplaceChild("left_arm",
                CubeListBuilder.create()
                        .texOffs(20, 50).mirror().addBox(-2.5F, -2.0F, -3.0F, 6.0F, 8.0F, 6.0F, deformation)
                        .texOffs(0, 49).mirror().addBox(-2.0F, 6.0F, -2.5F, 5.0F, 10.0F, 5.0F, deformation),
                PartPose.offset(8.5F, -6.0F, 0.0F));
        root.addOrReplaceChild("right_leg",
                CubeListBuilder.create().texOffs(40, 28).addBox(-3.0F, 0.0F, -3.0F, 6.0F, 16.0F, 6.0F, deformation),
                PartPose.offset(-3.2F, 8.0F, 0.0F));
        root.addOrReplaceChild("left_leg",
                CubeListBuilder.create().texOffs(40, 28).mirror().addBox(-3.0F, 0.0F, -3.0F, 6.0F, 16.0F, 6.0F, deformation),
                PartPose.offset(3.2F, 8.0F, 0.0F));

        return LayerDefinition.create(mesh, 64, 64);
    }

    @Override
    public void setupAnim(HumanoidNpcRenderState state) {
        super.setupAnim(state);
        this.hat.visible = false;
        this.mohawk.visible = state.halfTrollMohawk;
        this.hornRight1.visible = state.halfTrollHorns;
        this.hornLeft1.visible = state.halfTrollHorns;
        this.hornRight2.visible = state.halfTrollFullHorns;
        this.hornLeft2.visible = state.halfTrollFullHorns;
    }
}
