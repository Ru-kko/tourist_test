import { Component, EventEmitter, Input, Output } from '@angular/core';
import { InputComponent, InputProps } from './input/input.component';

@Component({
  selector: 'form-component',
  templateUrl: './form.component.html',
  styleUrls: ['./form.component.css'],
})
export class FormComponent<T> {
  @Output() onSubmit: EventEmitter<FormData> = new EventEmitter();
  @Input() data: (InputProps[] | InputProps)[] = [];
  formData: Partial<T> = {};

  submit(event: Event) {
    event.preventDefault();
    this.onSubmit.emit(new FormData(event.target as HTMLFormElement));
    return false;
  }

  isArray(obj: InputProps[] | InputProps): boolean {
    return Array.isArray(obj);
  }
}

export { InputComponent, InputProps };
