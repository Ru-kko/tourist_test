import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { EmptyResponse, PageResponse, Tourist } from 'src/app/typings';
import { Headers } from '../navigation.routes/components';
import { TouristApiService } from './services/tourist-api.service';

@Component({
  selector: 'app-tourist-page',
  templateUrl: './tourist-page.component.html',
  styleUrls: ['./tourist-page.component.css'],
})
export class TouristPageComponent {
  data: PageResponse<Tourist> = EmptyResponse();
  loaded: boolean = false;
  page: number = 1;
  headers: Headers<Tourist> = {
    id: { name: 'id', render: false },
    idCard: {
      name: 'Card ID',
    },
    name: {
      name: 'Frist Name',
    },
    lastName: {
      name: 'Last Name',
    },
    bornDate: {
      name: 'Born Date',
      preprocess: (d) => new Date(d as string).toLocaleDateString(),
    },
    travelBudget: {
      name: 'Trvel Budget',
    },
    travelFrequency: {
      name: 'Travel Frequency',
    },
  };

  constructor(private service: TouristApiService, private route: ActivatedRoute) {}
 
  ngOnInit(): void {
    this.route.queryParamMap.subscribe((params) => {
      this.page = parseInt(params.get('page') || '1');
    });
    this.service.getTourists(this.page).subscribe({
      next: (data) => {
        this.loaded = true;
        this.data = data;
      },
      error: (e:Error) => {
        alert(e.message)
      }
    });
  }
}
