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

    /**
     * TODO FIX THIS
     */
    public void deleteWorld() {
//        File file = new File(Bukkit.getServer().getWorldContainer().getAbsolutePath(), world.getName());
//        System.out.print("File path is " + file.getPath());
//        System.out.print("Requested file path is " + Bukkit.getServer().getWorldContainer().getAbsolutePath());
//        System.out.print("2ndary Requested file path is " + Bukkit.getServer().getWorldContainer());
//        delete(file);

        Bukkit.unloadWorld(world, false);

//        String rootDirectory = Bukkit.getServer().getWorldContainer().getAbsolutePath();
//        System.out.print(rootDirectory);
//        File data = new File(rootDirectory + "/" + world.getName());
//        System.out.print(data.getPath());
//        delete(data);

        File data = world.getWorldFolder();
        System.out.print(data.getPath());
        delete(data);

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

}
