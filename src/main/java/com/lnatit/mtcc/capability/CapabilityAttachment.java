package com.lnatit.mtcc.capability;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.lnatit.mtcc.MTCC.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CapabilityAttachment
{
//    @SubscribeEvent()
//    public static void onCapRegister(RegisterCapabilitiesEvent evt)
//    {
////        evt.register();
//    }

    @SubscribeEvent()
    public static void onAttachToPlayer(AttachCapabilitiesEvent<Entity> evt)
    {
        if (evt.getObject() instanceof Player)
            evt.addCapability(new ResourceLocation(MOD_ID, "prog_record"), new RecorderProvider());
    }

    @SubscribeEvent()
    public static void onAttachToLevel(AttachCapabilitiesEvent<Level> evt)
    {
        evt.addCapability(new ResourceLocation(MOD_ID, "prog_supply"), new SupplierProvider());
    }
}
