package lnatit.mcardsth.gui;

import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static lnatit.mcardsth.MineCardsTouhou.MOD_ID;

public class ContainerTypeReg
{
    public static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, MOD_ID);

    public static final RegistryObject<ContainerType<?>> PACKET = CONTAINERS.register("packet", () -> IForgeContainerType.create(((windowId, inv, data) -> new PacketContainer(windowId, inv))));
}
