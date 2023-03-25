import { Component, EventEmitter, Input, Output } from '@angular/core';
import { InputComponent, InputProps } from './input/input.component';

@Component({
  selector: 'app-form-component',
  templateUrl: './form.component.html',
  styleUrls: ['./form.component.css'],
})
export class FormComponent {
  @Output() Submit: EventEmitter<FormData> = new EventEmitter();
  @Input() data: (InputProps[] | InputProps)[] = [];

  submit(event: Event) {
    event.preventDefault();
    this.Submit.emit(new FormData(event.target as HTMLFormElement));
    return false;
  }

  isArray(obj: InputProps[] | InputProps): boolean {
    return Array.isArray(obj);
  }
}

export { InputComponent, InputProps };
