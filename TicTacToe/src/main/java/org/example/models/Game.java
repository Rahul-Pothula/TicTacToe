package org.example.models;

import org.example.exceptions.InvalidBotCountException;
import org.example.exceptions.InvalidMoveException;
import org.example.exceptions.InvalidPlayersSizeExcpetion;
import org.example.strategies.winningstrategy.ColWinningStrategy;
import org.example.strategies.winningstrategy.DiagonalWinningStrategy;
import org.example.strategies.winningstrategy.RowWinningStrategy;
import org.example.strategies.winningstrategy.WinningStrategy;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private Board board;
    private List<Player> players;
    private Player winner;
    private List<Move> moves;

    public List<WinningStrategy> getWinningStrategies() {
        return winningStrategies;
    }

    public void setWinningStrategies(List<WinningStrategy> winningStrategies) {
        this.winningStrategies = winningStrategies;
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    public void setCurrentPlayerIndex(int currentPlayerIndex) {
        this.currentPlayerIndex = currentPlayerIndex;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public List<Move> getMoves() {
        return moves;
    }

    public void setMoves(List<Move> moves) {
        this.moves = moves;
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    private GameState gameState;
    private int currentPlayerIndex;
    List<WinningStrategy> winningStrategies;

    public static Builder getBuilder() {
        return new Builder();
    }

    private Game(int size, List<Player> players, List<WinningStrategy> winningStrategies) {
        this.players = players;
        this.board = new Board(size);
        this.winner = null;
        this.moves = new ArrayList<>();
        this.gameState = GameState.IN_PROGRESS;
        this.currentPlayerIndex = 0;
        this.winningStrategies = winningStrategies;
    }

    private void validateMove(Move move) throws InvalidMoveException {
//        Cell cell = move.getCell();
        int i = move.getCell().getRow();
        int j = move.getCell().getCol();

        if(!board.getBoard().get(i).get(j).isEmpty())
            throw new InvalidMoveException("This block is not empty");
    }

    public Boolean checkWinner(Move move, Board board) {
        for(WinningStrategy winningStrategy : winningStrategies) {
            if(winningStrategy.checkWinner(board, move))
                return true;
        }
        return false;
    }

    public void makeMove() throws InvalidMoveException {
        Player currentPlayer = players.get(currentPlayerIndex);
        Move move = currentPlayer.attemptMove(board);

        validateMove(move);

        System.out.println(currentPlayer.getName() + "is making a move at position (" + move.getCell().getRow() + ", " + move.getCell().getCol() + ")");


        Move finalMove = new Move(currentPlayer, move.getCell());
        moves.add(finalMove);

        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();

        board.getBoard().get(finalMove.getCell().getRow()).get(finalMove.getCell().getCol()).setPlayer(currentPlayer);
        board.getBoard().get(finalMove.getCell().getRow()).get(finalMove.getCell().getCol()).setPlayer(currentPlayer);
        board.getBoard().get(finalMove.getCell().getRow()).get(finalMove.getCell().getCol()).setCellStatus(CellStatus.FILLED);

        if(checkWinner(finalMove, board)) {
            gameState = GameState.ENDED;
            winner = currentPlayer;
        }
        else if(moves.size() == board.getSize() * board.getSize()) {
            gameState = GameState.DRAW;
        }
    }

    public void printBoard() {
        List<List<Cell>> cellsList = board.getBoard();

        for(List<Cell> cells: cellsList) {
            for(Cell cell: cells) {
                if(cell.getCellStatus() == CellStatus.AVAILABLE)
                    System.out.print("| | ");
                else
                    System.out.print("|" + cell.getPlayer().getSymbol() + "| ");
            }
            System.out.println();
        }
    }

    public static class Builder {
        private int size;
        private List<Player> players;

        public Builder setSize(int size) {
            this.size = size;
            return this;
        }

        public Builder setPlayers(List<Player> players) {
            this.players = players;
            return this;
        }


        private void validateBotCount() throws InvalidBotCountException {
            int botCount = 0;
            for (Player player : players) {
                if(player.getPlayerType() == PlayerType.BOT && botCount < 1) botCount++;

                if(botCount > 1)
                    throw new InvalidBotCountException("Bot count is more than 1");
            }
        }

        private void validatePlayersSize() throws InvalidPlayersSizeExcpetion {
            if(players.size() != size - 1)
                throw new InvalidPlayersSizeExcpetion("Number of players should be 1 less than the board size");
        }
        private void validateGame() throws InvalidBotCountException, InvalidPlayersSizeExcpetion {
            validatePlayersSize();
            validateBotCount();
        }

        public Game build() throws InvalidBotCountException, InvalidPlayersSizeExcpetion {
            validateGame();
            List<WinningStrategy> winningStrategies = new ArrayList<>();
            winningStrategies.add(new RowWinningStrategy());
            winningStrategies.add(new ColWinningStrategy());
            winningStrategies.add(new DiagonalWinningStrategy());
            return new Game(size, players, winningStrategies);
        }
    }


}
