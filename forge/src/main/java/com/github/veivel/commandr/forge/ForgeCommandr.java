package com.github.veivel.commandr.forge;

import net.blay09.mods.balm.Balm;
import net.blay09.mods.balm.client.BalmClient;
import net.blay09.mods.balm.forge.platform.runtime.ForgeLoadContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import com.github.veivel.commandr.Commandr;
import com.github.veivel.commandr.client.CommandrClient;

@Mod(Commandr.MOD_ID)
public class ForgeCommandr {

    public ForgeCommandr(FMLJavaModLoadingContext context) {
        final var loadContext = new ForgeLoadContext(context.getModBusGroup());
        Balm.initializeMod(Commandr.MOD_ID, loadContext, Commandr::initialize);
        if (FMLEnvironment.dist.isClient()) {
            BalmClient.initializeMod(Commandr.MOD_ID, loadContext, CommandrClient::initialize);
        }
    }

}
