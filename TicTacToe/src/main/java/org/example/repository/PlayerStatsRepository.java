package org.example.repository;

import org.example.models.PlayerStats;

import java.util.List;

/**
 * Repository interface for PlayerStats entities
 */
public interface PlayerStatsRepository extends Repository<PlayerStats, String> {
    
    /**
     * Find player stats by player name
     * @param playerName The player name
     * @return List of player stats for the player
     */
    List<PlayerStats> findByPlayerName(String playerName);
    
    /**
     * Delete player stats by player name
     * @param playerName The player name
     * @return Number of stats records deleted
     */
    int deleteByPlayerName(String playerName);
    
    /**
     * Delete stats for players with less than minimum games played
     * @param minGames Minimum number of games played
     * @return Number of stats records deleted
     */
    int deleteByMinGamesPlayed(int minGames);
    
    /**
     * Reset all player stats (sets wins, losses, draws to 0)
     * @return Number of stats records reset
     */
    int resetAllStats();
}