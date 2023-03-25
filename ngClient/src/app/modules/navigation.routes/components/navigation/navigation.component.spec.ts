import { HttpClient, HttpHandler } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { AuthService } from 'src/app/services/auth/auth.service';
import { CloseBtnComponent } from '../';

import { NavigationComponent } from './navigation.component';

describe('NavigationComponent', () => {
  let component: NavigationComponent;
  let fixture: ComponentFixture<NavigationComponent>

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FontAwesomeModule, RouterTestingModule],
      declarations: [NavigationComponent, CloseBtnComponent],
      providers: [AuthService, HttpClient, HttpHandler],
    }).compileComponents();

    fixture = TestBed.createComponent(NavigationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should change class when nav state is changed', () => {
    component.navState = true;
    fixture.detectChanges()
    
    const elm: HTMLElement = fixture.nativeElement.querySelector('nav');

    expect(elm.className).toBe('nav-open')
  })
});
