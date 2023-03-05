import { CommonModule, NgFor } from '@angular/common';
import { Component, Input } from '@angular/core';
import { CastPipe } from 'src/app/pipes/cast.pipe';
import { InputComponent, InputProps } from './input/input.component';

@Component({
  standalone: true,
  selector: 'from-component',
  templateUrl: './from.component.html',
  styleUrls: ['./from.component.css'],
  imports: [InputComponent, CommonModule, CastPipe],
})
export class FromComponent {
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