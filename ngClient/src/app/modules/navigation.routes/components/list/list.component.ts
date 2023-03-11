import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';

@Component({
  selector: 'table-component',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.css'],
})
export class ListComponent<T extends Object> implements OnInit {
  @Input() idCol?: keyof T;
  @Output() rowClick: EventEmitter<{row: T, event: MouseEvent}> = new EventEmitter();
  @Input() headers!: Headers<T>;
  @Input() data: T[] = [];

  constructor(private sanitizer: DomSanitizer) {}

  ngOnInit(): void {
    if (!this.headers) {
      throw new TypeError('headers cant be null');
    }
  }

  getKeys() {
    return Object.values(this.headers);
  }

  createRow(row: T) {
    let res = '';
    Object.entries<Header<T, keyof T>>(this.headers).forEach(([k, v]) => {
      if (v.render !== false) {
        res += `<td data-label="${v.name}">
          ${
            v.preprocess ? v.preprocess?.(row[k as keyof T]) : row[k as keyof T]
          } </td>
        `;
      }
    });
    return this.sanitizer.bypassSecurityTrustHtml(res);
  }

  handleClick(e: MouseEvent, row: T) {
    this.rowClick.emit({row: row, event: e});
  }
}

interface Header<T, K extends keyof T> {
  preprocess?: (d: T[K]) => string;
  name: string;
  render?: boolean;
}

export type Headers<T> = {
  [K in keyof T]: Header<T, keyof T>
}