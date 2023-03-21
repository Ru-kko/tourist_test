import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import {
  City,
  EmptyResponse,
  PageResponse,
  Tourist,
  Trip,
} from 'src/app/typings';
import { Headers } from '../navigation.routes/components';
import { TripsApiService } from './services/trips-api.service';

@Component({
  selector: 'app-trips-page',
  templateUrl: './trips-page.component.html',
  styleUrls: ['./trips-page.component.css'],
})
export class TripsPageComponent implements OnInit {
  title = '';
  loaded = false;
  page = 1;
  info: PageResponse<Trip> = EmptyResponse();
  headres: Headers<Trip> = {
    id: {
      name: 'ID',
      render: false,
    },
    tourist: {
      name: 'Tourist',
      preprocess: (t) => (t as Tourist).name,
    },
    city: {
      name: 'City',
      preprocess: (c) => (c as City).name,
    },
    startDate: {
      name: 'Date',
      preprocess: (d) => new Date(d as string).toLocaleDateString(),
    },
  };

  constructor(
    public router: ActivatedRoute,
    private tripService: TripsApiService
  ) {}

  ngOnInit(): void {
    this.router.queryParamMap.subscribe((params) => {
      this.page = parseInt(params.get('page') || '1');
    });
    if (location.pathname.startsWith('/city/')) {
      this.tripService
        .getTripsFromCity(this.router.snapshot.paramMap.get('id') as string, this.page)
        .subscribe({
          next: (data) => {
            this.title = 'Trips in ' + (data.content[0]?.city.name ?? 'NaN');
            this.info = data;
            this.loaded = true;
          },
          error: this.handleApiError,
        });
    } else {
      this.tripService
        .getTripsFromTourist(
          this.router.snapshot.paramMap.get('id') as string,
          this.page
        )
        .subscribe({
          next: (data) => {
            this.title = 'Trips of ' + (data.content[0]?.tourist.name ?? 'NaN');
            this.info = data;
            this.loaded = true;
          },
          error: this.handleApiError,
        });
    }
  }

  handleApiError(e: HttpErrorResponse) {
    this.title = 'Error';
    console.error(e.name);
  }
}
