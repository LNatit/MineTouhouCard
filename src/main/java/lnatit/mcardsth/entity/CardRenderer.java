package lnatit.mcardsth.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.culling.ClippingHelper;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

/**
 * TODO inspect the method.
 */
@OnlyIn(Dist.CLIENT)
public class CardRenderer extends EntityRenderer<CardEntity>
{
    private final net.minecraft.client.renderer.ItemRenderer itemRenderer;
    private final Random random = new Random();

    public CardRenderer(EntityRendererManager renderManagerIn, net.minecraft.client.renderer.ItemRenderer itemRendererIn)
    {
        super(renderManagerIn);
        this.itemRenderer = itemRendererIn;
        this.shadowSize = 0.15F;
        this.shadowOpaque = 0.75F;
    }

    public void render(CardEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn)
    {
        matrixStackIn.push();
        ItemStack itemstack = entityIn.getCard();
        int i = itemstack.isEmpty() ? 187 : Item.getIdFromItem(itemstack.getItem()) + itemstack.getDamage();
        this.random.setSeed((long) i);
        IBakedModel ibakedmodel = this.itemRenderer
                .getItemModelWithOverrides(itemstack, entityIn.world, (LivingEntity) null);
        boolean flag = ibakedmodel.isGui3d();
//        int j = 1;
        float f1 = MathHelper.sin(((float) entityIn.getAge() + partialTicks) / 10.0F + entityIn.hoverStart) * 0.1F + 0.1F;
        float f2 = shouldBob() ? ibakedmodel
                .getItemCameraTransforms()
                .getTransform(ItemCameraTransforms.TransformType.GROUND)
                .scale
                .getY() : 0;
        matrixStackIn.translate(0.0D, (double) (f1 + 0.25F * f2), 0.0D);
        float f3 = entityIn.getItemHover(partialTicks);
        matrixStackIn.rotate(Vector3f.YP.rotation(f3));
        if (!flag)
//        {
//            float f7 = -0.0F * (float) (j - 1) * 0.5F;
//            float f8 = -0.0F * (float) (j - 1) * 0.5F;
//            float f9 = -0.09375F * (float) (j - 1) * 0.5F;
            matrixStackIn.translate((double) 0, (double) 0, (double) 0);
//        }

//        for(int k = 0; k < j; ++k)
//        {
        matrixStackIn.push();
//            if (k > 0)
//            {
//                if (flag)
//                {
//                    float f11 = (this.random.nextFloat() * 2.0F - 1.0F) * 0.15F;
//                    float f13 = (this.random.nextFloat() * 2.0F - 1.0F) * 0.15F;
//                    float f10 = (this.random.nextFloat() * 2.0F - 1.0F) * 0.15F;
//                    matrixStackIn.translate(shouldSpreadItems() ? f11 : 0, shouldSpreadItems() ? f13 : 0, shouldSpreadItems() ? f10 : 0);
//                } else
//                {
//                    float f12 = (this.random.nextFloat() * 2.0F - 1.0F) * 0.15F * 0.5F;
//                    float f14 = (this.random.nextFloat() * 2.0F - 1.0F) * 0.15F * 0.5F;
//                    matrixStackIn.translate(shouldSpreadItems() ? f12 : 0, shouldSpreadItems() ? f14 : 0, 0.0D);
//                }
//            }

        this.itemRenderer.renderItem(itemstack, ItemCameraTransforms.TransformType.GROUND, false, matrixStackIn, bufferIn, packedLightIn, OverlayTexture.NO_OVERLAY, ibakedmodel);
        matrixStackIn.pop();
        if (!flag)
            matrixStackIn.translate(0.0, 0.0, 0.09375F);
//        }

        matrixStackIn.pop();
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    @Override
//    public ResourceLocation getEntityTexture(InstantCardEntity entity)
//    {
//        return new ResourceLocation(MOD_ID, "");
//    }

    /**
     * Returns the location of an entity's texture.
     */
    public ResourceLocation getEntityTexture(CardEntity entity)
    {
        return PlayerContainer.LOCATION_BLOCKS_TEXTURE;
    }

    /*==================================== FORGE START ===========================================*/

    /**
     * @return If items should spread out when rendered in 3D
     */
    public boolean shouldSpreadItems()
    {
        return true;
    }

    /**
     * @return If items should have a bob effect
     */
    public boolean shouldBob()
    {
        return true;
    }

    @Override
    public boolean shouldRender(CardEntity livingEntityIn, ClippingHelper camera, double camX, double camY, double camZ)
    {
        return super.shouldRender(livingEntityIn, camera, camX, camY, camZ);
    }
}
