import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DelayedLinkComponent } from './delayed-link.component';

describe('DelayedLinkComponent', () => {
  let component: DelayedLinkComponent;
  let fixture: ComponentFixture<DelayedLinkComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DelayedLinkComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DelayedLinkComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
