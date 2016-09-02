package tech.shadowsystems.dw.world;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import tech.shadowsystems.dw.DeathSwap;
import tech.shadowsystems.dw.stats.SystemStats;

import java.io.File;

/**
 * Copyright © 2016 Jordan Osterberg and Shadow Technical Systems LLC. All rights reserved. Please email jordan.osterberg@shadowsystems.tech for usage rights and other information.
 */
public class WorldExtension {

    private World world;

    public WorldExtension(Runnable runnable) {
        world = Bukkit.getServer().createWorld(new WorldCreator("ds_" + DeathSwap.getInstance().getGameID()));
        world.setAutoSave(false);
        runnable.run();
    }


    public void deleteWorld() {
        Bukkit.unloadWorld(world, false);
        File file = new File(Bukkit.getServer().getWorldContainer().getAbsolutePath() + File.pathSeparator + world.getName());
        delete(file);
    }

    private void delete(File delete){
        if (delete.isDirectory()){
            String[] files = delete.list();

            if (files == null) {
                return;
            }

            for (String file : files){
                File toDelete = new File(file);

                delete(toDelete);
            }
        } else {
            delete.delete();
        }
    }

    public World getWorld() {
        return world;
    }

}
