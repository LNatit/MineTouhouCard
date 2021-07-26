package lnatit.mcardsth.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.DisplayEffectsScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import static lnatit.mcardsth.MineCardsTouhou.MOD_ID;

@OnlyIn(Dist.CLIENT)
public class PacketScreen extends DisplayEffectsScreen<PacketContainer>
{
    private static final ResourceLocation PACKET_CONTAINER_RESOURCE = new ResourceLocation(MOD_ID, "textures/gui/packet.png");
    public static final String TITLE = "container.minecardstouhou.packetscreen";
    private final int textureWidth = 176;
    private final int textureHeight = 182;

    protected static final Inventory INVENTORY = new Inventory(63);

    public PacketScreen(PlayerEntity player)
    {
        super(new PacketContainer(player), player.inventory, new TranslationTextComponent(TITLE));
        player.openContainer = this.container;
        this.passEvents = true;
        this.minecraft = Minecraft.getInstance();
        this.xSize = textureWidth;
        this.ySize = textureHeight;
        this.playerInventoryTitleY = this.ySize - 36;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int x, int y)
    {
        this.font.drawText(matrixStack, this.title, (float)this.titleX, (float)this.titleY, 4210752);
        this.font.drawText(matrixStack, this.playerInventory.getDisplayName(), (float)this.playerInventoryTitleX, (float)this.playerInventoryTitleY, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y)
    {
        this.renderBackground(matrixStack);
        this.minecraft.getTextureManager().bindTexture(PACKET_CONTAINER_RESOURCE);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        blit(matrixStack, i, j, 0, 0, xSize, ySize, this.textureWidth, textureHeight);
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks)
    {
        renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        renderHoveredTooltip(matrixStack, mouseX, mouseY);
    }
}
