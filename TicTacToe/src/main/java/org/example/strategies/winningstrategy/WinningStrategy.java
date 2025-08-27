package org.example.strategies.winningstrategy;

import org.example.models.Board;
import org.example.models.Move;

public interface WinningStrategy {
    public Boolean checkWinner(Board board, Move move);
}
