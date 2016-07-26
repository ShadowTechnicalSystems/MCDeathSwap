package tech.shadowsystems.dw;

import org.bukkit.entity.Player;
import tech.shadowsystems.dw.files.FileSystem;

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
    private boolean isGameRunning = false;
    private UUID firstPlayer;
    private UUID secondPlayer;

    public void onEnable() {
        gameID = FileSystem.getInstance().getSystemStats().getGameid();
    }

    public void onDisable() {
        FileSystem.getInstance().getSystemStats().setGameid(this.gameID);
    }

    public void startGame(Player one, Player two) {
        if (isGameRunning) {
            return;
        }

        this.isGameRunning = true;
        this.firstPlayer = one.getUniqueId();
        this.secondPlayer = two.getUniqueId();



    }

    public void stopGame() {

        gameID++;
    }

    public int getGameID() {
        return gameID;
    }


    /*

      // TODO LIST
      - Add listeners for death
      - Add commands for joining
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
