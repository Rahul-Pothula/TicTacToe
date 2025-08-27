package org.example.models;

public class Cell {
    private int row;
    private int col;
    private Player player;
    private CellStatus cellStatus;

    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
        this.cellStatus = CellStatus.AVAILABLE;
    }

    public CellStatus getCellStatus() {
        return cellStatus;
    }

    public void setCellStatus(CellStatus cellStatus) {
        this.cellStatus = cellStatus;
    }

//    private CellStatus cellStatus = CellStatus.AVAILABLE;


    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public Boolean isEmpty() {
        return this.cellStatus == CellStatus.AVAILABLE;
    }

}
