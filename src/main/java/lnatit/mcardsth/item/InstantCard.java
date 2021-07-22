package lnatit.mcardsth.item;

import lnatit.mcardsth.utils.AdvancementUtils;
import lnatit.mcardsth.utils.PlayerData;
import lnatit.mcardsth.utils.PlayerPropertiesUtils;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import static net.minecraft.item.Items.EMERALD;

public class InstantCard extends AbstractCard
{
    public InstantCard()
    {
        super(new Item.Properties().group(CardGroup.CARDS).maxStackSize(1).rarity(Rarity.RARE));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn)
    {
        ItemStack itemstack = playerIn.getHeldItem(handIn);

        if (cardCollection(playerIn) || onCardUse(playerIn))
        {
            itemstack.shrink(1);
            if (playerIn instanceof ServerPlayerEntity)
                CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayerEntity) playerIn, itemstack);
            return ActionResult.func_233538_a_(itemstack, worldIn.isRemote());
        }
        else return ActionResult.resultFail(itemstack);
    }

    private boolean onCardUse(PlayerEntity player)
    {
        boolean flag = false;
        PlayerData data = new PlayerData(player);

        if (this.getRegistryName() != null)
        {
            String cardName = this.getRegistryName().getPath();

            switch (cardName)
            {
                case "extend":
                    flag = data.Extend();
                    break;
                case "bomb":
                    data.addBomb();
                    flag = true;
                    break;
                case "extend2":
                    flag = data.addLifeFragment();
                    break;
                case "bomb2":
                    data.addBombFragment();
                    flag = true;
                    break;
                case "pendulum":
                    player.inventory.addItemStackToInventory(new ItemStack(EMERALD, 12));
                    flag = true;
                    break;
                case "dango":
                    //TODO dango to Power
//                    flag = playerGetDango(player);
                    break;
                case "mokou":
                    flag = data.Extend();
                    data.Extend();
                    data.Extend();
                    break;
            }
        }

        data.ApplyAndSync(player);

        return flag;
    }
}
