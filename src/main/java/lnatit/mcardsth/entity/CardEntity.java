package lnatit.mcardsth.entity;


import lnatit.mcardsth.item.AbstractCard;
import lnatit.mcardsth.item.ItemReg;
import lnatit.mcardsth.utils.AdvancementUtils;
import lnatit.mcardsth.utils.PlayerPropertiesUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

import static deeplake.idlframework.idlnbtutils.IDLNBTUtils.GetBoolean;
import static deeplake.idlframework.idlnbtutils.IDLNBTUtils.SetBoolean;
import static net.minecraft.item.Items.DEBUG_STICK;

/**
 * TODO unfinished!!! sound effect undo!!
 */
public class CardEntity extends Entity
{
    public static EntityType<CardEntity> TYPE = EntityType.Builder
            .<CardEntity>create(CardEntity::new, EntityClassification.MISC)
            .size(0.25F, 0.25F)
            .trackingRange(6)
            .updateInterval(20)
            .build("card_entity");

    private static final DataParameter<ItemStack> CARD = EntityDataManager.createKey(CardEntity.class, DataSerializers.ITEMSTACK);
    private int age;
    private int interactDelay;
    private int health = 5;
    private boolean isImmortal = false;
    public final float hoverStart;
    /**
     * The maximum age of this EntityItem.  The item is expired once this is reached.
     */
    public int lifespan = 1200;

    public CardEntity(EntityType<? extends CardEntity> entityTypeIn, World worldIn)
    {
        super(entityTypeIn, worldIn);
        this.hoverStart = (float) (Math.random() * Math.PI * 2.0D);
        this.init();
    }

    public CardEntity(World worldIn, double x, double y, double z)
    {
        this(EntityTypeReg.CARD.get(), worldIn);
        this.setPosition(x, y, z);
        this.rotationYaw = this.rand.nextFloat() * 360.0F;
    }

    public CardEntity(World worldIn, double x, double y, double z, AbstractCard card)
    {
        this(worldIn, x, y, z);
        this.setCard(new ItemStack(card));
    }

    public CardEntity(ItemEntity cardItem)
    {
        super(EntityTypeReg.CARD.get(), cardItem.world);
        this.init();
        this.setCard(cardItem.getItem());
        this.copyLocationAndAnglesFrom(cardItem);
        this.hoverStart = cardItem.hoverStart;
        this.setCustomName(cardItem.getItem().getDisplayName());
    }

