package org.example.strategies.winningstrategy;

import org.example.models.Board;
import org.example.models.Move;

import java.util.HashMap;
import java.util.Map;

public class DiagonalWinningStrategy implements WinningStrategy {

    private Map<Integer, Map<Character, Integer>> diaMap = new HashMap<>();

    private Map<Character, Integer> leftDiaMap = new HashMap<>();
    private Map<Character, Integer> rightDiaMap = new HashMap<>();

    @Override
    public Boolean checkWinner(Board board, Move move) {

//        Integer col = move.getCell().getCol();
//        Integer row = move.getCell().getRow();
//
//        if(row != col || (row + col != board.getSize()))
//            return false;
//
//        Character symbol = move.getPlayer().getSymbol();
//
//        if(!diaMap.containsKey(col)){
//            diaMap.put(col, new HashMap<>());
//        }
//        if(!diaMap.get(col).containsKey(symbol)){
//            diaMap.get(col).put(symbol, 0);
//        }
//
//        diaMap.get(col).put(symbol, diaMap.get(col).get(symbol) + 1);
//
//        return diaMap.get(col).get(symbol) == board.getSize();

        Integer col = move.getCell().getCol();
        Integer row = move.getCell().getRow();

//        if(row != col || (row + col != board.getSize()))
//            return false;

        Character symbol = move.getPlayer().getSymbol();

        if(row == col) {
            if(!leftDiaMap.containsKey(symbol))
                leftDiaMap.put(symbol, 0);

            leftDiaMap.put(symbol, leftDiaMap.get(symbol) + 1);

            return leftDiaMap.get(symbol) == board.getSize();
        }

        else if(row + col == board.getSize() - 1) {
            if(!rightDiaMap.containsKey(symbol))
                rightDiaMap.put(symbol, 0);
            rightDiaMap.put(symbol, rightDiaMap.get(symbol) + 1);

            return rightDiaMap.get(symbol) == board.getSize();
        }

        return false;
    }
}