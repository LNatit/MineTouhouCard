package lnatit.mcardsth.item;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

import static lnatit.mcardsth.utils.PlayerPropertiesUtils.*;

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
        if (playerIn.isSneaking())
        {
            if (doPlayersAbilityEnabled(playerIn))
            {
                enablePlayerAbility(playerIn, false);
                if (!(playerIn instanceof ServerPlayerEntity))
                    playerIn.sendMessage(new TranslationTextComponent("enabled"), null);
            }
            else
            {
                enablePlayerAbility(playerIn, true);
                if (!(playerIn instanceof ServerPlayerEntity))
                    playerIn.sendMessage(new TranslationTextComponent("disabled"), null);
            }
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }
}
