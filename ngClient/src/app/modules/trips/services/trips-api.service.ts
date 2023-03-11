import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { PageResponse, Trip } from 'src/app/typings';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class TripsApiService {
  private endpoint: string = environment.HOST;

  constructor(private http: HttpClient) {}

  getTripsFromCity(cityID: string, page?: number) {
    return this.http.get<PageResponse<Trip>>(
      `${this.endpoint}/city/${cityID}?page=${page ?? 1}`
    );
  }

  getTripsFromTourist(touritId: string, page?: number) {
    return this.http.get<PageResponse<Trip>>(
      `${this.endpoint}/tourist/${touritId}?page=${page ?? 1}`
    );
  }
}
