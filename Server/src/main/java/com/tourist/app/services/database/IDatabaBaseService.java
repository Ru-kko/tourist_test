package com.tourist.app.services.database;

import java.util.Optional;

import org.springframework.data.domain.Page;

import com.tourist.app.database.EntityBase;

public interface IDatabaBaseService<T, V extends EntityBase<T>> {
  public Page<V> getAll(Integer page);

  public Optional<V> getById(T id);

  public V save(V value);

  public V update(V value);

  public void deleteByID(T id);

  public void delete(V value);

}
