package lnatit.mcardsth.item;

import lnatit.mcardsth.utils.PlayerData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import static lnatit.mcardsth.LogUtils.LOGGER;
import static lnatit.mcardsth.LogUtils.Warn;
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

        if (cardCollection(playerIn, itemstack) || onCardUse(playerIn))
        {
            itemstack.shrink(1);
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
                    flag = true;
                    if (player.experienceLevel < 35)
                        player.addExperienceLevel(5);
                    else if (player.experienceLevel < 40)
                        player.experienceLevel = 40;
                    else flag = false;
                    break;
                case "mokou":
                    flag = data.Extend();
                    data.Extend();
                    data.Extend();
                    break;
                default:
                    Warn("trying to use an Instant card with Registry name: " + cardName);
                    break;
            }
        }

        data.ApplyAndSync(player);

        return flag;
    }
}
