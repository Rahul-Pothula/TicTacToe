package org.example.strategies.winningstrategy;

import org.example.models.Board;
import org.example.models.Move;

import java.util.HashMap;
import java.util.Map;

public class ColWinningStrategy implements WinningStrategy {

    private Map<Integer, Map<Character, Integer>> colMap = new HashMap<>();

    @Override
    public Boolean checkWinner(Board board, Move move) {
        Integer col = move.getCell().getCol();
        Character symbol = move.getPlayer().getSymbol();

        if(!colMap.containsKey(col)){
            colMap.put(col, new HashMap<>());
        }
        if(!colMap.get(col).containsKey(symbol)){
            colMap.get(col).put(symbol, 0);
        }

        colMap.get(col).put(symbol, colMap.get(col).get(symbol) + 1);

        return colMap.get(col).get(symbol) == board.getSize();
    }
}