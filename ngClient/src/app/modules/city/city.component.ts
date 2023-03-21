import { Component, OnInit } from '@angular/core';
import {
  DropDownMenuArgs,
  Headers,
  InputProps,
} from '../navigation.routes/components';
import {
  AuthTokenResponse,
  City,
  EmptyResponse,
  PageResponse,
} from 'src/app/typings';
import { ActivatedRoute } from '@angular/router';
import { CityService } from './services/CityApi.service';
import { AuthService } from 'src/app/services/auth/auth.service';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-city',
  templateUrl: './city.component.html',
  styleUrls: ['./city.component.css'],
})
export class CityComponent implements OnInit {
  data: PageResponse<City> = EmptyResponse();
  loaded = false;
  auth: boolean | AuthTokenResponse = false;
  page = 1;

  currentForm?: FloatingForm;
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
  cancel: InputProps = {
    label: 'Cancel',
    type: 'button',
    name: 'cancel',
    onClick: () => {
      this.currentForm = undefined;
    },
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

  generateCreationForm(): (InputProps | InputProps[])[] {
    return [
      { name: 'name', type: 'text', label: 'Name', required: true },
      {
        name: 'population',
        type: 'number',
        label: 'Population',
        required: true,
      },
      [
        {
          name: 'mostReserverdHotel',
          type: 'text',
          label: 'Best Hotel',
          required: true,
        },
        {
          name: 'mostTuristicPlace',
          type: 'text',
          label: 'BestPlace',
          required: true,
        },
      ],
      [{ name: 'submit', type: 'submit', label: 'Create' }, this.cancel],
    ];
  }

  generateEditForm(data: City): (InputProps | InputProps[])[] {
    return [
      {
        name: 'id',
        type: 'number',
        required: true,
        label: 'ID',
        readOnly: true,
        default: String(data.id),
      },
      {
        name: 'name',
        type: 'text',
        label: 'Name',
        required: true,
        default: data.name,
      },
      {
        name: 'population',
        type: 'number',
        label: 'Population',
        default: String(data.population),
        required: true,
      },
      [
        {
          name: 'mostReserverdHotel',
          type: 'text',
          label: 'Best Hotel',
          default: String(data.mostReserverdHotel),
          required: true,
        },
        {
          name: 'mostTuristicPlace',
          type: 'text',
          label: 'BestPlace',
          default: String(data.mostTuristicPlace),
          required: true,
        },
      ],
      [{ name: 'submit', type: 'submit', label: 'Edit' }, this.cancel],
    ];
  }

  generateReservationForm(id: number): (InputProps | InputProps[])[] {
    return [
      [
        {
          name: 'id',
          type: 'number',
          required: true,
          label: 'ID',
          readOnly: true,
          default: String(id),
        },
        { name: 'startDate', type: 'date', required: true, label: 'Date' },
        { name: 'submit', type: 'submit', label: 'Reservate' },
        this.cancel,
      ],
    ];
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
          href: '/city/' + row.id,
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

  onSubmit(data: FormData) {
    const endRequest = () => {
      this.currentForm = undefined;
    };
    if (this.currentForm?.type === Button.Reservate) {
      this.cityService
        .reservateCity(
          Number(data.get('id')),
          data.get('startDate')?.valueOf() as string
        )
        .subscribe({
          next: endRequest,
          error: this.handleError,
        });
      return;
    }
    const city: Partial<City> = {};

    city.name = data.get('name')?.valueOf() as string;
    city.population = data.get('population')?.valueOf() as number;
    city.mostTuristicPlace = data.get('mostTuristicPlace')?.valueOf() as string;
    city.mostReserverdHotel = data
      .get('mostReserverdHotel')
      ?.valueOf() as string;

    if (this.currentForm?.type === Button.New) {
      this.cityService.createCity(city as City).subscribe({
        next: endRequest,
        error: this.handleError,
      });
      return;
    }
    city.id = Number(data.get('id'));

    this.cityService.editCity(city as City).subscribe({
      next: endRequest,
      error: this.handleError,
    });
  }

  handleError(error: HttpErrorResponse) {
    switch (error.status) {
      case 400:
        return alert('verify your data');
      case 403:
        this.currentForm = undefined;
        return alert("you shouldn't do this");
      default:
        alert("sorry it shouldn't be happening");
    }
  }

  buttonClick({ button, data }: { button: Button; data: unknown }) {
    const city = data as City;
    switch (button) {
      case Button.New:
        this.currentForm = {
          props: this.generateCreationForm(),
          title: 'Add New City',
          type: Button.New,
        };
        break;
      case Button.Edit:
        this.currentForm = {
          props: this.generateEditForm(city),
          title: 'Editing ' + city.name,
          type: Button.Edit,
        };
        break;
      case Button.Reservate:
        this.currentForm = {
          props: this.generateReservationForm(city.id),
          title: 'Reservate ' + city.name,
          type: Button.Reservate,
        };
        break;
      case Button.Delete:
        this.cityService
          .deleteCity(city.id)
          .subscribe({ error: this.handleError });
    }
  }
}

interface FloatingForm {
  props: (InputProps | InputProps[])[];
  title: string;
  type: Button;
}

enum Button {
  History = 'History',
  Reservate = 'Reservate',
  Edit = 'Edit',
  Delete = 'Delete',
  New = 'New City',
}
