package tech.shadowsystems.dw;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import tech.shadowsystems.dw.files.FileSystem;
import tech.shadowsystems.dw.tasks.SwapTask;
import tech.shadowsystems.dw.utility.ChatUtil;
import tech.shadowsystems.dw.world.WorldExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Copyright © 2016 Jordan Osterberg and Shadow Technical Systems LLC. All rights reserved. Please email jordan.osterberg@shadowsystems.tech for usage rights and other information.
 */
public class DeathSwap {
    private static DeathSwap ourInstance = new DeathSwap();
    public static DeathSwap getInstance() {
        return ourInstance;
    }
    private DeathSwap() {
    }

    private int gameID = 0;
    private UUID firstPlayer;
    private UUID secondPlayer;
    private List<UUID> queue = new ArrayList<>();
    private WorldExtension worldExtension;

    public void onEnable() {
        gameID = FileSystem.getInstance().getSystemStats().getGameid();
    }

    public void onDisable() {
        FileSystem.getInstance().getSystemStats().setGameid(this.gameID);
    }

    public void joinQueue(Player player) {
        queue.add(player.getUniqueId());

        if (queue.size() == 2) {
            startGame(Bukkit.getPlayer(queue.get(0)), Bukkit.getPlayer(queue.get(1)));
            queue.clear();
        }
    }

    public void startGame(final Player one, Player two) {
        if (!GameState.isGameState(GameState.LOBBY)) {
            return;
        }

        this.firstPlayer = one.getUniqueId();
        this.secondPlayer = two.getUniqueId();
        GameState.setGameState(GameState.WORLD_GENERATING);
        broadcast("&cThe world is generating, prepare for lag.");

        worldExtension = new WorldExtension(new BukkitRunnable() {
            @Override
            public void run() {
                GameState.setGameState(GameState.INGAME);
                broadcast("&cWorld generated. You are playing game #" + getGameID());
                broadcast("&aYou will now be teleported, and the game will start in 30 seconds.");
                new SwapTask().runTask(DeathSwapPlugin.getInstance());
            }
        });

    }

    public void broadcast(String message) {
        Bukkit.broadcastMessage(ChatUtil.formatWithPrefix(message));
    }

    public void stopGame() {

        gameID++;
    }

    public int getGameID() {
        return gameID;
    }

    public boolean isUserPlaying(Player player) {
        return firstPlayer == player.getUniqueId() || secondPlayer == player.getUniqueId();
    }

    /*

      // TODO LIST
      - Add listeners for death
      - Add admin command for spawning join NPC
      - Finish SwapTask
      - Add spectator option

      == General Idea ==
      2 players playing
      Takes whole server
      Creates new world every game
      No killing for 3 swaps

      == After release ==
      API
      ...

     */

}
