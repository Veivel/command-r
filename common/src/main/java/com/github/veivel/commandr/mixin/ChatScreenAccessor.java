package com.github.veivel.commandr.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.client.gui.components.CommandSuggestions;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.ChatScreen;

@Mixin(ChatScreen.class)
public interface ChatScreenAccessor {
  @Accessor("input")
  EditBox getInput();

  @Accessor("commandSuggestions")
  CommandSuggestions getCommandSuggestions();
}
