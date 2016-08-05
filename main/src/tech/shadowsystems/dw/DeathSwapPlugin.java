package tech.shadowsystems.dw;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import tech.shadowsystems.dw.files.FileSystem;
import tech.shadowsystems.dw.listeners.PlayerDamage;
import tech.shadowsystems.dw.listeners.PlayerInteract;
import tech.shadowsystems.dw.listeners.PlayerLogin;
import tech.shadowsystems.dw.utility.ChatUtil;
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

        getServer().getPluginManager().registerEvents(new PlayerDamage(), this);
        getServer().getPluginManager().registerEvents(new PlayerInteract(), this);
        getServer().getPluginManager().registerEvents(new PlayerLogin(), this);
    }

    public void onDisable() {

        DeathSwap.getInstance().onDisable();
        FileSystem.getInstance().onDisable();

        instance = null;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            Villager villager = (Villager) player.getLocation().getWorld().spawnEntity(player.getLocation(), EntityType.VILLAGER);
            villager.setCustomName(ChatUtil.format("&aJoin DeathSwap"));
            villager.setProfession(Villager.Profession.BUTCHER);
            villager.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 999999, 999999));

        }
        return false;
    }

    public static DeathSwapPlugin getInstance() {
        return instance;
    }
}
