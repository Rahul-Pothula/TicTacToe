package org.example.strategies.botplayingstrategy;

import org.example.models.Board;
import org.example.models.Cell;
import org.example.models.Move;

import java.util.List;

public class EasyBotPlayingStrategy implements BotPlayingStrategy {
    @Override
    public Move makeBotMove(Board board) {
        List<List<Cell>> listOfCells = board.getBoard();

        for(List<Cell> cells : listOfCells) {
            for(Cell cell : cells) {
                if(cell.isEmpty()) {
                    return new Move(null, cell);
                }
            }
        }

        // It will never be null for sure
        return null;
    }
}
