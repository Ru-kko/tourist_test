import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { City, PageResponse } from 'src/app/typings';
import { environment } from 'src/environments/environment.development';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class CityService {
  private endpoint: string = environment.HOST + '/city';
  constructor(private http: HttpClient) {}

  getCities(page: number = 1):Observable<PageResponse<City>> {
    return this.http.get<PageResponse<City>>(this.endpoint + '?page=' + page);
  }
}
