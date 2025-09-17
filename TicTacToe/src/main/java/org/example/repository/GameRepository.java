package org.example.repository;

import org.example.models.Game;
import org.example.models.GameState;
import org.example.models.Player;

import java.util.List;

/**
 * Repository interface for Game entities with specific game-related operations
 */
public interface GameRepository extends Repository<Game, String> {
    
    /**
     * Find games by player
     * @param player The player to search for
     * @return List of games involving the player
     */
    List<Game> findByPlayer(Player player);
    
    /**
     * Find games by state
     * @param gameState The game state to search for
     * @return List of games with the specified state
     */
    List<Game> findByGameState(GameState gameState);
    
    /**
     * Find games by winner
     * @param winner The winning player
     * @return List of games won by the player
     */
    List<Game> findByWinner(Player winner);
    
    /**
     * Delete all games by player
     * @param player The player whose games should be deleted
     * @return Number of games deleted
     */
    int deleteByPlayer(Player player);
    
    /**
     * Delete all games with specific state
     * @param gameState The game state
     * @return Number of games deleted
     */
    int deleteByGameState(GameState gameState);
    
    /**
     * Delete all completed games (ENDED or DRAW)
     * @return Number of games deleted
     */
    int deleteCompletedGames();
}