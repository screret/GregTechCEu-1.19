package com.gregtechceu.gtceu.api.gui.widget;

import com.lowdragmc.lowdraglib.gui.util.DrawerHelper;
import com.lowdragmc.lowdraglib.gui.widget.Widget;
import com.mojang.blaze3d.vertex.PoseStack;
import lombok.Getter;
import lombok.Setter;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.Vec2;

import javax.annotation.Nonnull;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class PointSelectorWidget extends Widget {

    private boolean isDraggingMain;

    private int x, y;
    private Vec2 point;
    private Vec2 center;
    @Getter
    private Supplier<Vec2> pointSupplier;
    @Setter
    private Consumer<Vec2> onChanged;

    public PointSelectorWidget(int xPosition, int yPosition, int width, int height) {
        super(xPosition, yPosition, width, height);
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        if (pointSupplier != null && !isClientSideWidget) {
            Vec2 lastPoint = point;
            setPoint(pointSupplier.get());
            if (lastPoint != point) {
                writeUpdateInfo(-1, buffer -> {
                    buffer.writeVarInt((int) point.x);
                    buffer.writeVarInt((int) point.y);
                });
            }
        }
    }

    public boolean isMouseOverMain(double mouseX, double mouseY) {
        int x = getPosition().x;
        int y = getPosition().y;
        int width = getSize().width;
        int height = getSize().height;
        return isMouseOver(x, y, width, height, mouseX, mouseY);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        isDraggingMain = false;
        if (isMouseOverMain(mouseX, mouseY)) {
            if (button == 0) {
                isDraggingMain = true;
            } else if (button == 1) {
                int x = getPosition().x;
                int y = getPosition().y;
                int width = getSize().width;
                int height = getSize().height;
                this.x = (int) (normalizeMouse(mouseX, x, width) * width);
                this.y = (int) (normalizeMouse(mouseY, y, width) * height);
                refreshPoint();
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    private static float normalizeMouse(double mouse, int pos, int size) {
        if (mouse >= pos + size) return 1;
        if (mouse <= pos) return 0;
        double x = mouse - pos;
        double y = x % size / size;
        if (y < 0) {
            x = -x;
            y = -y;
        }
        x /= size;
        return (float) (x % 2 > 1 ? 1 - y : y);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        int x = getPosition().x;
        int y = getPosition().y;
        int width = getSize().width;
        int height = getSize().height;
        if (isDraggingMain) {
            float normalizedX = normalizeMouse(mouseX, x, width) * width;
            float normalizedY = normalizeMouse(mouseY, y, height) * height;
            this.center = new Vec2(normalizedX, normalizedY);
            refreshPoint();
            return true;
        }
        return false;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        isDraggingMain = false;
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Environment(EnvType.CLIENT)
    public void drawInBackground(@Nonnull PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        super.drawInBackground(matrixStack, mouseX, mouseY, partialTicks);
        int x = getPosition().x;
        int y = getPosition().y;
        int width = getSize().width;
        int height = getSize().height;
        this.renderInfo(matrixStack, x, y, width, height);
    }

    @Override
    public void readUpdateInfo(int id, FriendlyByteBuf buffer) {
        if (id == -1) {
            setPoint(new Vec2(buffer.readVarInt(), buffer.readVarInt()));
        } else {
            super.readUpdateInfo(id, buffer);
        }
    }

    private void refreshPoint() {
        point = new Vec2(x, y);
        if (onChanged != null) {
            onChanged.accept(point);
        }
        if (isRemote() && !isClientSideWidget) {
            writeClientAction(-1, buffer -> {
                buffer.writeVarInt((int) point.x);
                buffer.writeVarInt((int) point.y);
            });
        }
    }

    public PointSelectorWidget setPoint(Vec2 point) {
        if (this.point == point) return this;
        refreshPoint();
        return this;
    }

    @Environment(EnvType.CLIENT)
    private void renderInfo(PoseStack poseStack, int x, int y, int width, int height) {
        Font font = Minecraft.getInstance().font;
        y += 2;
        var strX = x + 10;
        var strGapY = (int) Math.max(0, (height - 7f * font.lineHeight) / 6f) + font.lineHeight;
        DrawerHelper.drawText(poseStack,"X:" + this.x, strX, y, 1f, -1, true);
        DrawerHelper.drawText(poseStack, "Y:" + this.y, strX, y + strGapY, 1f, -1, true);
//		DrawerHelper.drawText(poseStack, "mode:" + mode, strX, y + strGapY * 6, 1f, 0xffffffff);
    }
}
