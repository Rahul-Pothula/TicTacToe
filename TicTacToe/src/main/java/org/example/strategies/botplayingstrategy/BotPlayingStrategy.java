package org.example.strategies.botplayingstrategy;

import org.example.models.Board;
import org.example.models.Move;

public interface BotPlayingStrategy {
    public Move makeBotMove(Board board);
}
