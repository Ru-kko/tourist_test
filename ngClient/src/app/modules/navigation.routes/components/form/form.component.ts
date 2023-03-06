import { Component, Input } from '@angular/core';
import { InputComponent, InputProps } from './input/input.component';

@Component({
  selector: 'form-component',
  templateUrl: './form.component.html',
  styleUrls: ['./form.component.css'],
})
export class FormComponent {
  @Input() onSubmit?: (e: HTMLFormElement) => void;
  @Input() data: (InputProps[] | InputProps)[] = [];

  submit(e: Event) {
    this.onSubmit?.(e.target as HTMLFormElement);
  }

  isArray(obj: InputProps[] | InputProps): boolean {
    return Array.isArray(obj);
  }

  cast<T>(obj: any): T {
    return obj as T;
  }
}

export { InputComponent };
