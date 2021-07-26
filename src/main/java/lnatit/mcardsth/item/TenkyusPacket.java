package lnatit.mcardsth.item;

import lnatit.mcardsth.gui.ClientGuiUtil;
import lnatit.mcardsth.gui.PacketScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import static lnatit.mcardsth.utils.PlayerPropertiesUtils.doPlayersAbilityEnabled;
import static lnatit.mcardsth.utils.PlayerPropertiesUtils.enablePlayerAbility;

public class TenkyusPacket extends Item
{
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
        else
            ClientGuiUtil.displayGuiScreen(worldIn, playerIn);
        return ActionResult.resultSuccess(playerIn.getHeldItem(handIn));
    }
}
