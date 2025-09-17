package org.example.service;

import org.example.models.*;
import org.example.repository.GameRepository;
import org.example.repository.PlayerStatsRepository;

import java.util.List;
import java.util.Optional;

/**
 * Service class that demonstrates repository delete operations
 * This shows how to delete repositories in the TicTacToe application
 */
public class RepositoryManagementService {
    
    private final GameRepository gameRepository;
    private final PlayerStatsRepository playerStatsRepository;
    
    public RepositoryManagementService(GameRepository gameRepository, 
                                      PlayerStatsRepository playerStatsRepository) {
        this.gameRepository = gameRepository;
        this.playerStatsRepository = playerStatsRepository;
    }
    
    /**
     * Delete a specific game by ID
     * @param gameId The game ID to delete
     * @return true if game was deleted, false if not found
     */
    public boolean deleteGame(String gameId) {
        return gameRepository.deleteById(gameId);
    }
    
    /**
     * Delete all games for a specific player
     * @param player The player whose games should be deleted
     * @return Number of games deleted
     */
    public int deleteAllGamesForPlayer(Player player) {
        return gameRepository.deleteByPlayer(player);
    }
    
    /**
     * Delete all completed games (games that are finished)
     * @return Number of games deleted
     */
    public int deleteCompletedGames() {
        return gameRepository.deleteCompletedGames();
    }
    
    /**
     * Delete all games with a specific state
     * @param gameState The game state
     * @return Number of games deleted
     */
    public int deleteGamesByState(GameState gameState) {
        return gameRepository.deleteByGameState(gameState);
    }
    
    /**
     * Delete all saved games (complete repository cleanup)
     * @return Number of games deleted
     */
    public int deleteAllGames() {
        return gameRepository.deleteAll();
    }
    
    /**
     * Delete player statistics by player name
     * @param playerName The player name
     * @return Number of stats records deleted
     */
    public int deletePlayerStats(String playerName) {
        return playerStatsRepository.deleteByPlayerName(playerName);
    }
    
    /**
     * Delete statistics for inactive players (players with fewer than minimum games)
     * @param minGames Minimum number of games played
     * @return Number of stats records deleted
     */
    public int deleteInactivePlayerStats(int minGames) {
        return playerStatsRepository.deleteByMinGamesPlayed(minGames);
    }
    
    /**
     * Reset all player statistics (sets wins, losses, draws to 0)
     * @return Number of stats records reset
     */
    public int resetAllPlayerStats() {
        return playerStatsRepository.resetAllStats();
    }
    
    /**
     * Delete all player statistics
     * @return Number of stats records deleted
     */
    public int deleteAllPlayerStats() {
        return playerStatsRepository.deleteAll();
    }
    
    /**
     * Complete cleanup - delete all games and player statistics
     * @return Summary of deletion operations
     */
    public DeletionSummary deleteAllRepositoryData() {
        int gamesDeleted = gameRepository.deleteAll();
        int statsDeleted = playerStatsRepository.deleteAll();
        return new DeletionSummary(gamesDeleted, statsDeleted);
    }
    
    /**
     * Get repository statistics
     * @return Current repository statistics
     */
    public RepositoryStats getRepositoryStats() {
        long gameCount = gameRepository.count();
        long statsCount = playerStatsRepository.count();
        long completedGames = gameRepository.findByGameState(GameState.ENDED).size() + 
                              gameRepository.findByGameState(GameState.DRAW).size();
        long inProgressGames = gameRepository.findByGameState(GameState.IN_PROGRESS).size();
        
        return new RepositoryStats(gameCount, statsCount, completedGames, inProgressGames);
    }
    
    /**
     * Summary of deletion operations
     */
    public static class DeletionSummary {
        private final int gamesDeleted;
        private final int statsDeleted;
        
        public DeletionSummary(int gamesDeleted, int statsDeleted) {
            this.gamesDeleted = gamesDeleted;
            this.statsDeleted = statsDeleted;
        }
        
        public int getGamesDeleted() { return gamesDeleted; }
        public int getStatsDeleted() { return statsDeleted; }
        public int getTotalDeleted() { return gamesDeleted + statsDeleted; }
        
        @Override
        public String toString() {
            return String.format("Deletion Summary: %d games deleted, %d stats deleted, %d total records deleted",
                    gamesDeleted, statsDeleted, getTotalDeleted());
        }
    }
    
    /**
     * Repository statistics
     */
    public static class RepositoryStats {
        private final long totalGames;
        private final long totalStats;
        private final long completedGames;
        private final long inProgressGames;
        
        public RepositoryStats(long totalGames, long totalStats, long completedGames, long inProgressGames) {
            this.totalGames = totalGames;
            this.totalStats = totalStats;
            this.completedGames = completedGames;
            this.inProgressGames = inProgressGames;
        }
        
        public long getTotalGames() { return totalGames; }
        public long getTotalStats() { return totalStats; }
        public long getCompletedGames() { return completedGames; }
        public long getInProgressGames() { return inProgressGames; }
        
        @Override
        public String toString() {
            return String.format("Repository Stats: %d total games (%d completed, %d in progress), %d player stats",
                    totalGames, completedGames, inProgressGames, totalStats);
        }
    }
}