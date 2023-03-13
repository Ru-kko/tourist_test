import { HttpErrorResponse } from '@angular/common/http';
import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AuthService } from 'src/app/services/auth/auth.service';
import {
  AuthTokenResponse,
  EmptyResponse,
  PageResponse,
  Tourist,
} from 'src/app/typings';
import {
  DropDownMenuArgs,
  Headers,
  InputProps,
} from '../navigation.routes/components';
import { TouristApiService } from './services/tourist-api.service';

@Component({
  selector: 'app-tourist-page',
  templateUrl: './tourist-page.component.html',
  styleUrls: ['./tourist-page.component.css'],
})
export class TouristPageComponent {
  data: PageResponse<Tourist> = EmptyResponse();
  loaded: boolean = false;
  auth: boolean | AuthTokenResponse = false;
  page: number = 1;
  currentForm?: FloatingForm;

  dropdownArguments: DropDownMenuArgs<Button> = {
    open: false,
    x: 0,
    y: 0,
    buttons: [],
  };
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

  constructor(
    private usersService: TouristApiService,
    private route: ActivatedRoute,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.route.queryParamMap.subscribe((params) => {
      this.page = parseInt(params.get('page') || '1');
    });
    this.usersService.getTourists(this.page).subscribe({
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

  generateEditForm(tourist: Tourist): (InputProps | InputProps[])[] {
    return [
      [
        {
          name: 'id',
          type: 'number',
          label: 'ID',
          required: true,
          readOnly: true,
          default: String(tourist.id),
        },
        {
          name: 'idCard',
          type: 'number',
          label: 'Card ID',
          required: true,
          default: tourist.idCard,
        },
      ],
      [
        {
          name: 'name',
          type: 'text',
          label: 'Frist Name',
          required: true,
          default: tourist.name,
        },
        {
          name: 'lastName',
          type: 'text',
          label: 'Last Name',
          required: true,
          default: tourist.lastName,
        },
      ],
      {
        name: 'nornDate',
        type: 'date',
        label: 'Born Date',
        required: true,
        default: new Date(tourist.bornDate).toISOString(),
      },
      [
        {
          name: 'travelFrequency',
          type: 'number',
          label: 'Travel Frequency',
          required: true,
          default: String(tourist.travelFrequency),
        },
        {
          name: 'travelBudget',
          type: 'number',
          label: 'Travel Budget',
          required: true,
          default: String(tourist.travelBudget),
        },
      ],
      [
        {
          label: 'Cancel',
          type: 'button',
          name: 'cancel',
          onClick: () => {
            this.currentForm = undefined;
          },
        },
        { name: 'submit', type: 'submit', label: 'Update' },
      ],
    ];
  }

  onRowClick({ row, event }: { row: Tourist; event: MouseEvent }) {
    const args: DropDownMenuArgs<Button> = {
      open: true,
      saveData: row,
      x: event.clientX,
      y: event.clientY,
      buttons: [
        {
          text: Button.History,
          link: true,
          href: '/tourist/' + row.id,
        },
      ],
    };

    console.log({ aut: this.auth, row });

    if (typeof this.auth !== 'boolean' && row.idCard === this.auth.cardId) {
      args.buttons.push({ text: Button.Delete }, { text: Button.Edit });
    }

    this.dropdownArguments = args;
  }

  buttonClick({ button, data }: { button: Button; data: Tourist }) {
    switch (button) {
      case Button.Delete:
        this.usersService.deleteMe().catch(this.handleError);
        break;
      case Button.Edit:
        this.currentForm = {
          props: this.generateEditForm(data),
          title: 'Editing ' + data.name,
          type: Button.Edit,
        };
        break;
    }
  }

  onSubmit(data: FormData) {
    const updated: Tourist = {
      id: data.get('id')?.valueOf() as number,
      name: data.get('name')?.valueOf() as string,
      lastName: data.get('lastName')?.valueOf() as string,
      travelBudget: data.get('travelBudget')?.valueOf() as number,
      travelFrequency: data.get('travelFrequency')?.valueOf() as number,
      idCard: data.get('idCard')?.valueOf() as string,
      bornDate: data.get('bornDate')?.valueOf() as string,
    };

    this.usersService
      .update(updated)
      .then(() => (this.currentForm = undefined))
      .catch(this.handleError);
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
}
interface FloatingForm {
  props: (InputProps | InputProps[])[];
  title: string;
  type: Button;
}

enum Button {
  History = 'History',
  Delete = 'Delete',
  Edit = 'Edit',
}
