package org.example.strategies.winningstrategy;

import org.example.models.Board;
import org.example.models.Move;

import java.util.HashMap;
import java.util.Map;

public class RowWinningStrategy implements WinningStrategy {

    private Map<Integer, Map<Character, Integer>> rowMap = new HashMap<>();

    @Override
    public Boolean checkWinner(Board board, Move move) {
        Integer row = move.getCell().getRow();
        Character symbol = move.getPlayer().getSymbol();

        if(!rowMap.containsKey(row)){
            rowMap.put(row, new HashMap<>());
        }
        if(!rowMap.get(row).containsKey(symbol)){
            rowMap.get(row).put(symbol, 0);
        }

        rowMap.get(row).put(symbol, rowMap.get(row).get(symbol) + 1);

        return rowMap.get(row).get(symbol) == board.getSize();
    }
}