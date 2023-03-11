import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/services/auth/auth.service';
import { InputProps } from '../navigation.routes/components';
import { User, UserRegistration } from 'src/app/typings';
import { HttpErrorResponse } from '@angular/common/http';
import { Location } from '@angular/common';

@Component({
  selector: 'app-auth-page',
  templateUrl: './auth-page.component.html',
  styleUrls: ['./auth-page.component.css'],
})
export class AuthPageComponent implements OnInit {
  title = 'Log In';
  login: (InputProps[] | InputProps)[] = [
    { name: 'cardId', type: 'number', label: 'Card ID', required: true },
    { name: 'password', type: 'password', label: 'Password', required: true },
    [
      { name: 'submit', type: 'submit', label: 'Log In' },
      {
        name: 'change',
        type: 'button',
        label: 'Sign Up',
        onClick: () => {
          this.title = 'Sign Up';
        },
      },
    ],
  ];
  signUp: (InputProps[] | InputProps)[] = [
    [
      {
        name: 'name',
        type: 'text',
        label: 'Frist Name',
        required: true,
      },
      {
        name: 'lastName',
        type: 'text',
        label: 'Last Name',
        required: true,
      },
    ],
    [
      {
        name: 'idCard',
        type: 'number',
        label: 'Card ID',
        required: true,
      },
      { name: 'password', type: 'password', label: 'Password', required: true },
    ],
    [
      {
        name: 'travelFrequency',
        type: 'number',
        label: 'TravelFrequency',
        required: true,
      },
      {
        name: 'travelBudget',
        type: 'number',
        label: 'TravelBudget',
        required: true,
      },
    ],
    {
      name: 'bornDate',
      type: 'date',
      label: 'BornDate',
    },
    [
      { name: 'submit', type: 'submit', label: 'Sign Up', required: true },
      {
        name: 'change',
        type: 'button',
        label: 'Log In',
        onClick: () => {
          this.title = 'Log In';
        },
      },
    ],
  ];

  constructor(private authService: AuthService, private router: Location) {}

  ngOnInit(): void {
    this.authService.store$.subscribe((data) => {
      if (data) this.router.back();
    });
  }

  onLogIn(form: FormData) {
    let data: User = {
      cardId: form.get('cardId')?.toString() ?? '',
      password: form.get('password')?.toString() ?? '',
    };

    this.authService.logIn(data).then(() => this.router.back()).catch(this.haddleError)
  }

  onSignUp(form: FormData) {
    const data: UserRegistration = {
      password: form.get('password')?.toString() ?? '',
      tourist: {
        idCard: form.get('idCard')?.valueOf() as string,
        name: form.get('name')?.toString() ?? '',
        lastName: form.get('lastName')?.toString() ?? '',
        bornDate: form.get('bornDate')?.toString() ?? '',
        travelBudget: parseInt(form.get('travelBudget')?.valueOf() as string),
        travelFrequency: parseInt(
          form.get('travelFrequency')?.valueOf() as string
        ),
      },
    };

    this.authService.register(data).then(() => this.router.back()).catch(this.haddleError)
  }

  private haddleError(e: HttpErrorResponse) {
    if (e.status < 500 && e.status >= 400) {
      return alert('check your credentials');
    }
    console.error(e);
  }
}
