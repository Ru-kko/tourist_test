import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-pagination',
  templateUrl: './pagination.component.html',
  styleUrls: ['./pagination.component.css'],
})
export class PaginationComponent implements OnInit {
  @Input() pages = 1;
  @Input() actual = 1;
  @Input() onclick?: (n: number) => void;

  range!: number[];
    
  ngOnInit(): void {
    this.range = this.getPagingRange(this.actual, this.pages);
  }

  clickDecrease() {
    if (this.actual > 1) {
      this.onclick?.(this.actual - 1);
      this.range = this.getPagingRange(this.actual, this.pages);
    }
  }
  clickIncrease() {
    if (this.actual < this.pages) {
      this.onclick?.(this.actual + 1);
      this.range = this.getPagingRange(this.actual, this.pages);
    }
  }

  getPagingRange(n: number, total: number): number[] {
    let length = 5;

    if (length > total) length = total;

    let start = n - Math.floor(length / 2);
    start = Math.max(start, 1);
    start = Math.min(start, 1 + total - length);

    return Array.from({ length: length }, (_, i) => start + i);
  }
}
