package com.lnatit.mtcc.client;

import com.lnatit.mtcc.item.TeaDrop;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Map;

import static com.lnatit.mtcc.MTCC.*;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModelRegistry
{
    @SubscribeEvent()
    public static void register(ModelEvent.RegisterAdditional evt)
    {

    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onModelBake(ModelEvent.ModifyBakingResult evt)
    {
        Map<ResourceLocation, BakedModel> modelMap = evt.getModels();
        ModelResourceLocation drop_0 = new ModelResourceLocation(MOD_ID, "drop_0", "inventory");
        modelMap.put(drop_0, new DropBakedModel(modelMap.get(drop_0)));
        // TODO register other drops
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onColorRegister(RegisterColorHandlersEvent.Item evt)
    {
        evt.register(
                (pStack, pTintIndex) ->
                {
                    ItemStack target = TeaDrop.imitateFromStack(pStack);
                    return target.getItem() instanceof TeaDrop ? -1
                            : evt.getItemColors().getColor(target, pTintIndex);
                },
                DROP_0.get(), DROP_1.get(), DROP_2.get(), DROP_3.get()
        );
    }
}
