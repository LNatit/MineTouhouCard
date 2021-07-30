package lnatit.mcardsth.network;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.Random;
import java.util.function.Supplier;

public class ParticleRenderPacket extends IPacket
{
    private byte index;     //  1: SStrike
    private BlockPos pos;

    public ParticleRenderPacket(byte index, BlockPos pos)
    {
        this.index = index;
        this.pos = pos;
    }

    public static void encode(ParticleRenderPacket packet, PacketBuffer buffer)
    {
        buffer.writeByte(packet.index);
        buffer.writeBlockPos(packet.pos);
    }

    public static ParticleRenderPacket decode(PacketBuffer buffer)
    {
        return new ParticleRenderPacket(buffer.readByte(), buffer.readBlockPos());
    }

    public static void handle(ParticleRenderPacket packet, Supplier<NetworkEvent.Context> contextSupplier)
    {
        contextSupplier.get().enqueueWork(() ->
                                          {
                                              WorldRenderer renderer = Minecraft.getInstance().worldRenderer;
                                              Random random = new Random();
                                              BlockPos blockPos = packet.pos;
                                              switch (packet.index)
                                              {
                                                  case 1:
                                                      for (int k = 0; k < 200; ++k)
                                                      {
                                                          float f = random.nextFloat() * 4.0F;
                                                          float f1 = random.nextFloat() * ((float) Math.PI * 2F);
                                                          double d1 = (double) (MathHelper.cos(f1) * f);
                                                          double d2 = 0.01D + random.nextDouble() * 0.5D;
                                                          double d3 = (double) (MathHelper.sin(f1) * f);
                                                          renderer.addParticle(ParticleTypes.TOTEM_OF_UNDYING,
                                                                               false,
                                                                               (double) blockPos.getX() + d1 * 0.1D,
                                                                               (double) blockPos.getY() + 0.3D,
                                                                               (double) blockPos.getZ() + d3 * 0.1D,
                                                                               d1, d2, d3
                                                          );
                                                      }
                                                      ;
                                                      break;
                                              }
                                          });

        contextSupplier.get().setPacketHandled(true);
    }
}
