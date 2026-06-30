package com.github.veivel.commandr.mixin;

import com.github.veivel.commandr.core.MixinRelay;
import com.github.veivel.commandr.core.gui.MinecraftScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EditBox.class)
public abstract class EditBoxMixin extends AbstractWidget {

    public EditBoxMixin(
        int x,
        int y,
        int width,
        int height,
        Component message
    ) {
        super(x, y, width, height, message);
    }

    @Inject(at = @At("HEAD"), method = "extractWidgetRenderState")
    public void extractWidgetRenderState(
        final GuiGraphicsExtractor graphics,
        final int mouseX,
        final int mouseY,
        final float a,
        CallbackInfo ci
    ) {
        // Making sure the logic below will not run
        // on unrelated screens.
        if (!MinecraftScreen.isCurrentChatScreen()) {
            return;
        }

        int x = 4; // default this.x value for chat screen's EditBox
        String prefix = "bck-i-search:";
        if (!MixinRelay.inSearchMode()) {
            // Needed for edge case where suggestion is used/applied so
            // we go back to rendering "normal" chat
            this.setX(x);
            return;
        }
        int textColor = -9408400; // this.textColorUneditable
        if (MixinRelay.isSearchEmpty()) {
            textColor = -43691; // ChatFormatting.RED
        }
        graphics.text(
            Minecraft.getInstance().fontFilterFishy,
            prefix,
            x,
            this.getY(),
            textColor
        );
        // 1.5 * x * length of prefix string; this works but feels VERY janky
        Double newX = Math.ceil(4 * prefix.length() * 1.5);
        this.setX(newX.intValue());
    }
}
