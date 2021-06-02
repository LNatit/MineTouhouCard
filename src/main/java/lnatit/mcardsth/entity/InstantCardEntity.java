package lnatit.mcardsth.entity;

import lnatit.mcardsth.item.InstantCard;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

/**
 * TODO unfinished!!!
 */
public class InstantCardEntity extends Entity
{
    private static final DataParameter<ItemStack> CARD = EntityDataManager.createKey(ItemEntity.class, DataSerializers.ITEMSTACK);
    private int age;
    private int pickupDelay;
    private int health = 5;
    public final float hoverStart;
    /**
     * The maximum age of this EntityItem.  The item is expired once this is reached.
     */
    public int lifespan = 1200;

    public InstantCardEntity(EntityType<? extends InstantCardEntity> entityTypeIn, World worldIn)
    {
        super(entityTypeIn, worldIn);
        this.hoverStart = (float) (Math.random() * Math.PI * 2.0D);
        this.init();
    }

    public InstantCardEntity(World worldIn, double x, double y, double z)
    {
        this(EntityTypeReg.INSTANT_CARD.get(), worldIn);
        this.setPosition(x, y, z);
        this.rotationYaw = this.rand.nextFloat() * 360.0F;
        this.setMotion(this.rand.nextDouble() * 0.2D - 0.1D, 0.2D, this.rand.nextDouble() * 0.2D - 0.1D);
    }

    public InstantCardEntity(World worldIn, double x, double y, double z, InstantCard card)
    {
        this(worldIn, x, y, z);
        this.setCard(new ItemStack(card));
    }

    public InstantCardEntity(ItemEntity cardItem)
    {
        super(EntityTypeReg.INSTANT_CARD.get(), cardItem.world);
        this.setCard(cardItem.getItem());
        this.copyLocationAndAnglesFrom(cardItem);
        this.age = cardItem.getAge();
        this.hoverStart = cardItem.hoverStart;

    }

    public void init()
    {
        this.pickupDelay = 10;
        this.age = this.lifespan;
    }

    @Override
    protected void registerData()
    {
        this.getDataManager().register(CARD, ItemStack.EMPTY);
    }

    @Override
    public void tick()
    {
        if (this.getCard().isEmpty())
            this.remove();
        else
        {
            super.tick();
            if (this.pickupDelay == 0)
                this.pickupDelay--;

        }
    }

    @Override
    protected void readAdditional(CompoundNBT compound)
    {
        compound.putShort("Health", (short) this.health);
        compound.putShort("Age", (short) this.age);
        compound.putShort("PickupDelay", (short) this.pickupDelay);

        if (!this.getCard().isEmpty())
            compound.put("Item", this.getCard().write(new CompoundNBT()));
    }

    @Override
    protected void writeAdditional(CompoundNBT compound)
    {
        this.health = compound.getShort("Health");
        this.age = compound.getShort("Age");
        this.pickupDelay = compound.getShort("PickupDelay");

        if (compound.contains("Item"))
            this.setCard(ItemStack.read(compound.getCompound("Item")));
    }

    public int getAge()
    {
        return age;
    }

    public ItemStack getCard()
    {
        return this.dataManager.get(CARD);
    }

    public void setCard(ItemStack card)
    {
        if (card.getItem() instanceof InstantCard)
            this.dataManager.set(CARD, card);
    }

    @OnlyIn(Dist.CLIENT)
    public float getItemHover(float partialTicks) {
        return ((float)this.getAge() + partialTicks) / 20.0F + this.hoverStart;
    }

    @Override
    public IPacket<?> createSpawnPacket()
    {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
