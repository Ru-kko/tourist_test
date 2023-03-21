import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { City, PageResponse } from 'src/app/typings';
import { environment } from 'src/environments/environment.development';
import { Observable } from 'rxjs';
import { AuthService } from 'src/app/services/auth/auth.service';

@Injectable({
  providedIn: 'root',
})
export class CityService {
  private endpoint: string = environment.HOST + '/city';
  private headers: HttpHeaders = new HttpHeaders();

  constructor(private http: HttpClient, private auth: AuthService) {
    this.auth.store$.subscribe((data) => {
      if (typeof data !== 'boolean') {
        this.headers = new HttpHeaders().set(
          'Authorization',
          `${data.tokenType}${data.token}`
        );
        return;
      }
      this.headers = new HttpHeaders();
      this.headers.delete('Authorization');
    });
  }

  getCities(page = 1): Observable<PageResponse<City>> {
    return this.http.get<PageResponse<City>>(this.endpoint + '?page=' + page);
  }

  reservateCity(cityId: number, date: string) {
    return this.http.post(`${this.endpoint}/${cityId}?day=${date}`, null, {
      headers: this.headers,
    });
  }

  createCity(city: City) {
    return this.http.post(this.endpoint, city, {
      headers: this.headers,
    });
  }

  editCity(city: City) {
    return this.http.put(this.endpoint, city, {
      headers: this.headers,
    });
  }

  deleteCity(cityId: number) {
    return this.http.delete(this.endpoint, {
      headers: this.headers,
      body: {
        id: cityId,
      },
    });
  }
}
