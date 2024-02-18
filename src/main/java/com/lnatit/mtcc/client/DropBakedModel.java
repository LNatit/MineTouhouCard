package com.lnatit.mtcc.client;

import com.lnatit.mtcc.item.TeaDrop;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.textures.UnitTextureAtlasSprite;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

public class DropBakedModel implements BakedModel
{
    public BakedModel defaultModel;

    public DropBakedModel(BakedModel defaultModel)
    {
        this.defaultModel = defaultModel;
    }

    @Override
    public @NotNull List<BakedQuad> getQuads(@Nullable BlockState p_235039_, @Nullable Direction p_235040_, @NotNull RandomSource p_235041_)
    {
        return Collections.emptyList();
    }

    @Override
    public boolean useAmbientOcclusion()
    {
        return false;
    }

    @Override
    public boolean isGui3d()
    {
        return false;
    }

    @Override
    public boolean usesBlockLight()
    {
        return false;
    }

    @Override
    public boolean isCustomRenderer()
    {
        return false;
    }

    @Override
    public @NotNull TextureAtlasSprite getParticleIcon()
    {
        return UnitTextureAtlasSprite.INSTANCE;
    }

    @Override
    public @NotNull ItemOverrides getOverrides()
    {
        return new DropHandler();
    }

    public static final class DropHandler extends ItemOverrides
    {
        @Override
        public BakedModel resolve(@NotNull BakedModel pModel, @NotNull ItemStack pStack, @Nullable ClientLevel pLevel, @Nullable LivingEntity pEntity, int pSeed)
        {
            if (!(pStack.getItem() instanceof TeaDrop))
                return super.resolve(pModel, pStack, pLevel, pEntity, pSeed);

            ItemStack imitated = TeaDrop.imitateFromStack(pStack);
            BakedModel bakedModel = Minecraft.getInstance().getItemRenderer().getItemModelShaper().getItemModel(imitated);
            if (bakedModel instanceof DropBakedModel dropBakedModel)
                bakedModel = dropBakedModel.defaultModel;
            return bakedModel.getOverrides().resolve(bakedModel, imitated, pLevel, pEntity, pSeed);
        }
    }
}
