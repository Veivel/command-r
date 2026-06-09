package com.github.veivel.commandr.fabric;

import net.blay09.mods.balm.Balm;
import net.blay09.mods.balm.fabric.platform.runtime.FabricLoadContext;
import net.fabricmc.api.ModInitializer;
import com.github.veivel.commandr.Commandr;

public class FabricCommandr implements ModInitializer {
    @Override
    public void onInitialize() {
        Balm.initializeMod(Commandr.MOD_ID, FabricLoadContext.INSTANCE, Commandr::initialize);
    }
}
