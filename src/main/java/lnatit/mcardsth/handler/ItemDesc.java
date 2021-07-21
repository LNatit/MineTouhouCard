package lnatit.mcardsth.handler;

import lnatit.mcardsth.item.AbstractCard;
import lnatit.mcardsth.item.TenkyusPacket;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;

import static deeplake.idlframework.idlnbtutils.IDLNBTConst.COUNT;
import static deeplake.idlframework.idlnbtutils.IDLNBTUtils.GetInt;
import static lnatit.mcardsth.MineCardsTouhou.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID, value = Dist.CLIENT)
public class ItemDesc
{
    @SubscribeEvent
    public static void onItemDesc(ItemTooltipEvent event)
    {
        PlayerEntity player = event.getPlayer();
        ItemStack itemStack = event.getItemStack();
        Item item = itemStack.getItem();
        List<ITextComponent> info = new ArrayList<>(4);

        if (item.getRegistryName() == null)
            return;

        if (item instanceof AbstractCard)
        {
            for (int i = 0; i < 4; i++)
                info.add(new TranslationTextComponent(item.getRegistryName().toString() + ".desc_" + i));
        }
        else if (item instanceof TenkyusPacket)
        {
            int count = GetInt(player, COUNT, 0);
            for (int i = 0; i < 2; i++)
                info.add(new TranslationTextComponent(item.getRegistryName().toString() + ".desc_" + i));
            info.add(new TranslationTextComponent(item.getRegistryName().toString() + ".desc_2", count));
        }
        event.getToolTip().addAll(info);
    }
}
