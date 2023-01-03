package com.tourist.app.dataBase;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public abstract class BaseRepository<T, V extends EntityBase<T>> {

  public V save(V entity) {
    if (entity == null) return null;
    if (entity.getId() != null) {
      if (this.getById(entity.getId()).isPresent()) {
        return null;
      }
    }

    return getRepo().save(entity);
  }

  public V update(V entity) {
    if (entity.getId() == null) {
      return null;
    }

    if (this.getById(entity.getId()).isEmpty()) {
      return null;
    }
    return getRepo().save(entity);
  }

  public List<V> getAll() {
    return (List<V>) getRepo().findAll();
  }

  public Optional<V> getById(T id) {
    return getRepo().findById(id);
  }

  public void deleteByID(T id) {
    getRepo().deleteById(id);
  }

  public void delete(V entity) {
    if (entity == null) return;
    if (entity.getId() == null) return;

    getRepo().delete(entity);
  }

  protected abstract CrudRepository<V, T> getRepo();
}
