import {
  Component,
  EventEmitter,
  Input,
  OnChanges,
  Output,
  SimpleChanges,
} from '@angular/core';

@Component({
  selector: 'app-dropdown-menu',
  templateUrl: './dropdown-menu.component.html',
  styleUrls: ['./dropdown-menu.component.css'],
})
export class DropdownMenuComponent<T extends string> implements OnChanges {
  @Input() props: DropDownMenuArgs<T> = {
    open: false,
    buttons: [],
    x: 0,
    y: 0,
    saveData: null,
  };
  @Output() Click: EventEmitter<{ button: T; data: unknown }> =
    new EventEmitter();

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['props'] && changes['props'].currentValue.open) {
      const event = () => {
        this.props = {
          open: false,
          buttons: [],
          x: 0,
          y: 0,
          saveData: null,
        };
        document.removeEventListener('click', event);
      };
      setTimeout(() => {
        document.addEventListener('click', event);
      }, 200);
    }
  }

  clickHandler(btn: T) {
    this.Click.emit({ data: this.props.saveData, button: btn });
  }
}

export interface DropDownMenuArgs<T extends string> {
  open: boolean;
  buttons: DropDownBtn<T>[];
  saveData?: unknown;
  x: number;
  y: number;
}

export interface DropDownBtn<T extends string> {
  link?: boolean;
  href?: string;
  text: T;
}
