package com.tourist.app.services;

import java.util.List;
import java.util.Optional;

import com.tourist.app.dataBase.BaseRepository;
import com.tourist.app.dataBase.EntityBase;

public abstract class DatabaseService<T, V extends EntityBase<T>> {
  protected abstract BaseRepository<T, V> getRepo();

  public abstract V update(V entity);

  public List<V> getAll() {
    return (List<V>) getRepo().getAll();
  }

  public Optional<V> getById(T id) {
    return getRepo().getById(id);
  }

  public V save(V entity) {
    return getRepo().save(entity);
  }

  public void deleteById(T id) {
    getRepo().deleteByID(id);
  }

  public void delete(V entity) {
    getRepo().delete(entity);
  }
}
