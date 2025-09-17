package org.example.repository.impl;

import org.example.models.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.example.exceptions.InvalidBotCountException;
import org.example.exceptions.InvalidPlayersSizeExcpetion;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for repository delete functionality
 */
public class RepositoryDeleteTest {
    
    private InMemoryGameRepository gameRepository;
    private InMemoryPlayerStatsRepository playerStatsRepository;
    private Player player1;
    private Player player2;
    
    @BeforeEach
    void setUp() {
        gameRepository = new InMemoryGameRepository();
        playerStatsRepository = new InMemoryPlayerStatsRepository();
        player1 = new Player("Alice", 'X');
        player2 = new Player("Bob", 'O');
    }
    
    @Test
    void testDeleteGameById() throws InvalidBotCountException, InvalidPlayersSizeExcpetion {
        // Create and save a game
        Game game = Game.getBuilder()
                .setSize(3)
                .setPlayers(List.of(player1, player2))
                .build();
        gameRepository.save(game);
        
        // Verify game exists
        assertTrue(gameRepository.existsById(game.getId()));
        assertEquals(1, gameRepository.count());
        
        // Delete the game
        boolean deleted = gameRepository.deleteById(game.getId());
        
        // Verify deletion
        assertTrue(deleted);
        assertFalse(gameRepository.existsById(game.getId()));
        assertEquals(0, gameRepository.count());
    }
    
    @Test
    void testDeleteGameByIdNotFound() {
        // Try to delete non-existent game
        boolean deleted = gameRepository.deleteById("non-existent-id");
        
        // Verify no deletion occurred
        assertFalse(deleted);
        assertEquals(0, gameRepository.count());
    }
    
    @Test
    void testDeleteAllGames() throws InvalidBotCountException, InvalidPlayersSizeExcpetion {
        // Create and save multiple games
        Game game1 = Game.getBuilder()
                .setSize(3)
                .setPlayers(List.of(player1, player2))
                .build();
        Game game2 = Game.getBuilder()
                .setSize(3)
                .setPlayers(List.of(player1, player2))
                .build();
        
        gameRepository.save(game1);
        gameRepository.save(game2);
        
        // Verify games exist
        assertEquals(2, gameRepository.count());
        
        // Delete all games
        int deletedCount = gameRepository.deleteAll();
        
        // Verify all games deleted
        assertEquals(2, deletedCount);
        assertEquals(0, gameRepository.count());
    }
    
    @Test
    void testDeleteCompletedGames() throws InvalidBotCountException, InvalidPlayersSizeExcpetion {
        // Create games with different states
        Game endedGame = Game.getBuilder()
                .setSize(3)
                .setPlayers(List.of(player1, player2))
                .build();
        endedGame.setGameState(GameState.ENDED);
        
        Game drawGame = Game.getBuilder()
                .setSize(3)
                .setPlayers(List.of(player1, player2))
                .build();
        drawGame.setGameState(GameState.DRAW);
        
        Game inProgressGame = Game.getBuilder()
                .setSize(3)
                .setPlayers(List.of(player1, player2))
                .build();
        inProgressGame.setGameState(GameState.IN_PROGRESS);
        
        gameRepository.save(endedGame);
        gameRepository.save(drawGame);
        gameRepository.save(inProgressGame);
        
        // Verify all games exist
        assertEquals(3, gameRepository.count());
        
        // Delete completed games
        int deletedCount = gameRepository.deleteCompletedGames();
        
        // Verify only completed games deleted
        assertEquals(2, deletedCount);
        assertEquals(1, gameRepository.count());
        
        // Verify in-progress game still exists
        Optional<Game> remainingGame = gameRepository.findById(inProgressGame.getId());
        assertTrue(remainingGame.isPresent());
        assertEquals(GameState.IN_PROGRESS, remainingGame.get().getGameState());
    }
    
    @Test
    void testDeletePlayerStats() {
        // Create and save player stats
        PlayerStats aliceStats = new PlayerStats("Alice");
        PlayerStats bobStats = new PlayerStats("Bob");
        
        playerStatsRepository.save(aliceStats);
        playerStatsRepository.save(bobStats);
        
        // Verify stats exist
        assertEquals(2, playerStatsRepository.count());
        
        // Delete Alice's stats
        int deletedCount = playerStatsRepository.deleteByPlayerName("Alice");
        
        // Verify deletion
        assertEquals(1, deletedCount);
        assertEquals(1, playerStatsRepository.count());
        
        // Verify Bob's stats still exist
        List<PlayerStats> bobStatsList = playerStatsRepository.findByPlayerName("Bob");
        assertEquals(1, bobStatsList.size());
    }
    
    @Test
    void testDeleteInactivePlayerStats() {
        // Create player stats with different game counts
        PlayerStats activePlayer = new PlayerStats("Active");
        activePlayer.setGamesPlayed(5);
        
        PlayerStats inactivePlayer = new PlayerStats("Inactive");
        inactivePlayer.setGamesPlayed(1);
        
        playerStatsRepository.save(activePlayer);
        playerStatsRepository.save(inactivePlayer);
        
        // Verify both stats exist
        assertEquals(2, playerStatsRepository.count());
        
        // Delete inactive players (less than 3 games)
        int deletedCount = playerStatsRepository.deleteByMinGamesPlayed(3);
        
        // Verify only inactive player deleted
        assertEquals(1, deletedCount);
        assertEquals(1, playerStatsRepository.count());
        
        // Verify active player still exists
        List<PlayerStats> activeStats = playerStatsRepository.findByPlayerName("Active");
        assertEquals(1, activeStats.size());
    }
    
    @Test
    void testResetAllPlayerStats() {
        // Create player stats with some game data
        PlayerStats player1Stats = new PlayerStats("Player1");
        player1Stats.setGamesPlayed(10);
        player1Stats.setGamesWon(7);
        player1Stats.setGamesLost(2);
        player1Stats.setGamesDraw(1);
        
        PlayerStats player2Stats = new PlayerStats("Player2");
        player2Stats.setGamesPlayed(5);
        player2Stats.setGamesWon(3);
        player2Stats.setGamesLost(2);
        
        playerStatsRepository.save(player1Stats);
        playerStatsRepository.save(player2Stats);
        
        // Reset all stats
        int resetCount = playerStatsRepository.resetAllStats();
        
        // Verify reset
        assertEquals(2, resetCount);
        assertEquals(2, playerStatsRepository.count());
        
        // Verify stats are reset
        List<PlayerStats> allStats = playerStatsRepository.findAll();
        for (PlayerStats stats : allStats) {
            assertEquals(0, stats.getGamesPlayed());
            assertEquals(0, stats.getGamesWon());
            assertEquals(0, stats.getGamesLost());
            assertEquals(0, stats.getGamesDraw());
            assertEquals(0.0, stats.getWinRate());
        }
    }
}