package lnatit.mcardsth.handler;

import lnatit.mcardsth.item.AbstractCard;
import lnatit.mcardsth.item.TenkyusPacket;
import lnatit.mcardsth.utils.PlayerPropertiesUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;

import static deeplake.idlframework.idlnbtutils.IDLNBT.*;
import static deeplake.idlframework.idlnbtutils.IDLNBTConst.COUNT;
import static lnatit.mcardsth.MineCardsTouhou.MOD_ID;
import static lnatit.mcardsth.utils.PlayerPropertiesUtils.*;

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
            if (!doPlayerCollected(player, (AbstractCard) item))
                info.add(new TranslationTextComponent("desc." + MOD_ID + "." + item.getRegistryName().getPath() + "_unknown"));
            else
            {
                for (int i = 0; i < 4; i++)
                {
                    TranslationTextComponent text = new TranslationTextComponent("desc." + MOD_ID + "." + item.getRegistryName().getPath() + "_" + (i + 1));

                    text.mergeStyle(TextFormatting.AQUA);

                    info.add(text);
                }
            }
        }
        else if (item instanceof TenkyusPacket)
        {
            int count = getPlayerIdeallandIntSafe(player, COUNT);

            for (int i = 0; i < 2; i++)
                info.add(new TranslationTextComponent("desc." + MOD_ID + "." + item.getRegistryName().getPath() + "_" + (i + 1)));

            TranslationTextComponent text1 = new TranslationTextComponent("desc." + MOD_ID + "." + item.getRegistryName().getPath() + "_" + 3, count);

            text1.mergeStyle(TextFormatting.GOLD);

            info.add(text1);
        }

        event.getToolTip().addAll(info);
    }
}
