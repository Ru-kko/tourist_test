package com.tourist.app.services.database;

import java.util.Optional;

import org.springframework.data.domain.Page;

import com.tourist.app.database.EntityBase;

/**
 * This is an interface that defines the methods that will be implemented by the database service classes.
 */
interface IDatabaBaseService<T, V extends EntityBase<T>> {
  /**
   * Get all the values, and return them in a page.
   * 
   * @param page The page number to return.
   * @return A page of value objects.
   */
  public Page<V> getAll(Integer page);

  /**
   * Returns the value associated with the given id, or an empty Optional if no
   * value is associated with
   * the id.
   * 
   * @param id The id of the entity to be retrieved.
   * @return Optional<V>
   */
  public Optional<V> getById(T id);

  /**
   * Save the value to the database and return the saved value.
   * is esential check if given entity has not an id to be saved
   * 
   * @param value The value to be saved.
   * @return The value that was saved, it will be null if the value couldn't save
   */
  public V save(V value);

  /**
   * Update the value of the current node to the given value and return the new value.
   * 
   * Null values in the given entity will be not change
   * 
   * @param value The value to be updated.
   * @return The updated value.
   */
  public V update(V value);

  /**
   * Delete the object with the given ID.
   * 
   * @param id The ID of the object to delete.
   */
  public void deleteByID(T id);

  /**
   * Delete the entity the database.
   * 
   * @param value The value to be deleted.
   */
  public void delete(V value);

}
