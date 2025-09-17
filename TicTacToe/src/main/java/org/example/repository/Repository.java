package org.example.repository;

import java.util.List;
import java.util.Optional;

/**
 * Generic repository interface for CRUD operations
 * @param <T> The entity type
 * @param <ID> The identifier type
 */
public interface Repository<T, ID> {
    
    /**
     * Save an entity
     * @param entity The entity to save
     * @return The saved entity
     */
    T save(T entity);
    
    /**
     * Find an entity by its identifier
     * @param id The identifier
     * @return Optional containing the entity if found
     */
    Optional<T> findById(ID id);
    
    /**
     * Find all entities
     * @return List of all entities
     */
    List<T> findAll();
    
    /**
     * Delete an entity by its identifier
     * @param id The identifier of the entity to delete
     * @return true if entity was deleted, false if not found
     */
    boolean deleteById(ID id);
    
    /**
     * Delete an entity
     * @param entity The entity to delete
     * @return true if entity was deleted, false if not found
     */
    boolean delete(T entity);
    
    /**
     * Delete all entities
     * @return Number of entities deleted
     */
    int deleteAll();
    
    /**
     * Check if an entity exists by identifier
     * @param id The identifier
     * @return true if entity exists
     */
    boolean existsById(ID id);
    
    /**
     * Count total number of entities
     * @return Total count
     */
    long count();
}