package com.github.veivel.commandr.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.github.veivel.commandr.Commandr;
import com.github.veivel.commandr.core.MixinRelay;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.Component;

@Mixin(EditBox.class)
public abstract class EditBoxMixin extends AbstractWidget {

  public EditBoxMixin(int x, int y, int width, int height, Component message) {
    super(x, y, width, height, message);
  }

  @Inject(at = @At("HEAD"), method = "extractWidgetRenderState")
  public void extractWidgetRenderState(final GuiGraphicsExtractor graphics, final int mouseX, final int mouseY, final float a, CallbackInfo ci) {
    // TODO: refactor?
    int x = 4;
    if (MixinRelay.inSearchMode()) {
      Commandr.logger.info("x: {}, y: {}", x, this.getY());
      graphics.text(
        Minecraft.getInstance().fontFilterFishy, 
        "bck-i-search:", 
        x,
        this.getY(), 
        -9408400 // this.textColorUneditable
      );
      this.setX(x * (13 + 6)); // 1.5 * x * length of prefix string. this feels janky though
    }
  }
}
