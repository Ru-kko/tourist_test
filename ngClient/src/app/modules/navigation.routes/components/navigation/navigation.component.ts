import { Component, OnInit } from '@angular/core';
import {
  faMountainCity,
  faRightFromBracket,
  faUsers,
  IconDefinition,
} from '@fortawesome/free-solid-svg-icons';
import { AuthService } from 'src/app/services/auth/auth.service';
import { AuthTokenResponse } from 'src/app/typings';

@Component({
  selector: 'app-navigation',
  templateUrl: './navigation.component.html',
  styleUrls: ['./navigation.component.css'],
})
export class NavigationComponent implements OnInit {
  userIcon: IconDefinition = faUsers;
  cityIcon: IconDefinition = faMountainCity;
  logged: IconDefinition = faRightFromBracket;
  unLogged: IconDefinition = faUsers;
  authState: AuthTokenResponse | boolean = false;
  navState: boolean = false;
  toggle = (_: MouseEvent, state: boolean) => {
    this.navState = state;
  };

  constructor(private auth: AuthService) {}

  ngOnInit(): void {
    this.auth.store$.subscribe((data) => {
      this.authState = data;
    });
  }

  logOut() {
    if (this.auth) {
      this.auth.logOut();
    }
  }
}
