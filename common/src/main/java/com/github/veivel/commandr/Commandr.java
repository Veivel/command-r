package com.github.veivel.commandr;

import net.blay09.mods.balm.Balm;
import net.minecraft.resources.Identifier;
import net.blay09.mods.balm.core.BalmRegistrars;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Commandr {

    public static final Logger logger = LoggerFactory.getLogger(Commandr.class);

    public static final String MOD_ID = "commandr";

    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }

    public static CommandrConfig config() {
        return Balm.config().getActiveConfig(CommandrConfig.class);
    }

    public static void initialize(BalmRegistrars registrars) {
        Balm.config().registerConfig(CommandrConfig.class);

        // registrars.blocks(ModBlocks::initialize);
        // registrars.items(ModItems::initialize);
        // registrars.creativeModeTabs(ModItems::initialize);
    }

}
