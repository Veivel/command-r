package com.github.veivel.commandr.neoforge;

import net.blay09.mods.balm.Balm;
import net.blay09.mods.balm.neoforge.platform.runtime.NeoForgeLoadContext;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import com.github.veivel.commandr.Commandr;

@Mod(Commandr.MOD_ID)
public class NeoForgeCommandr {

    public NeoForgeCommandr(ModContainer modContainer, IEventBus modEventBus) {
        final var context = new NeoForgeLoadContext(modContainer, modEventBus);
        Balm.initializeMod(Commandr.MOD_ID, context, Commandr::initialize);
    }
}
