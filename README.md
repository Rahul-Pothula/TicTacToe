This is an implementation of fully functional tictactoe game which supports diverse functionalities such as multiplayer, bot player and even the difficulty level which involves good quality code design by using design patterns such as builder, strategy, factory design patterns.

## Repository Management with Delete Operations

This TicTacToe implementation now includes a comprehensive repository pattern that answers the question **"How can I delete repositories?"**

### Features

- **Generic Repository Interface**: CRUD operations with delete functionality
- **Game Repository**: Manage saved games with various delete operations
- **Player Statistics Repository**: Track and manage player statistics with cleanup options
- **Repository Management Service**: High-level service for repository operations

### How to Delete Repository Data

#### 1. Delete Individual Games
```java
GameRepository gameRepository = new InMemoryGameRepository();
// Delete a specific game by ID
boolean deleted = gameRepository.deleteById("game_123");
```

#### 2. Delete Games by Criteria
```java
// Delete all completed games (ENDED or DRAW)
int completedDeleted = gameRepository.deleteCompletedGames();

// Delete all games for a specific player
int playerGamesDeleted = gameRepository.deleteByPlayer(player);

// Delete games by state
int gamesDeleted = gameRepository.deleteByGameState(GameState.IN_PROGRESS);
```

#### 3. Delete Player Statistics
```java
PlayerStatsRepository statsRepository = new InMemoryPlayerStatsRepository();
// Delete stats for a specific player
int statsDeleted = statsRepository.deleteByPlayerName("Alice");

// Delete inactive players (less than X games)
int inactiveDeleted = statsRepository.deleteByMinGamesPlayed(5);

// Reset all statistics (sets wins/losses to 0)
int resetCount = statsRepository.resetAllStats();
```

#### 4. Complete Repository Cleanup
```java
RepositoryManagementService service = new RepositoryManagementService(gameRepo, statsRepo);
// Delete all data from both repositories
RepositoryManagementService.DeletionSummary summary = service.deleteAllRepositoryData();
```

### Running the Delete Demo

To see all delete operations in action:
```bash
mvn exec:java -Dexec.mainClass="org.example.RepositoryDeleteDemo"
```

This demo shows:
- Creating sample game and statistics data
- Various delete operations (by ID, by criteria, bulk delete)
- Repository statistics before and after deletions
- Complete cleanup operations

### Repository Classes

- `Repository<T, ID>` - Generic repository interface
- `GameRepository` - Game-specific repository operations
- `PlayerStatsRepository` - Player statistics repository operations
- `InMemoryGameRepository` - In-memory implementation for games
- `InMemoryPlayerStatsRepository` - In-memory implementation for stats
- `RepositoryManagementService` - High-level service for repository management

Resources

To run this file, you can follow the following commands:
javac Main.java
java Main
<img width="1309" height="683" alt="image" src="https://github.com/user-attachments/assets/75494ec7-2de3-4691-ab24-d5bb0b8ab83e" />
<img width="1320" height="276" alt="image" src="https://github.com/user-attachments/assets/382375d3-b4ae-4fa4-afa8-f220524710d5" />
