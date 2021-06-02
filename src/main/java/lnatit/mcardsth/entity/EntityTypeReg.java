package lnatit.mcardsth.entity;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static lnatit.mcardsth.MineCardsTouhou.MOD_ID;

public class EntityTypeReg
{
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, MOD_ID);

    public static final RegistryObject<EntityType<InstantCardEntity>> INSTANT_CARD =
            ENTITIES.register("instant_card_entity", () ->
                    EntityType.Builder
                            .create((EntityType.IFactory<InstantCardEntity>) InstantCardEntity::new, EntityClassification.MISC)
                            .size(0.25F, 0.25F)
                            .trackingRange(6)
                            .updateInterval(20)
                            .build("instant_card_entity")
            );
}
