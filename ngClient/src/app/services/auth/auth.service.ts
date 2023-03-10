import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, take } from 'rxjs';
import { AuthTokenResponse, User, UserRegistration } from 'src/app/typings';
import { environment } from 'src/environments/environment.development';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private HOST = environment.HOST;
  private authentication = new BehaviorSubject<AuthTokenResponse | boolean> (false);
  readonly store$ = this.authentication.asObservable();

  constructor(private http: HttpClient) {
    let auth = localStorage.getItem('auth');

    this.authentication.next(auth ? JSON.parse(auth) : false);
  }

  register(form: UserRegistration): Observable<AuthTokenResponse> {
    console.log("register");
    
    const res = this.http.post<AuthTokenResponse>(
      this.HOST + '/register',
      form
    ).pipe(take(1));

    res.subscribe({
      next: (data) => {
        localStorage.setItem('auth', JSON.stringify(data));
        this.authentication.next(data);
      },
    }).unsubscribe();

    return res;
  }

  logIn(form: User): Observable<AuthTokenResponse> {
    const res = this.http.post<AuthTokenResponse>(this.HOST + '/login', form);

    res.subscribe({
      next: (data) => {
        localStorage.setItem('auth', JSON.stringify(data));
        this.authentication.next(data);
      },
    }).unsubscribe();
    return res;
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
