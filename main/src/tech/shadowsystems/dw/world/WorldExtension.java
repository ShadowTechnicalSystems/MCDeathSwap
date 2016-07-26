package tech.shadowsystems.dw.world;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import tech.shadowsystems.dw.DeathSwap;

/**
 * Copyright © 2016 Jordan Osterberg and Shadow Technical Systems LLC. All rights reserved. Please email jordan.osterberg@shadowsystems.tech for usage rights and other information.
 */
public class WorldExtension {

    private World world;

    public WorldExtension() {
        world = Bukkit.getServer().createWorld(new WorldCreator("ds_" + DeathSwap.getInstance().getGameID()));
    }

}
