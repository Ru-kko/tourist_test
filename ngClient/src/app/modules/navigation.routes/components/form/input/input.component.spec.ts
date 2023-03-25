import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InputComponent } from './input.component';

describe('InputComponent', () => {
  let component: InputComponent;
  let fixture: ComponentFixture<InputComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ InputComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(InputComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();

    component.inputProps = {
      name: 'name',
      type: 'number',
      label: 'create'
    }
    fixture.detectChanges();

    const elm = (fixture.nativeElement as HTMLElement).querySelector('input');

    expect(elm?.required).toBeFalse();
    expect(elm?.readOnly).toBeFalse();
    expect(elm?.type).toBe('number')
    expect(elm?.value).toBeFalsy();
  });

  it('should create an input with default value, required and read only', () => {
    component.inputProps = {
      name: 'default name',
      type: 'text',
      required: true,
      readOnly: true,
      label: 'default',
      default: 'default value'
    }
    fixture.detectChanges();

    const elm = (fixture.nativeElement as HTMLElement).querySelector('input');
    expect(elm?.required).toBeTrue();
    expect(elm?.readOnly).toBeTrue();
    expect(elm?.type).toBe('text')
    expect(elm?.value).toBe(component.inputProps.default);
  })
});
