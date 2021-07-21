package lnatit.mcardsth.handler;

import lnatit.mcardsth.entity.EntityTypeReg;
import lnatit.mcardsth.entity.CardRenderer;
import lnatit.mcardsth.item.AbstractCard;
import lnatit.mcardsth.item.ItemReg;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static deeplake.idlframework.idlnbtutils.IDLNBTConst.COUNT;
import static lnatit.mcardsth.MineCardsTouhou.MOD_ID;

/**
 * Register CardRenderer
 */
@Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventHandler
{
    @SubscribeEvent
    public static void onClientSetUpEvent(FMLClientSetupEvent event)
    {
        RenderingRegistry.registerEntityRenderingHandler(EntityTypeReg.CARD.get(),
                                                         (EntityRendererManager manager) ->
                                                                 new CardRenderer(manager, Minecraft.getInstance().getItemRenderer())
                                                        );

        event.enqueueWork(ClientEventHandler::registerProperties);
    }

    private static void registerProperties()
    {
        for (RegistryObject<Item> itemObj : ItemReg.ITEMS.getEntries())
        {
            Item item = itemObj.get();
            if (item instanceof AbstractCard)
                ItemModelsProperties
                        .registerProperty(itemObj.get(),
                                          new ResourceLocation("unlocked"),
                                          new UnlockedGetter());
        }
    }

    private static class UnlockedGetter implements IItemPropertyGetter
    {
        @Override
        public float call(@Nonnull ItemStack itemStack, @Nullable ClientWorld clientWorld, @Nullable LivingEntity entityIn)
        {
            if (entityIn instanceof PlayerEntity)
            {
                Item item = itemStack.getItem();
                boolean flag = entityIn.getPersistentData().getBoolean(item.getRegistryName().getNamespace());
                return flag ? 1 : 0;
            }
            return 0;
        }
    }

    private static class CountGetter implements IItemPropertyGetter
    {
        //TODO
        @Override
        public float call(@Nonnull ItemStack itemStack, @Nullable ClientWorld clientWorld, @Nullable LivingEntity entityIn)
        {
            if (entityIn instanceof PlayerEntity)
            {
                int count = entityIn.getPersistentData().getInt(COUNT);
                switch (count)
                {
                    case 0:

                }
            }
            return 0;
        }
    }
}
