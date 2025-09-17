package org.example.repository.impl;

import org.example.models.Game;
import org.example.models.GameState;
import org.example.models.Player;
import org.example.repository.GameRepository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * In-memory implementation of GameRepository
 * This implementation demonstrates repository pattern with delete functionality
 */
public class InMemoryGameRepository implements GameRepository {
    
    private final Map<String, Game> gameStorage = new ConcurrentHashMap<>();
    
    @Override
    public Game save(Game game) {
        if (game == null) {
            throw new IllegalArgumentException("Game cannot be null");
        }
        if (game.getId() == null) {
            throw new IllegalArgumentException("Game ID cannot be null");
        }
        gameStorage.put(game.getId(), game);
        return game;
    }
    
    @Override
    public Optional<Game> findById(String id) {
        if (id == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(gameStorage.get(id));
    }
    
    @Override
    public List<Game> findAll() {
        return new ArrayList<>(gameStorage.values());
    }
    
    @Override
    public boolean deleteById(String id) {
        if (id == null) {
            return false;
        }
        return gameStorage.remove(id) != null;
    }
    
    @Override
    public boolean delete(Game game) {
        if (game == null || game.getId() == null) {
            return false;
        }
        return deleteById(game.getId());
    }
    
    @Override
    public int deleteAll() {
        int count = gameStorage.size();
        gameStorage.clear();
        return count;
    }
    
    @Override
    public boolean existsById(String id) {
        if (id == null) {
            return false;
        }
        return gameStorage.containsKey(id);
    }
    
    @Override
    public long count() {
        return gameStorage.size();
    }
    
    @Override
    public List<Game> findByPlayer(Player player) {
        if (player == null) {
            return new ArrayList<>();
        }
        return gameStorage.values().stream()
                .filter(game -> game.getPlayers().contains(player))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Game> findByGameState(GameState gameState) {
        if (gameState == null) {
            return new ArrayList<>();
        }
        return gameStorage.values().stream()
                .filter(game -> gameState.equals(game.getGameState()))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Game> findByWinner(Player winner) {
        if (winner == null) {
            return new ArrayList<>();
        }
        return gameStorage.values().stream()
                .filter(game -> winner.equals(game.getWinner()))
                .collect(Collectors.toList());
    }
    
    @Override
    public int deleteByPlayer(Player player) {
        if (player == null) {
            return 0;
        }
        List<String> gameIdsToDelete = gameStorage.values().stream()
                .filter(game -> game.getPlayers().contains(player))
                .map(Game::getId)
                .collect(Collectors.toList());
        
        int deletedCount = 0;
        for (String gameId : gameIdsToDelete) {
            if (gameStorage.remove(gameId) != null) {
                deletedCount++;
            }
        }
        return deletedCount;
    }
    
    @Override
    public int deleteByGameState(GameState gameState) {
        if (gameState == null) {
            return 0;
        }
        List<String> gameIdsToDelete = gameStorage.values().stream()
                .filter(game -> gameState.equals(game.getGameState()))
                .map(Game::getId)
                .collect(Collectors.toList());
        
        int deletedCount = 0;
        for (String gameId : gameIdsToDelete) {
            if (gameStorage.remove(gameId) != null) {
                deletedCount++;
            }
        }
        return deletedCount;
    }
    
    @Override
    public int deleteCompletedGames() {
        List<String> gameIdsToDelete = gameStorage.values().stream()
                .filter(game -> game.getGameState() == GameState.ENDED || 
                               game.getGameState() == GameState.DRAW)
                .map(Game::getId)
                .collect(Collectors.toList());
        
        int deletedCount = 0;
        for (String gameId : gameIdsToDelete) {
            if (gameStorage.remove(gameId) != null) {
                deletedCount++;
            }
        }
        return deletedCount;
    }
}