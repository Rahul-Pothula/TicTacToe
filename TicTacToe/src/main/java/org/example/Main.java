package org.example;

import org.example.controllers.GameController;
import org.example.exceptions.InvalidBotCountException;
import org.example.exceptions.InvalidMoveException;
import org.example.exceptions.InvalidPlayersSizeExcpetion;
import org.example.models.*;

import java.util.ArrayList;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws InvalidBotCountException, InvalidPlayersSizeExcpetion, InvalidMoveException {
        int size = 3;
        List<Player> players = new ArrayList<>(size);

        players.add(new Player("Rahul", 'X'));
        players.add(new Bot("Rishab", 'O', BotDifficultyLevel.EASY));

        GameController gameController = new GameController();

        Game game = gameController.startGame(size, players);

        while(gameController.getGameState(game).equals(GameState.IN_PROGRESS)) {
            gameController.printGameState(game);
            gameController.attemptMove(game);
        }

        if(gameController.getGameState(game).equals(GameState.ENDED)) {
            gameController.printGameState(game);
            System.out.println(gameController.getWinner(game).getName() + " has WON the game!!");
        }
        else {
            gameController.printGameState(game);
            System.out.println("GAME DRAW");
        }
    }
}