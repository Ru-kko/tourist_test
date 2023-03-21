import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-page-container',
  templateUrl: './page-container.component.html',
  styleUrls: ['./page-container.component.css'],
})
export class PageContainerComponent {
  @Input() title = 'missing title';
  @Input() width: Widths = 'w8';
  @Input() height: Heigths = 'h9';
  @Input() floating = false;
}

type Widths = 'w4' | 'w5' | 'w6' | 'w7' | 'w8';
type Heigths = 'h5' | 'h6' | 'h7' | 'h8' | 'h9';
