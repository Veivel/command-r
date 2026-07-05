package com.github.veivel.commandr;

import net.blay09.mods.balm.platform.config.reflection.Config;

@Config(Commandr.MOD_ID)
public class CommandrConfig {

    /**
     * If set to True, Command R will only search
     * through commands and will ignore chat messages.
     * If set to False (default), everything will be
     * searched.
     */
    // TODO: apply this config
    public boolean searchOnlyCommands = false;
}
