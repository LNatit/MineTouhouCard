package lnatit.mcardsth.handler;

import lnatit.mcardsth.entity.CardEntity;
import lnatit.mcardsth.entity.CardRenderer;
import lnatit.mcardsth.entity.EntityTypeReg;
import lnatit.mcardsth.item.AbstractCard;
import lnatit.mcardsth.item.ItemReg;
import lnatit.mcardsth.item.TenkyusPacket;
import lnatit.mcardsth.utils.Config;
import lnatit.mcardsth.utils.PlayerPropertiesUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ArmorStandEntity;
import net.minecraft.entity.item.ItemFrameEntity;
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
                        .registerProperty(item,
                                          new ResourceLocation(MOD_ID, itemObj.get().getRegistryName().getPath() + ".unlocked"),
                                          new UnlockedGetter());

            if (item instanceof TenkyusPacket)
                ItemModelsProperties
                        .registerProperty(item,
                                          new ResourceLocation(MOD_ID, itemObj.get().getRegistryName().getPath() + ".count"),
                                          new CountGetter());
        }
    }

    private static class UnlockedGetter implements IItemPropertyGetter
    {
        @Override
        public float call(@Nonnull ItemStack itemStack, @Nullable ClientWorld clientWorld, @Nullable LivingEntity entityIn)
        {
            boolean flag = false;
            if (entityIn instanceof ClientPlayerEntity)
            {
                Item item = itemStack.getItem();
                flag = PlayerPropertiesUtils.doPlayerCollected((PlayerEntity) entityIn, (AbstractCard) item);
            }
            else
            {
                Entity attachedEntity = itemStack.getAttachedEntity();
                if (attachedEntity instanceof CardEntity)
                    flag = true;
                else if (Config.ITEM_FRAME_DISPLAY.get() && attachedEntity instanceof ItemFrameEntity)
                    flag = true;
                else if (Config.ARMOR_STAND_DISPLAY.get() && attachedEntity instanceof ArmorStandEntity)
                    flag = true;
            }
            return flag ? 1 : 0;
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
                int count = PlayerPropertiesUtils.playerCardsTotal((PlayerEntity) entityIn);
                switch (count)
                {
                    case 0:

                }
            }
            return 0;
        }
    }
}
