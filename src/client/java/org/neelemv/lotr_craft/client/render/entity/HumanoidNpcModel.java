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

public class HumanoidNpcModel extends HumanoidModel<HumanoidNpcRenderState> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(Identifier.fromNamespaceAndPath(Lotr_craft.MOD_ID, "humanoid_npc"), "main");
    private final ModelPart nose;
    private final ModelPart rightEar;
    private final ModelPart leftEar;

    public HumanoidNpcModel(ModelPart root) {
        super(root);
        this.nose = this.head.getChild("nose");
        this.rightEar = this.head.getChild("right_ear");
        this.leftEar = this.head.getChild("left_ear");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        CubeDeformation deformation = CubeDeformation.NONE;

        PartDefinition head = root.addOrReplaceChild("head",
                CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, deformation),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        head.addOrReplaceChild("hat",
                CubeListBuilder.create().texOffs(0, 32).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 16.0F, 8.0F, new CubeDeformation(0.5F)),
                PartPose.ZERO);
        head.addOrReplaceChild("nose",
                CubeListBuilder.create().texOffs(14, 17).addBox(-0.5F, -4.0F, -4.8F, 1.0F, 2.0F, 1.0F, deformation),
                PartPose.ZERO);
        head.addOrReplaceChild("right_ear",
                CubeListBuilder.create().texOffs(0, 0).addBox(-3.5F, -5.5F, 2.0F, 1.0F, 2.0F, 3.0F, deformation),
                PartPose.rotation(0.2617994F, -0.5235988F, -0.22689281F));
        head.addOrReplaceChild("left_ear",
                CubeListBuilder.create().texOffs(24, 0).addBox(2.5F, -5.5F, 2.0F, 1.0F, 2.0F, 3.0F, deformation),
                PartPose.rotation(0.2617994F, 0.5235988F, 0.22689281F));
        root.addOrReplaceChild("body",
                CubeListBuilder.create().texOffs(16, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, deformation),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        root.addOrReplaceChild("right_arm",
                CubeListBuilder.create().texOffs(40, 16).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, deformation),
                PartPose.offset(-5.0F, 2.0F, 0.0F));
        root.addOrReplaceChild("left_arm",
                CubeListBuilder.create().texOffs(40, 16).mirror().addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, deformation),
                PartPose.offset(5.0F, 2.0F, 0.0F));
        root.addOrReplaceChild("right_leg",
                CubeListBuilder.create().texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, deformation),
                PartPose.offset(-1.9F, 12.0F, 0.0F));
        root.addOrReplaceChild("left_leg",
                CubeListBuilder.create().texOffs(0, 16).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, deformation),
                PartPose.offset(1.9F, 12.0F, 0.0F));

        return LayerDefinition.create(mesh, 64, 64);
    }

    @Override
    public void setupAnim(HumanoidNpcRenderState state) {
        super.setupAnim(state);
        this.hat.visible = !state.orcModelFeatures;
        this.nose.visible = state.orcModelFeatures;
        this.rightEar.visible = state.orcModelFeatures;
        this.leftEar.visible = state.orcModelFeatures;
        correctNpcShieldBlockPose(state);
    }

    static void correctNpcShieldBlockPose(HumanoidModel<HumanoidNpcRenderState> model, HumanoidNpcRenderState state) {
        if (!state.offhandShieldBlocking) {
            return;
        }
        model.leftArm.yRot = model.head.yRot + 0.5235988F;
    }

    private void correctNpcShieldBlockPose(HumanoidNpcRenderState state) {
        correctNpcShieldBlockPose(this, state);
    }
}
