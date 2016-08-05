package tech.shadowsystems.dw.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import tech.shadowsystems.dw.DeathSwap;

/**
 * Copyright © 2016 Jordan Osterberg and Shadow Technical Systems LLC. All rights reserved. Please email jordan.osterberg@shadowsystems.tech for usage rights and other information.
 */
public class PlayerDamage implements Listener {

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();

            if (!DeathSwap.getInstance().isRunning() || DeathSwap.getInstance().getSwapsSoFar() <= 3) {
                event.setCancelled(true);
                return;
            }
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        Player winner = null;
        if (DeathSwap.getInstance().getFirstPlayer() == player) {
            winner = DeathSwap.getInstance().getSecondPlayer();
        } else {
            winner = DeathSwap.getInstance().getFirstPlayer();
        }

        player.setHealth(player.getMaxHealth());

        DeathSwap.getInstance().stopGame(winner);
    }

}
