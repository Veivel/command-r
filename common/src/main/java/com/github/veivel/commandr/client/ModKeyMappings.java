package com.github.veivel.commandr.client;

import com.mojang.blaze3d.platform.InputConstants;

import net.blay09.mods.kuma.api.InputBinding;
import net.blay09.mods.kuma.api.KeyModifier;
import net.blay09.mods.kuma.api.KeyModifiers;
import net.blay09.mods.kuma.api.Kuma;
import net.blay09.mods.kuma.api.ManagedKeyMapping;
import net.minecraft.client.KeyMapping.Category;

import com.github.veivel.commandr.Commandr;

import static com.github.veivel.commandr.Commandr.id;

public class ModKeyMappings {

    public static ManagedKeyMapping yourKey;

    public static void initialize() {

        Kuma.createKeyMapping(id("reverse-search"))
            .withDefault(InputBinding.key(
                InputConstants.KEY_R, 
                KeyModifiers.of(KeyModifier.CONTROL)
            ))
            .handleScreenInput((event) -> {
                Commandr.logger.info("awooga event {}", event.keyMapping());
                Commandr.logger.info("awooga input {}", event.input());
                return true;
            })
            .ignoreScreenFocus()
            .enableKeyRepeat()
            .build();
    }
}
