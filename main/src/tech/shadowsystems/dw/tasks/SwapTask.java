package tech.shadowsystems.dw.tasks;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import tech.shadowsystems.dw.DeathSwap;
import tech.shadowsystems.dw.DeathSwapPlugin;

import java.util.Random;

/**
 * Copyright © 2016 Jordan Osterberg and Shadow Technical Systems LLC. All rights reserved. Please email jordan.osterberg@shadowsystems.tech for usage rights and other information.
 */
public class SwapTask extends BukkitRunnable {

    public void run() {
        if (!DeathSwap.getInstance().isRunning()) {
            return;
        }

        Random random = new Random();

        if (DeathSwap.getInstance().isFirstSwap()) {
            Integer[] nonYValues = {1000, -1000};
            int x = nonYValues[random.nextInt(nonYValues.length)];
            int z = nonYValues[random.nextInt(nonYValues.length)];

            Location location = new Location(DeathSwap.getInstance().getWorldExtension().getWorld(), x, 256, z);
            Location locationTwo = new Location(DeathSwap.getInstance().getWorldExtension().getWorld(), x * -1, 256, z * -1); // Reversing locations by multiplying negative (or positive) by negative.

            applyEffects(DeathSwap.getInstance().getFirstPlayer());
            applyEffects(DeathSwap.getInstance().getSecondPlayer());

            DeathSwap.getInstance().getFirstPlayer().teleport(location);
            DeathSwap.getInstance().getSecondPlayer().teleport(locationTwo);
        } else {
            Location playerOne = DeathSwap.getInstance().getFirstPlayer().getLocation();
            Location playerTwo = DeathSwap.getInstance().getSecondPlayer().getLocation();

            applyEffects(DeathSwap.getInstance().getFirstPlayer());
            applyEffects(DeathSwap.getInstance().getSecondPlayer());

            DeathSwap.getInstance().getFirstPlayer().teleport(playerTwo);
            DeathSwap.getInstance().getSecondPlayer().teleport(playerOne);
        }

        DeathSwap.getInstance().setSwapsSoFar(DeathSwap.getInstance().getSwapsSoFar() + 1);
        DeathSwap.getInstance().setFirstSwap(false);
        DeathSwap.getInstance().broadcast("&cSwapped!");

        // 120 seconds = 2 minutes
        int randomized = randInt(30, 120, random);
        int delay = 20 * randomized;
        new SwapTask().runTaskLater(DeathSwapPlugin.getInstance(), delay);
    }

    private void applyEffects(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 5 * 20, 4));
    }

    private int randInt(int min, int max, Random random) {
        return random.nextInt((max - min) + 1) + min;
    }

}
