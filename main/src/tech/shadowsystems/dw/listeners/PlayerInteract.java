package tech.shadowsystems.dw.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import tech.shadowsystems.dw.DeathSwap;
import tech.shadowsystems.dw.GameState;
import tech.shadowsystems.dw.utility.ChatUtil;

/**
 * Copyright © 2016 Jordan Osterberg and Shadow Technical Systems LLC. All rights reserved. Please email jordan.osterberg@shadowsystems.tech for usage rights and other information.
 */
public class PlayerInteract implements Listener {

    @EventHandler
    public void onPlayerInteractAtEntity(PlayerInteractAtEntityEvent event) {
        Player player = event.getPlayer();
        Entity entity = event.getRightClicked();

        if (entity.getCustomName() != null && entity.getCustomName().toLowerCase().contains("join deathswap")) {
            if (GameState.isGameState(GameState.LOBBY)) {
                player.sendMessage(ChatUtil.formatWithPrefix("You're in the DeathSwap queue."));
                DeathSwap.getInstance().joinQueue(player);
            }
        }

    }

}
