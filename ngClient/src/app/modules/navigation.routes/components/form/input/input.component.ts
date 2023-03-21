import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-form-component-input',
  templateUrl: './input.component.html',
  styleUrls: ['./input.component.css']
})
export class InputComponent implements OnInit {
  @Input() inputProps!: InputProps;

  changeValue(name:string, event: Event) {
    this.inputProps.onChange?.(name, (event.target as HTMLInputElement).value ?? '');
  }

  ngOnInit(): void {
    if (!this.inputProps)
      throw new Error('Cannot read properties of this input');
  }
}

export interface InputProps {
  name: string;
  type: 'text' | 'password' | 'date' | 'number' | 'submit' | 'button';
  required?: boolean;
  default?: string;
  readOnly?: boolean;
  label: string;
  onChange?: (name: string, value: string) => void;
  onClick?: (e: Event) => void;
}
