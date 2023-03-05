import { Component, Input } from '@angular/core';

@Component({
  selector: 'open-toggle-btn',
  templateUrl: './close-btn.component.html',
  styleUrls: ['./close-btn.component.css'],
})
export class CloseBtnComponent {
  @Input() size: String = '50px';
  @Input() onclick?: (e: MouseEvent, status: btnState) => void;
  @Input() initialState: btnState = 'off';

  className: btnState = this.initialState;

  handleClick(e: MouseEvent) {
    switch (this.className) {
      case 'off':
        this.className = 'on';
        break;
      case 'on':
        this.className = 'off';
        break;
    }
    this.onclick?.(e, this.className);
  }
}

type btnState = 'on' | 'off';
