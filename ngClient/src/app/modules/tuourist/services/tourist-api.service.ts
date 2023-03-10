import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { PageResponse, Tourist } from 'src/app/typings';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class TouristApiService {
  private endpoint: string = environment.HOST + '/tourist';
  constructor(private http: HttpClient) {}
  
  getTourists(page: number = 1):Observable<PageResponse<Tourist>> {
    return this.http.get<PageResponse<Tourist>>(this.endpoint + '?page=' + page);
  }
}
