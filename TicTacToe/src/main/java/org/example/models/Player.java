package org.example.models;

import java.util.Scanner;

public class Player {
    private String name;
    private Character symbol;
    private PlayerType playerType;

    public Player(String name, Character symbol) {
        this.name = name;
        this.symbol = symbol;
        this.playerType =  PlayerType.HUMAN;
    }

    public PlayerType getPlayerType() {
        return playerType;
    }

    public void setPlayerType(PlayerType playerType) {
        this.playerType = playerType;
    }


    public Character getSymbol() {
        return symbol;
    }

    public void setSymbol(Character symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Move attemptMove(Board board) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter the row: ");
        int row = sc.nextInt();
        System.out.println("Please enter the column: ");
        int col = sc.nextInt();

        return new Move(this, new Cell(row, col));
    }


}
