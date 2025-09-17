package org.example.models;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Model representing player statistics
 */
public class PlayerStats {
    private String id;
    private String playerName;
    private int gamesPlayed;
    private int gamesWon;
    private int gamesLost;
    private int gamesDraw;
    private double winRate;
    private LocalDateTime lastPlayed;
    private LocalDateTime createdAt;
    
    public PlayerStats() {
        this.createdAt = LocalDateTime.now();
        this.lastPlayed = LocalDateTime.now();
    }
    
    public PlayerStats(String playerName) {
        this();
        this.playerName = playerName;
        this.id = generateId(playerName);
    }
    
    private String generateId(String playerName) {
        return "player_" + playerName.toLowerCase().replaceAll("\\s+", "_") + "_" + System.currentTimeMillis();
    }
    
    public void updateStats(GameResult result) {
        this.gamesPlayed++;
        this.lastPlayed = LocalDateTime.now();
        
        switch (result) {
            case WIN:
                this.gamesWon++;
                break;
            case LOSS:
                this.gamesLost++;
                break;
            case DRAW:
                this.gamesDraw++;
                break;
        }
        
        updateWinRate();
    }
    
    private void updateWinRate() {
        if (gamesPlayed > 0) {
            this.winRate = (double) gamesWon / gamesPlayed;
        } else {
            this.winRate = 0.0;
        }
    }
    
    // Getters and Setters
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getPlayerName() {
        return playerName;
    }
    
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
    
    public int getGamesPlayed() {
        return gamesPlayed;
    }
    
    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
        updateWinRate();
    }
    
    public int getGamesWon() {
        return gamesWon;
    }
    
    public void setGamesWon(int gamesWon) {
        this.gamesWon = gamesWon;
        updateWinRate();
    }
    
    public int getGamesLost() {
        return gamesLost;
    }
    
    public void setGamesLost(int gamesLost) {
        this.gamesLost = gamesLost;
        updateWinRate();
    }
    
    public int getGamesDraw() {
        return gamesDraw;
    }
    
    public void setGamesDraw(int gamesDraw) {
        this.gamesDraw = gamesDraw;
        updateWinRate();
    }
    
    public double getWinRate() {
        return winRate;
    }
    
    public LocalDateTime getLastPlayed() {
        return lastPlayed;
    }
    
    public void setLastPlayed(LocalDateTime lastPlayed) {
        this.lastPlayed = lastPlayed;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlayerStats)) return false;
        PlayerStats that = (PlayerStats) o;
        return Objects.equals(id, that.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public String toString() {
        return "PlayerStats{" +
                "id='" + id + '\'' +
                ", playerName='" + playerName + '\'' +
                ", gamesPlayed=" + gamesPlayed +
                ", gamesWon=" + gamesWon +
                ", gamesLost=" + gamesLost +
                ", gamesDraw=" + gamesDraw +
                ", winRate=" + String.format("%.2f", winRate) +
                ", lastPlayed=" + lastPlayed +
                '}';
    }
}