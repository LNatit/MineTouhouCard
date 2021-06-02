package lnatit.mcardsth.entity;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class InstantCardRenderer extends EntityRenderer<InstantCardEntity>
{
    public InstantCardRenderer(EntityRendererManager renderManager)
    {
        super(renderManager);
    }

    @Override
    public ResourceLocation getEntityTexture(InstantCardEntity entity)
    {
        return null;
    }
}
