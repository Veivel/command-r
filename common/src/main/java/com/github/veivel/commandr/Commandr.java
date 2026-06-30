package com.github.veivel.commandr;

import com.github.veivel.commandr.core.MixinRelay;
import com.github.veivel.commandr.core.history.HistoryManager;
import com.github.veivel.commandr.core.history.InMemoryHistoryManager;
import com.github.veivel.commandr.core.screen.ChatScreenController;
import com.github.veivel.commandr.core.screen.ChatScreenState;
import net.blay09.mods.balm.Balm;
import net.blay09.mods.balm.core.BalmRegistrars;
import net.minecraft.resources.Identifier;
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

        HistoryManager historyManager = new InMemoryHistoryManager();
        ChatScreenState chatScreenState = new ChatScreenState();
        ChatScreenController chatScreenController = new ChatScreenController(
            chatScreenState,
            historyManager
        );

        MixinRelay.init(chatScreenState, chatScreenController, historyManager);
    }
}
