import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-open-toggle-btn',
  templateUrl: './close-btn.component.html',
  styleUrls: ['./close-btn.component.css'],
})
export class CloseBtnComponent {
  @Input() size = '50px';
  @Input() onclick?: (e: MouseEvent, status: boolean) => void;
  /**
   * false -> show menu
   * true -> show x to close
   */
  @Input() state = false;


  handleClick(e: MouseEvent) {
    this.state = !this.state;
    this.onclick?.(e, this.state);
  }
}

