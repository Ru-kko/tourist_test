package com.tourist.app.database;

public interface EntityBase<T> {
  public T getId();

  public void setId(T id);
}
