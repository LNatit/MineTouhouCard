package lnatit.mcardsth.item;

import lnatit.mcardsth.gui.PacketContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import static lnatit.mcardsth.utils.PlayerPropertiesUtils.doPlayersAbilityEnabled;
import static lnatit.mcardsth.utils.PlayerPropertiesUtils.enablePlayerAbility;

public class TenkyusPacket extends Item
{
    private static final ITextComponent CONTAINER_NAME = new TranslationTextComponent("container.minecardstouhou.packetscreen");

    public TenkyusPacket()
    {
        super(new Item.Properties()
                      .group(CardGroup.CARDS)
                      .maxStackSize(1)
                      .rarity(Rarity.EPIC));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn)
    {
        if (!playerIn.isSneaking())
        {
            if (doPlayersAbilityEnabled(playerIn))
            {
                enablePlayerAbility(playerIn, false);
                if (!(playerIn instanceof ServerPlayerEntity))
                    playerIn.sendMessage(new TranslationTextComponent("disabled"), null);
            }
            else
            {
                enablePlayerAbility(playerIn, true);
                if (!(playerIn instanceof ServerPlayerEntity))
                    playerIn.sendMessage(new TranslationTextComponent("enabled"), null);
            }
        }
        else if (playerIn instanceof ServerPlayerEntity)
            NetworkHooks.openGui((ServerPlayerEntity) playerIn, getContainer());
        return ActionResult.resultSuccess(playerIn.getHeldItem(handIn));
    }

    public static INamedContainerProvider getContainer()
    {
        return new SimpleNamedContainerProvider((id, inventory, player) -> new PacketContainer(id, inventory), CONTAINER_NAME);
    }
}
