package tech.shadowsystems.dw;

import org.bukkit.plugin.java.JavaPlugin;
import tech.shadowsystems.dw.files.FileSystem;
import tech.shadowsystems.dw.world.WorldExtension;

/**
 * Copyright © 2016 Jordan Osterberg and Shadow Technical Systems LLC. All rights reserved. Please email jordan.osterberg@shadowsystems.tech for usage rights and other information.
 */
public class DeathSwapPlugin extends JavaPlugin {

    private static DeathSwapPlugin instance = null;

    public void onEnable() {
        instance = this;

        FileSystem.getInstance().onEnable(this);
        DeathSwap.getInstance().onEnable();

    }

    public void onDisable() {

        DeathSwap.getInstance().onDisable();
        FileSystem.getInstance().onDisable();

        instance = null;
    }

    public static DeathSwapPlugin getInstance() {
        return instance;
    }
}
