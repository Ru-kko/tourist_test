import { ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';

import { CloseBtnComponent } from './close-btn.component';

describe('CloseBtnComponent', () => {
  let component: CloseBtnComponent;
  let fixture: ComponentFixture<CloseBtnComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CloseBtnComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CloseBtnComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should change the class name when state changes', () => {
    component.state = true;
    fixture.detectChanges();

    const open = fixture.debugElement.query(By.css('button')).nativeElement as HTMLButtonElement;
    console.log(open.className);
    expect(open.className).toBe('on');

    component.state = false;
    fixture.detectChanges();

    const close = fixture.debugElement.query(By.css('button')).nativeElement as HTMLButtonElement;
    console.log(close.className);
    expect(close.className).toBe('');
  })
});
