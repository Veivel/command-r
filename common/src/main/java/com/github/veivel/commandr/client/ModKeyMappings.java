package com.github.veivel.commandr.client;

import com.mojang.blaze3d.platform.InputConstants;

import net.blay09.mods.kuma.api.InputBinding;
import net.blay09.mods.kuma.api.KeyModifier;
import net.blay09.mods.kuma.api.KeyModifiers;
import net.blay09.mods.kuma.api.Kuma;
import net.blay09.mods.kuma.api.ManagedKeyMapping;

import com.github.veivel.commandr.Commandr;

import static com.github.veivel.commandr.Commandr.id;

public class ModKeyMappings {

    public static ManagedKeyMapping search;
    public static ManagedKeyMapping previous;

    public static void initialize() {

        search = Kuma.createKeyMapping(id("search"))
            .withDefault(InputBinding.key(
                InputConstants.KEY_R, 
                KeyModifiers.of(KeyModifier.CONTROL)
            ))
            .handleScreenInput((event) -> {
                Commandr.chatScreenController.handleActionKey();
                return true;
            })
            .ignoreScreenFocus()
            .enableKeyRepeat()
            .build();

        previous = Kuma.createKeyMapping(id("previous"))
            .withDefault(InputBinding.key(
                InputConstants.KEY_R,
                KeyModifiers.of(KeyModifier.CONTROL, KeyModifier.SHIFT)
            ))
            .handleScreenInput((event) -> {
                Commandr.chatScreenController.handlePrevKey();
                return true;
            })
            .ignoreScreenFocus()
            .enableKeyRepeat()
            .build();

    }
}
