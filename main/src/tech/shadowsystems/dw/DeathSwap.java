package tech.shadowsystems.dw;

import org.bukkit.Bukkit;
import org.bukkit.Location;
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
    private int swapsSoFar = 0;
    private Player firstPlayer;
    private Player secondPlayer;
    private List<UUID> queue = new ArrayList<>();
    private WorldExtension worldExtension;
    private boolean firstSwap = true;
    private boolean running = false;

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

    public void startGame(Player one, Player two) {
        if (!GameState.isGameState(GameState.LOBBY)) {
            return;
        }

        running = true;

        this.firstPlayer = one;
        this.secondPlayer = two;

        getFirstPlayer().getInventory().clear();
        getSecondPlayer().getInventory().clear();
        getFirstPlayer().getInventory().setArmorContents(null);
        getSecondPlayer().getInventory().setArmorContents(null);

        GameState.setGameState(GameState.WORLD_GENERATING);
        broadcast("&cThe world is generating, prepare for lag.");

        worldExtension = new WorldExtension(new BukkitRunnable() {
            @Override
            public void run() {
                GameState.setGameState(GameState.INGAME);
                broadcast("&cWorld generated.");
                broadcast("&aYou will now be teleported. The game has started.");
                new SwapTask().runTask(DeathSwapPlugin.getInstance());
            }
        });

    }

    public void broadcast(String message) {
        Bukkit.broadcastMessage(ChatUtil.formatWithPrefix(message));
    }

    public void stopGame(Player winner) {
        running = false;

        GameState.setGameState(GameState.RESETTING);

        getFirstPlayer().teleport(new Location(Bukkit.getWorld("world"), 0, 256, 0));
        getSecondPlayer().teleport(new Location(Bukkit.getWorld("world"), 0, 256, 0));

        getFirstPlayer().getInventory().clear();
        getSecondPlayer().getInventory().clear();
        getFirstPlayer().getInventory().setArmorContents(null);
        getSecondPlayer().getInventory().setArmorContents(null);

        broadcast("&a" + winner.getName() + " won deathswap!");

        worldExtension.deleteWorld();

        gameID++;

        GameState.setGameState(GameState.LOBBY);
    }

    public int getGameID() {
        return gameID;
    }

    public boolean isUserPlaying(Player player) {
        if (!isRunning()) {
            return true;
        }
        return firstPlayer == player || secondPlayer == player;
    }

    public boolean isFirstSwap() {
        return firstSwap;
    }

    public void setFirstSwap(boolean firstSwap) {
        this.firstSwap = firstSwap;
    }

    public Player getFirstPlayer() {
        return firstPlayer;
    }

    public Player getSecondPlayer() {
        return secondPlayer;
    }

    public WorldExtension getWorldExtension() {
        return worldExtension;
    }

    public int getSwapsSoFar() {
        return swapsSoFar;
    }

    public void setSwapsSoFar(int swapsSoFar) {
        this.swapsSoFar = swapsSoFar;
    }

    public boolean isRunning() {
        return running;
    }

    public boolean isInQueue(Player player) {
        return queue.contains(player.getUniqueId());
    }

    /*

      // TODO LIST
      - Add admin command for spawning join NPC
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
