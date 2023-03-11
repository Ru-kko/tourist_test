import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, Observable, take } from 'rxjs';
import { AuthTokenResponse, User, UserRegistration } from 'src/app/typings';
import { environment } from 'src/environments/environment.development';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private HOST = environment.HOST;
  private authentication = new BehaviorSubject<AuthTokenResponse | boolean>(
    false
  );
  readonly store$ = this.authentication.asObservable();

  constructor(private http: HttpClient) {
    let auth = localStorage.getItem('auth');

    this.authentication.next(auth ? JSON.parse(auth) : false);
  }

  register(form: UserRegistration): Promise<AuthTokenResponse> {
    return new Promise<AuthTokenResponse>((res, rej) => {
      this.http
        .post<AuthTokenResponse>(this.HOST + '/register', form)
        .subscribe({
          next: (data) => {
            localStorage.setItem('auth', JSON.stringify(data));
            this.authentication.next(data);
            res(data);
          },
          error: (e) => rej(e),
        });
    });
  }

  logIn(form: User): Promise<AuthTokenResponse> {
    return new Promise<AuthTokenResponse>((res, rej) => {
      this.http.post<AuthTokenResponse>(this.HOST + '/login', form).subscribe({
        next: (data) => {
          localStorage.setItem('auth', JSON.stringify(data));
          this.authentication.next(data);
          res(data);
        },
        error: rej
      });
    });
  }

  update(token: AuthTokenResponse) {
    localStorage.setItem('auth', JSON.stringify(token));
    this.authentication.next(token);
  }

  logOut() {
    localStorage.removeItem('auth');
    this.authentication.next(false);
  }
}
