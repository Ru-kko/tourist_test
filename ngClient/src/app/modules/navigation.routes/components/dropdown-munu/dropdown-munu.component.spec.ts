import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DropdownMunuComponent } from './dropdown-munu.component';

describe('DropdownMunuComponent', () => {
  let component: DropdownMunuComponent;
  let fixture: ComponentFixture<DropdownMunuComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DropdownMunuComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DropdownMunuComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
