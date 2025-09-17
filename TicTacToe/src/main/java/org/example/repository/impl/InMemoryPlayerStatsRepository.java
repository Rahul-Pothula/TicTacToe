package org.example.repository.impl;

import org.example.models.PlayerStats;
import org.example.repository.PlayerStatsRepository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * In-memory implementation of PlayerStatsRepository
 * This implementation demonstrates repository pattern with delete functionality for player statistics
 */
public class InMemoryPlayerStatsRepository implements PlayerStatsRepository {
    
    private final Map<String, PlayerStats> statsStorage = new ConcurrentHashMap<>();
    
    @Override
    public PlayerStats save(PlayerStats playerStats) {
        if (playerStats == null) {
            throw new IllegalArgumentException("PlayerStats cannot be null");
        }
        if (playerStats.getId() == null) {
            throw new IllegalArgumentException("PlayerStats ID cannot be null");
        }
        statsStorage.put(playerStats.getId(), playerStats);
        return playerStats;
    }
    
    @Override
    public Optional<PlayerStats> findById(String id) {
        if (id == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(statsStorage.get(id));
    }
    
    @Override
    public List<PlayerStats> findAll() {
        return new ArrayList<>(statsStorage.values());
    }
    
    @Override
    public boolean deleteById(String id) {
        if (id == null) {
            return false;
        }
        return statsStorage.remove(id) != null;
    }
    
    @Override
    public boolean delete(PlayerStats playerStats) {
        if (playerStats == null || playerStats.getId() == null) {
            return false;
        }
        return deleteById(playerStats.getId());
    }
    
    @Override
    public int deleteAll() {
        int count = statsStorage.size();
        statsStorage.clear();
        return count;
    }
    
    @Override
    public boolean existsById(String id) {
        if (id == null) {
            return false;
        }
        return statsStorage.containsKey(id);
    }
    
    @Override
    public long count() {
        return statsStorage.size();
    }
    
    @Override
    public List<PlayerStats> findByPlayerName(String playerName) {
        if (playerName == null) {
            return new ArrayList<>();
        }
        return statsStorage.values().stream()
                .filter(stats -> playerName.equals(stats.getPlayerName()))
                .collect(Collectors.toList());
    }
    
    @Override
    public int deleteByPlayerName(String playerName) {
        if (playerName == null) {
            return 0;
        }
        List<String> statsIdsToDelete = statsStorage.values().stream()
                .filter(stats -> playerName.equals(stats.getPlayerName()))
                .map(PlayerStats::getId)
                .collect(Collectors.toList());
        
        int deletedCount = 0;
        for (String statsId : statsIdsToDelete) {
            if (statsStorage.remove(statsId) != null) {
                deletedCount++;
            }
        }
        return deletedCount;
    }
    
    @Override
    public int deleteByMinGamesPlayed(int minGames) {
        if (minGames < 0) {
            return 0;
        }
        List<String> statsIdsToDelete = statsStorage.values().stream()
                .filter(stats -> stats.getGamesPlayed() < minGames)
                .map(PlayerStats::getId)
                .collect(Collectors.toList());
        
        int deletedCount = 0;
        for (String statsId : statsIdsToDelete) {
            if (statsStorage.remove(statsId) != null) {
                deletedCount++;
            }
        }
        return deletedCount;
    }
    
    @Override
    public int resetAllStats() {
        int resetCount = 0;
        for (PlayerStats stats : statsStorage.values()) {
            stats.setGamesPlayed(0);
            stats.setGamesWon(0);
            stats.setGamesLost(0);
            stats.setGamesDraw(0);
            resetCount++;
        }
        return resetCount;
    }
}