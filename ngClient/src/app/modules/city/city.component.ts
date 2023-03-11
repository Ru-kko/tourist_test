import { Component, OnInit } from '@angular/core';
import { DropDownMenuArgs, Headers } from '../navigation.routes/components';
import {
  AuthTokenResponse,
  City,
  EmptyResponse,
  PageResponse,
} from 'src/app/typings';
import { ActivatedRoute } from '@angular/router';
import { CityService } from './services/CityApi.service';
import { AuthService } from 'src/app/services/auth/auth.service';

@Component({
  selector: 'app-city',
  templateUrl: './city.component.html',
  styleUrls: ['./city.component.css'],
})
export class CityComponent implements OnInit {
  data: PageResponse<City> = EmptyResponse();
  loaded: boolean = false;
  auth: boolean | AuthTokenResponse = false;
  page: number = 1;
  headers: Headers<City> = {
    id: {
      name: 'id',
      render: false,
    },
    name: {
      name: 'Name',
      preprocess: (s) => (<string>s).toUpperCase(),
    },
    population: {
      name: 'Population',
    },
    mostTuristicPlace: {
      name: 'Most Turistic Place',
    },
    mostReserverdHotel: {
      name: 'Most Reserverd Hotel',
    },
  };
  dropdownArguments: DropDownMenuArgs<Button> = {
    open: false,
    x: 0,
    y: 0,
    buttons: [
      {
        text: Button.History,
        link: true,
        href: '/',
      },
    ],
  };

  constructor(
    private cityService: CityService,
    private authService: AuthService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.route.queryParamMap.subscribe((params) => {
      this.page = parseInt(params.get('page') || '1');
    });
    this.cityService.getCities(this.page).subscribe({
      next: (data) => {
        this.loaded = true;
        this.data = data;
      },
      error: (e: Error) => {
        alert(e.message);
      },
    });
    this.authService.store$.subscribe((auth) => (this.auth = auth));
  }

  rowClick({ row, event }: { row: City; event: MouseEvent }) {
    const args: DropDownMenuArgs<Button> = {
      open: true,
      saveData: row,
      x: event.clientX,
      y: event.clientY,
      buttons: [
        {
          text: Button.History,
          link: true,
          href: '/',
        },
      ],
    };

    if (this.auth) args.buttons.push({ text: Button.Reservate });
    if (typeof this.auth !== 'boolean' && this.auth.admin)
      args.buttons.push(
        { text: Button.Edit },
        { text: Button.Delete },
        { text: Button.New }
      );
    this.dropdownArguments = args;
  }

  buttonClick(foo: any) {
    // ToDo use it to handle droptown options
  }
}
enum Button {
  History = 'History',
  Reservate = 'Reservate',
  Edit = 'Edit',
  Delete = 'Delete',
  New = 'New City',
}
