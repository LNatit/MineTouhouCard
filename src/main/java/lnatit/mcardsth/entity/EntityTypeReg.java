package lnatit.mcardsth.entity;

import net.minecraft.entity.EntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static lnatit.mcardsth.MineCardsTouhou.MOD_ID;

public class EntityTypeReg
{
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, MOD_ID);

    public static final RegistryObject<EntityType<CardEntity>> CARD =
            ENTITIES.register("card_entity", () -> CardEntity.TYPE);
}
