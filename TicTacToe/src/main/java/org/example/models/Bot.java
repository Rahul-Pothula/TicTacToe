package org.example.models;

import org.example.factory.BotPlayingStrategyFactory;
import org.example.strategies.botplayingstrategy.BotPlayingStrategy;

public class Bot extends Player {
    private BotDifficultyLevel botDifficultyLevel;
    private BotPlayingStrategy botPlayingStrategy;

    public Bot(String playerName, Character symbol, BotDifficultyLevel botDifficultyLevel) {
        super(playerName, symbol);
        this.botDifficultyLevel = botDifficultyLevel;
        this.botPlayingStrategy = BotPlayingStrategyFactory.getBotPlayingStrategy(botDifficultyLevel);
    }

    @Override
    public Move attemptMove(Board board) {
        Move move = botPlayingStrategy.makeBotMove(board);
        move.setPlayer(this);
        return move;
    }
}
