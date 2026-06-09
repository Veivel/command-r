package com.github.veivel.commandr.client;

import net.blay09.mods.balm.client.BalmClientRegistrars;

public class CommandrClient {

    public static void initialize(BalmClientRegistrars registrars) {
        ModKeyMappings.initialize();
    }

}
