import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AuthService } from 'src/app/services/auth/auth.service';
import { AuthTokenResponse, PageResponse, Tourist } from 'src/app/typings';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class TouristApiService {
  private endpoint: string = environment.HOST + '/tourist';
  private headers: HttpHeaders = new HttpHeaders();

  constructor(private http: HttpClient, private auth: AuthService) {
    this.auth.store$.subscribe((data) => {
      if (typeof data === 'boolean') {
        this.headers = new HttpHeaders();
        this.headers.delete('Authorization');
        return;
      }
      this.headers = new HttpHeaders().set(
        'Authorization',
        `${data.tokenType}${data.token}`
      );
      return;
    });
  }

  getTourists(page = 1): Observable<PageResponse<Tourist>> {
    return this.http.get<PageResponse<Tourist>>(
      this.endpoint + '?page=' + page
    );
  }

  deleteMe() {
    return new Promise<void>((res, err) => {
      this.http
        .delete(this.endpoint, {
          headers: this.headers,
        })
        .subscribe({
          next: () => {
            this.auth.update(false);
            res();
          },
          error: err,
        });
    });
  }

  update(tourist: Tourist) {
    return new Promise<void>((res, err) => {
      this.http
        .put<{token: AuthTokenResponse}>(this.endpoint, tourist, {
          headers: this.headers,
        })
        .subscribe({
          next: (data) => {
            this.auth.update(data.token);
            res();
          },
          error: err,
        });
    });
  }
}
