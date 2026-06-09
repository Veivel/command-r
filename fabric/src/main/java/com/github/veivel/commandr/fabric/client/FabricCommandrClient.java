package com.github.veivel.commandr.fabric.client;

import net.blay09.mods.balm.client.BalmClient;
import net.blay09.mods.balm.fabric.platform.runtime.FabricLoadContext;
import net.fabricmc.api.ClientModInitializer;
import com.github.veivel.commandr.Commandr;
import com.github.veivel.commandr.client.CommandrClient;

public class FabricCommandrClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BalmClient.initializeMod(Commandr.MOD_ID, FabricLoadContext.INSTANCE, CommandrClient::initialize);
    }
}