    public void init()
    {
        this.interactDelay = 10;
        this.age = 1;
        this.setCustomNameVisible(true);
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
            if (this.interactDelay > 0 && this.interactDelay != 32767)
                this.interactDelay--;

            this.prevPosX = this.getPosX();
            this.prevPosY = this.getPosY();
            this.prevPosZ = this.getPosZ();
            Vector3d vector3d = this.getMotion();
            float f = this.getEyeHeight() - 0.11111111F;
            if (this.isInWater() && this.func_233571_b_(FluidTags.WATER) > (double) f)
            {
                this.applyFloatMotion();
            }
            else if (this.isInLava() && this.func_233571_b_(FluidTags.LAVA) > (double) f)
            {
                this.func_234274_v_();
            }
            else if (!this.hasNoGravity())
            {
                this.setMotion(this.getMotion().add(0.0D, -0.04D, 0.0D));
            }

            if (this.world.isRemote)
            {
                this.noClip = false;
            }
            else
            {
                this.noClip = !this.world.hasNoCollisions(this);
                if (this.noClip)
                    this.pushOutOfBlocks(this.getPosX(), (this.getBoundingBox().minY + this.getBoundingBox().maxY) / 2.0D, this.getPosZ());
            }

            if (!this.onGround || horizontalMag(this.getMotion()) > (double) 1.0E-5F || (this.ticksExisted + this.getEntityId()) % 4 == 0)
            {
                this.move(MoverType.SELF, this.getMotion());
                float f1 = 0.98F;
                if (this.onGround)
                {
                    f1 = this.world.getBlockState(new BlockPos(this.getPosX(), this.getPosY() - 1.0D, this.getPosZ())).getSlipperiness(world, new BlockPos(this.getPosX(), this.getPosY() - 1.0D, this.getPosZ()), this) * 0.98F;
                }

                this.setMotion(this.getMotion().mul(f1, 0.98D, f1));
                if (this.onGround)
                {
                    Vector3d vector3d1 = this.getMotion();
                    if (vector3d1.y < 0.0D)
                    {
                        this.setMotion(vector3d1.mul(1.0D, -0.5D, 1.0D));
                    }
                }
            }

            boolean flag = MathHelper.floor(this.prevPosX) != MathHelper.floor(this.getPosX()) || MathHelper.floor(this.prevPosY) != MathHelper.floor(this.getPosY()) || MathHelper.floor(this.prevPosZ) != MathHelper.floor(this.getPosZ());
            int i = flag ? 2 : 40;
            if (this.ticksExisted % i == 0)
            {
                if (this.world.getFluidState(this.getPosition()).isTagged(FluidTags.LAVA) && !this.isImmuneToFire())
                {
                    this.playSound(SoundEvents.ENTITY_GENERIC_BURN, 0.4F, 2.0F + this.rand.nextFloat() * 0.4F);
                }
            }

            ++this.age;
            if (this.age == lifespan && this.isImmortal)
                this.age = -32768;

            this.isAirBorne |= this.func_233566_aG_();
            if (!this.world.isRemote)
            {
                double d0 = this.getMotion().subtract(vector3d).lengthSquared();
                if (d0 > 0.01D)
                    this.isAirBorne = true;
            }

            ItemStack item = this.getCard();
            if (!this.world.isRemote && this.age >= lifespan)
                this.remove();

            if (item.isEmpty())
                this.remove();
        }
    }

    private void applyFloatMotion()
    {
        Vector3d vector3d = this.getMotion();
        this.setMotion(vector3d.x * (double) 0.99F, vector3d.y + (double) (vector3d.y < (double) 0.06F ? 5.0E-4F : 0.0F), vector3d.z * (double) 0.99F);
    }

    private void func_234274_v_()
    {
        Vector3d vector3d = this.getMotion();
        this.setMotion(vector3d.x * (double) 0.95F, vector3d.y + (double) (vector3d.y < (double) 0.06F ? 5.0E-4F : 0.0F), vector3d.z * (double) 0.95F);
    }

    private void setImmortal()
    {
        this.isImmortal = true;
        this.setNoDespawn();
        this.setNoGravity(true);
        this.setInvulnerable(true);
        this.entityCollisionReduction = 1F;
    }

    private void removeImmortal()
    {
        this.isImmortal = false;
        this.age = 0;
        this.setNoGravity(false);
        this.setInvulnerable(false);
        this.entityCollisionReduction = 0F;
    }

    @Override
    public boolean canBeCollidedWith()
    {
        return true;
    }

    @Override
    public void onKillCommand()
    {
        if (isImmortal)
            return;
        else super.onKillCommand();
    }

    @Override
    public void readAdditional(CompoundNBT compound)
    {
        this.health = compound.getShort("Health");
        this.age = compound.getShort("Age");
        this.interactDelay = compound.getShort("PickupDelay");
        this.isImmortal = compound.getBoolean("Immortal");

        if (compound.contains("Item"))
            this.setCard(ItemStack.read(compound.getCompound("Item")));
    }

    @Override
    public void writeAdditional(CompoundNBT compound)
    {
        compound.putShort("Health", (short) this.health);
        compound.putShort("Age", (short) this.age);
        compound.putShort("PickupDelay", (short) this.interactDelay);
        compound.putBoolean("Immortal", this.isImmortal);

        if (!this.getCard().isEmpty())
            compound.put("Item", this.getCard().write(new CompoundNBT()));
    }

    @Override
    public ActionResultType processInitialInteract(PlayerEntity player, Hand hand)
    {
        if (!this.isAlive())
            return ActionResultType.PASS;
        else
        {
            ActionResultType actionresulttype = this.processInteract(player, hand);
            return actionresulttype.isSuccessOrConsume() ? actionresulttype : super.processInitialInteract(player, hand);
        }
    }

    //TODO sided method
    private ActionResultType processInteract(PlayerEntity player, Hand hand)
    {
        ItemStack itemstack = player.getHeldItem(hand);

        if (!this.getCard().isEmpty())
        {
            if (itemstack.getItem() == ItemReg.TENKYU_S_PACKET.get())
            {
                AbstractCard card = (AbstractCard) this.getCard().getItem();
                if (!PlayerPropertiesUtils.doPlayerCollected(player, card))
                {
                    PlayerPropertiesUtils.collectCard(player, card);
                    AdvancementUtils.giveAdvancement(player, AdvancementUtils.getAdvIdFromAbsCard(card));
                    this.interactDelay = 10;
                    return ActionResultType.func_233537_a_(this.world.isRemote);
                }
            }
            else if (itemstack.getItem() == DEBUG_STICK)
            {
                if (this.isImmortal)
                {
                    this.removeImmortal();
                    if (player instanceof ServerPlayerEntity)
                    {
                        //TODO switch to ttc
                        player.sendMessage(new StringTextComponent("immortal status removed!"), null);
                    }
                }
                else
                {
                    this.setImmortal();
                    if (player instanceof ServerPlayerEntity)
                    {
                        //TODO switch to ttc & output properties in txt
                        player.sendMessage(new StringTextComponent("immortal status confirmed!"), null);
                    }
                }
            }
        }
        return ActionResultType.PASS;
    }

    public void setNoDespawn()
    {
        this.age = -32768;
    }

    @OnlyIn(Dist.CLIENT)
    public int getAge()
    {
        return this.age;
    }

    public ItemStack getCard()
    {
        return this.dataManager.get(CARD);
    }

    public void setCard(ItemStack card)
    {
        if (card.getItem() instanceof AbstractCard)
            this.dataManager.set(CARD, card);
    }

    public void setInteractDelay(int interactDelay)
    {
        this.interactDelay = interactDelay;
    }

    @OnlyIn(Dist.CLIENT)
    public float getItemHover(float partialTicks)
    {
        return ((float) this.getAge() + partialTicks) / 20.0F + this.hoverStart;
    }

    @Override
    public IPacket<?> createSpawnPacket()
    {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
