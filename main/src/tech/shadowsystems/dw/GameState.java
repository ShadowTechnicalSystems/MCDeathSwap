package tech.shadowsystems.dw;

/**
 * Copyright © 2016 Jordan Osterberg and Shadow Technical Systems LLC. All rights reserved. Please email jordan.osterberg@shadowsystems.tech for usage rights and other information.
 */
public enum GameState {

    LOBBY,
    WORLD_GENERATING,
    INGAME,
    RESETTING;

    GameState() {} // If you wanted, you could have other variables within this enum

    public static GameState gameState = LOBBY;

    public static GameState getGameState() {
        return gameState;
    }

    public static void setGameState(GameState gameState) {
        GameState.gameState = gameState;
    }

    public static boolean isGameState(GameState gameState) {
        return GameState.gameState == gameState;
    }

}
