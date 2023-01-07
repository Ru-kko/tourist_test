package com.tourist.app.entity;

import java.util.List;

public class PageResponse<T> {
  public List<T> content;
  public Long lenght;
  public Integer totalPages;

  public PageResponse(){}

  public PageResponse(List<T> content, Long lenght, Integer totalpages) {
    this.content = content;
    this.lenght = lenght;
    this.totalPages = totalpages;
  }
}
