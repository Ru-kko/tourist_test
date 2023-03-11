import {
  Component,
  EventEmitter,
  Input,
  OnChanges,
  Output,
  SimpleChanges,
} from '@angular/core';

@Component({
  selector: 'dropdown-munu',
  templateUrl: './dropdown-munu.component.html',
  styleUrls: ['./dropdown-munu.component.css'],
})
export class DropdownMunuComponent<T extends string> implements OnChanges {
  @Input() props: DropDownMenuArgs<T> = {
    open: false,
    buttons: [],
    x: 0,
    y: 0,
    saveData: null,
  };
  @Output() onClick: EventEmitter<{ button: T; data: any }> =
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
    this.onClick.emit({ data: this.props.saveData, button: btn });
  }
}

export interface DropDownMenuArgs<T extends string> {
  open: boolean;
  buttons: DropDownBtn<T>[];
  saveData?: any;
  x: number;
  y: number;
}

export interface DropDownBtn<T extends string> {
  link?: boolean;
  href?: string;
  text: T;
}
