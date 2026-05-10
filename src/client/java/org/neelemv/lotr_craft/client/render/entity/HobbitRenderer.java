package org.neelemv.lotr_craft.client.render.entity;

import java.util.UUID;

import org.neelemv.lotr_craft.Lotr_craft;
import org.neelemv.lotr_craft.entity.HobbitKind;
import org.neelemv.lotr_craft.entity.LotrHobbitEntity;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.resources.Identifier;

public class HobbitRenderer extends HumanoidMobRenderer<LotrHobbitEntity, HobbitRenderState, HumanoidModel<HobbitRenderState>> {
    private static final Identifier FALLBACK_TEXTURE = texture("hobbit_male/0");

    public HobbitRenderer(EntityRendererProvider.Context context) {
        super(context, new HobbitModel(context.bakeLayer(HobbitModel.LAYER_LOCATION)), 0.35F);
        addLayer(new HobbitOutfitLayer(this));
    }

    @Override
    public HobbitRenderState createRenderState() {
        return new HobbitRenderState();
    }

    @Override
    public void extractRenderState(LotrHobbitEntity hobbit, HobbitRenderState state, float partialTick) {
        super.extractRenderState(hobbit, state, partialTick);
        state.texture = skinTexture(hobbit);
        state.outfitTexture = outfitTexture(hobbit.kind());
    }

    @Override
    public Identifier getTextureLocation(HobbitRenderState state) {
        return state.texture == null ? FALLBACK_TEXTURE : state.texture;
    }

    @Override
    protected void scale(HobbitRenderState state, PoseStack poseStack) {
        poseStack.scale(0.75F, 0.75F, 0.75F);
    }

    private static Identifier skinTexture(LotrHobbitEntity hobbit) {
        UUID uuid = hobbit.getUUID();
        long bits = uuid.getMostSignificantBits() ^ uuid.getLeastSignificantBits();
        boolean child = Math.floorMod(bits, 10) == 0;
        boolean male = (bits & 1L) == 0L;
        int variants = child ? 3 : 13;
        int index = Math.floorMod(bits >> 1, variants);
        String folder = child ? male ? "child_male" : "child_female" : male ? "hobbit_male" : "hobbit_female";
        return texture(folder + "/" + index);
    }

    private static Identifier outfitTexture(HobbitKind kind) {
        if (kind.outfitTextureName() == null) {
            return null;
        }
        return texture(kind.outfitTextureName());
    }

    private static Identifier texture(String path) {
        return Identifier.fromNamespaceAndPath(Lotr_craft.MOD_ID, "textures/entity/hobbit/" + path + ".png");
    }
}
