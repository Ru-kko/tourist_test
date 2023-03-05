import { Component, Input } from '@angular/core';
import { InputProps } from './input/input.component';

@Component({
  selector: 'from-component',
  templateUrl: './form.component.html',
  styleUrls: ['./form.component.css'],
})
export class FormComponent {
  @Input() onSubmit?: (e: HTMLFormElement) => void;
  @Input() data: (InputProps[] | InputProps)[] = [];

  submit(e: Event) {
    this.onSubmit?.((e.target) as HTMLFormElement);
  }

  isArray(obj: InputProps[] | InputProps): boolean {
    return Array.isArray(obj)
  }

  cast<T>(obj: any): T {
    return obj as T;
  }
}