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

  // TODO: Make sure this has no other entry points
  // TODO: refactor?
  @Inject(at = @At("HEAD"), method = "extractWidgetRenderState")
  public void extractWidgetRenderState(final GuiGraphicsExtractor graphics, final int mouseX, final int mouseY, final float a, CallbackInfo ci) {
    if (!MixinRelay.inSearchMode()) {
      // TODO: set x back to 4
      // needed for edge case where suggestion is used/applied and we
      // go back to normal chat mode
      return;
    }
    int x = 4; // default this.x value for chat screen's EditBox
    int textColor = -9408400; // this.textColorUneditable
    if (MixinRelay.isSearchEmpty()) {
      textColor = -43691; // ChatFormatting.RED
    }
    graphics.text(
      Minecraft.getInstance().fontFilterFishy, 
      "bck-i-search:", 
      x,
      this.getY(), 
      textColor
    );
    this.setX(x * (13 + 6)); // 1.5 * x * length of prefix string. this feels janky though
  }
}
