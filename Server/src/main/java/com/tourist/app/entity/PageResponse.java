package com.tourist.app.entity;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class PageResponse<T> {
  public List<T> content;
  public Long lenght;
  public Integer totalPages;
}
