package org.example;

import org.example.models.*;
import org.example.repository.GameRepository;
import org.example.repository.PlayerStatsRepository;
import org.example.repository.impl.InMemoryGameRepository;
import org.example.repository.impl.InMemoryPlayerStatsRepository;
import org.example.service.RepositoryManagementService;
import org.example.exceptions.InvalidBotCountException;
import org.example.exceptions.InvalidPlayersSizeExcpetion;

import java.util.ArrayList;
import java.util.List;

/**
 * Demo class showing how to delete repositories in the TicTacToe application
 * This answers the question: "How can I delete repositories?"
 */
public class RepositoryDeleteDemo {
    
    public static void main(String[] args) throws InvalidBotCountException, InvalidPlayersSizeExcpetion {
        // Initialize repositories
        GameRepository gameRepository = new InMemoryGameRepository();
        PlayerStatsRepository playerStatsRepository = new InMemoryPlayerStatsRepository();
        
        // Initialize the repository management service
        RepositoryManagementService repositoryService = new RepositoryManagementService(
                gameRepository, playerStatsRepository);
        
        System.out.println("=== TicTacToe Repository Delete Operations Demo ===\n");
        
        // Create some sample data
        createSampleData(gameRepository, playerStatsRepository);
        
        // Show initial repository stats
        System.out.println("Initial repository state:");
        System.out.println(repositoryService.getRepositoryStats());
        System.out.println();
        
        // Demonstrate different delete operations
        demonstrateDeleteOperations(repositoryService);
    }
    
    private static void createSampleData(GameRepository gameRepository, 
                                        PlayerStatsRepository playerStatsRepository) 
            throws InvalidBotCountException, InvalidPlayersSizeExcpetion {
        
        // Create players
        Player player1 = new Player("Alice", 'X');
        Player player2 = new Player("Bob", 'O');
        Player player3 = new Bot("BotCharlie", 'X', BotDifficultyLevel.EASY);
        
        // Create some games
        List<Player> players1 = List.of(player1, player2);
        List<Player> players2 = List.of(player1, player3);
        List<Player> players3 = List.of(player2, player3);
        
        Game game1 = Game.getBuilder().setSize(3).setPlayers(players1).build();
        Game game2 = Game.getBuilder().setSize(3).setPlayers(players2).build();
        Game game3 = Game.getBuilder().setSize(3).setPlayers(players3).build();
        
        // Simulate different game states
        game1.setGameState(GameState.ENDED);
        game1.setWinner(player1);
        
        game2.setGameState(GameState.DRAW);
        
        game3.setGameState(GameState.IN_PROGRESS);
        
        // Save games
        gameRepository.save(game1);
        gameRepository.save(game2);
        gameRepository.save(game3);
        
        // Create player statistics
        PlayerStats aliceStats = new PlayerStats("Alice");
        aliceStats.updateStats(GameResult.WIN);
        aliceStats.updateStats(GameResult.DRAW);
        
        PlayerStats bobStats = new PlayerStats("Bob");
        bobStats.updateStats(GameResult.LOSS);
        bobStats.updateStats(GameResult.DRAW);
        bobStats.updateStats(GameResult.WIN);
        
        PlayerStats charlieStats = new PlayerStats("BotCharlie");
        charlieStats.updateStats(GameResult.LOSS);
        
        // Save player stats
        playerStatsRepository.save(aliceStats);
        playerStatsRepository.save(bobStats);
        playerStatsRepository.save(charlieStats);
        
        System.out.println("Sample data created: 3 games, 3 player stats\n");
    }
    
    private static void demonstrateDeleteOperations(RepositoryManagementService repositoryService) {
        System.out.println("=== Repository Delete Operations ===\n");
        
        // 1. Delete completed games
        System.out.println("1. Deleting completed games...");
        int completedDeleted = repositoryService.deleteCompletedGames();
        System.out.println("Deleted " + completedDeleted + " completed games");
        System.out.println("After deletion: " + repositoryService.getRepositoryStats());
        System.out.println();
        
        // 2. Delete player stats for inactive players (less than 2 games)
        System.out.println("2. Deleting stats for inactive players (< 2 games)...");
        int inactiveDeleted = repositoryService.deleteInactivePlayerStats(2);
        System.out.println("Deleted " + inactiveDeleted + " inactive player stats");
        System.out.println("After deletion: " + repositoryService.getRepositoryStats());
        System.out.println();
        
        // 3. Delete specific player stats
        System.out.println("3. Deleting stats for player 'Bob'...");
        int bobStatsDeleted = repositoryService.deletePlayerStats("Bob");
        System.out.println("Deleted " + bobStatsDeleted + " stats records for Bob");
        System.out.println("After deletion: " + repositoryService.getRepositoryStats());
        System.out.println();
        
        // 4. Reset remaining player stats
        System.out.println("4. Resetting all remaining player statistics...");
        int statsReset = repositoryService.resetAllPlayerStats();
        System.out.println("Reset " + statsReset + " player stats records");
        System.out.println("After reset: " + repositoryService.getRepositoryStats());
        System.out.println();
        
        // 5. Complete cleanup - delete all repository data
        System.out.println("5. Performing complete repository cleanup...");
        RepositoryManagementService.DeletionSummary summary = repositoryService.deleteAllRepositoryData();
        System.out.println(summary);
        System.out.println("Final state: " + repositoryService.getRepositoryStats());
        System.out.println();
        
        System.out.println("=== Repository Delete Operations Demo Complete ===");
        System.out.println("\\nThis demo shows various ways to delete repository data:");
        System.out.println("- Delete specific games by ID");
        System.out.println("- Delete games by player");
        System.out.println("- Delete games by state (completed, in-progress)");
        System.out.println("- Delete all games");
        System.out.println("- Delete player statistics by name");
        System.out.println("- Delete inactive player statistics");
        System.out.println("- Reset player statistics");
        System.out.println("- Complete repository cleanup");
    }
}