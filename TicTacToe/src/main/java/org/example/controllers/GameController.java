package org.example.controllers;

import org.example.exceptions.InvalidBotCountException;
import org.example.exceptions.InvalidMoveException;
import org.example.exceptions.InvalidPlayersSizeExcpetion;
import org.example.models.Game;
import org.example.models.GameState;
import org.example.models.Player;

import java.util.List;

public class GameController {
    public Game startGame(int size, List<Player> players) throws InvalidBotCountException, InvalidPlayersSizeExcpetion {
        return Game.getBuilder().setSize(size).setPlayers(players).build();
    }

    public void attemptMove(Game game) throws InvalidMoveException {
        game.makeMove();
    }

    public GameState getGameState(Game game) {
        return game.getGameState();
    }

    public Player getWinner(Game game) {
        return game.getWinner();
    }

    public void printGameState(Game game) {
        game.printBoard();
    }

}
