package com.tourist.app.entity;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * It's a class that represents esential data of a page with data
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PageResponse<T> {
  private List<T> content;
  private Long lenght;
  private Integer totalPages;
}
