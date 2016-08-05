package tech.shadowsystems.dw.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import tech.shadowsystems.dw.DeathSwap;
import tech.shadowsystems.dw.GameState;
import tech.shadowsystems.dw.utility.ChatUtil;

/**
 * Copyright © 2016 Jordan Osterberg and Shadow Technical Systems LLC. All rights reserved. Please email jordan.osterberg@shadowsystems.tech for usage rights and other information.
 */
public class PlayerLogin implements Listener {

    @EventHandler
    public void onLogin(PlayerLoginEvent event) {
        if (!GameState.isGameState(GameState.LOBBY)) {
            if (GameState.isGameState(GameState.RESETTING)) {
                event.disallow(PlayerLoginEvent.Result.KICK_WHITELIST, ChatUtil.formatWithPrefix("\n&fThe game is resetting."));
                return;
            }

            if (!DeathSwap.getInstance().isUserPlaying(event.getPlayer())) {
                event.disallow(PlayerLoginEvent.Result.KICK_FULL, ChatUtil.formatWithPrefix("\n&cThis DeathSwap game is in progress.\n&cYou are currently unable to join."));
                return;
            }
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.teleport(new Location(Bukkit.getWorld("world"), 0, 256, 0));
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (GameState.isGameState(GameState.INGAME)) {
            player.damage(9001);
        }
    }

}
