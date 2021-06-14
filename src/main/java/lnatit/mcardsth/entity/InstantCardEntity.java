package lnatit.mcardsth.entity;


import lnatit.mcardsth.event.InstantCardPickupEvent;
import lnatit.mcardsth.item.InstantCard;
import lnatit.mcardsth.network.CardActivationPacket;
import lnatit.mcardsth.network.NetworkManager;
import lnatit.mcardsth.utils.InstantCardUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
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
import net.minecraft.stats.Stats;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.UUID;

import static lnatit.mcardsth.utils.InstantCardUtils.triggerItemPickupTrigger;

/**
 * TODO unfinished!!!
 */
public class InstantCardEntity extends Entity
{
    public static EntityType<InstantCardEntity> TYPE = EntityType.Builder
            .<InstantCardEntity>create(InstantCardEntity::new, EntityClassification.MISC)
            .size(0.25F, 0.25F)
            .trackingRange(6)
            .updateInterval(20)
            .build("instant_card_entity");

    private static final DataParameter<ItemStack> CARD = EntityDataManager.createKey(ItemEntity.class, DataSerializers.ITEMSTACK);
    private int age;
    private int pickupDelay;
    private int health = 5;
    private UUID thrower;
    private PlayerEntity closestPlayer;
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
        this.init();
        this.setCard(cardItem.getItem());
        this.copyLocationAndAnglesFrom(cardItem);
        this.setMotion(cardItem.getMotion());
        this.thrower = cardItem.getThrowerId();
        this.age = cardItem.getAge();
        this.hoverStart = cardItem.hoverStart;
        this.setCustomName(cardItem.getItem().getDisplayName());
    }

    public void init()
    {
        this.pickupDelay = 10;
        this.age = this.lifespan;
        this.setCustomNameVisible(true);
    }

    @Override
    protected void registerData()
    {
        this.getDataManager().register(CARD, ItemStack.EMPTY);
    }

    /**
     * TODO inspect the method, fix possible bugs
     */
    @Override
    public void tick()
    {
        if (this.getCard().isEmpty())
            this.remove();
        else
        {
            super.tick();
            if (this.pickupDelay > 0 && this.pickupDelay != 32767)
                this.pickupDelay--;

            this.prevPosX = this.getPosX();
            this.prevPosY = this.getPosY();
            this.prevPosZ = this.getPosZ();
            Vector3d vector3d = this.getMotion();
            float f = this.getEyeHeight() - 0.11111111F;
            if (this.isInWater() && this.func_233571_b_(FluidTags.WATER) > (double) f)
            {
                this.applyFloatMotion();
            } else if (this.isInLava() && this.func_233571_b_(FluidTags.LAVA) > (double) f)
            {
                this.func_234274_v_();
            } else if (!this.hasNoGravity())
            {
                this.setMotion(this.getMotion().add(0.0D, -0.04D, 0.0D));
            }

            if (this.world.isRemote)
            {
                this.noClip = false;
            } else
            {
                this.noClip = !this.world.hasNoCollisions(this);
                if (this.noClip)
                    this.pushOutOfBlocks(this.getPosX(), (this.getBoundingBox().minY + this.getBoundingBox().maxY) / 2.0D, this.getPosZ());
            }

//            double d0 = 8.0D;
            if (this.closestPlayer == null || this.closestPlayer.getDistanceSq(this) > 64.0D)
            {
                this.closestPlayer = this.world.getClosestPlayer(this, 8.0D);
            }

            if (this.closestPlayer != null && this.closestPlayer.isSpectator()) {
                this.closestPlayer = null;
            }

            if (this.closestPlayer != null)
            {
                Vector3d vector3d2 = new Vector3d(this.closestPlayer.getPosX() - this.getPosX(), this.closestPlayer.getPosY() - this.getPosY(), this.closestPlayer.getPosZ() - this.getPosZ());
                double d1 = vector3d2.lengthSquared();
                if (d1 < 64.0D)
                {
                    double d2 = 1.0D - Math.sqrt(d1) / 8.0D;
                    this.setMotion(this.getMotion().add(vector3d2.normalize().scale(d2 * d2 * 0.05D)));
                }
            }
//


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

            if (this.age != -32768)
                ++this.age;

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

    @Override
    public void readAdditional(CompoundNBT compound)
    {
        this.health = compound.getShort("Health");
        this.age = compound.getShort("Age");
        this.pickupDelay = compound.getShort("PickupDelay");

        if (compound.contains("Item"))
            this.setCard(ItemStack.read(compound.getCompound("Item")));
    }

    @Override
    public void writeAdditional(CompoundNBT compound)
    {
        compound.putShort("Health", (short) this.health);
        compound.putShort("Age", (short) this.age);
        compound.putShort("PickupDelay", (short) this.pickupDelay);

        if (!this.getCard().isEmpty())
            compound.put("Item", this.getCard().write(new CompoundNBT()));
    }

    @Override
    public void onCollideWithPlayer(PlayerEntity entityIn)
    {
        if (!this.world.isRemote)
        {
            if (this.pickupDelay > 0) return;
            InstantCard card = (InstantCard) this.getCard().getItem();
            if (entityIn.getCooldownTracker().hasCooldown(card)) return;

//            if (MinecraftForge.EVENT_BUS.post(new InstantCardPickupEvent(entityIn, card)))
//                return;

            if (!InstantCardUtils.instantCardHandler(entityIn, card))
                return;
            this.remove();

            entityIn.addStat(Stats.ITEM_PICKED_UP.get(card), 1);
            triggerItemPickupTrigger(entityIn, this);

            entityIn.addStat(Stats.ITEM_USED.get(card), 1);
            NetworkManager.serverSendToPlayer(new CardActivationPacket(card), (ServerPlayerEntity) entityIn);
            entityIn.getCooldownTracker().setCooldown(card, 20);
        }
    }

    @Nullable
    public UUID getThrowerId() {
        return this.thrower;
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
        if (card.getItem() instanceof InstantCard)
            this.dataManager.set(CARD, card);
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
