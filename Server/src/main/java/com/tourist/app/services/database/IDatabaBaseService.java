package com.tourist.app.services.database;

import java.util.Optional;

import com.tourist.app.database.EntityBase;
import com.tourist.app.entity.PageResponse;

public interface IDatabaBaseService<T, V extends EntityBase<T>> {
  public PageResponse<V> getAll(Integer page);

  public Optional<V> getById(T id);

  public V save(V value);

  public V update(V value);

  public void deleteByID(T id);

  public void delete(V value);

}
