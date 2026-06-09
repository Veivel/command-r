package com.github.veivel.commandr.neoforge.client;

import net.blay09.mods.balm.client.BalmClient;
import net.blay09.mods.balm.neoforge.platform.runtime.NeoForgeLoadContext;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import com.github.veivel.commandr.Commandr;
import com.github.veivel.commandr.client.CommandrClient;

@Mod(value = Commandr.MOD_ID, dist = Dist.CLIENT)
public class NeoForgeCommandrClient {

    public NeoForgeCommandrClient(ModContainer modContainer, IEventBus modEventBus) {
        final var context = new NeoForgeLoadContext(modContainer, modEventBus);
        BalmClient.initializeMod(Commandr.MOD_ID, context, CommandrClient::initialize);
    }
}
