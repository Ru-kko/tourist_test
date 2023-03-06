import {  Component } from '@angular/core';
import {
  faMountainCity,
  faRightFromBracket,
  faUsers,
  IconDefinition,
} from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-navigation',
  templateUrl: './navigation.component.html',
  styleUrls: ['./navigation.component.css'],
})
export class NavigationComponent {
  userIcon: IconDefinition = faUsers;
  cityIcon: IconDefinition = faMountainCity;
  logged: IconDefinition = faRightFromBracket;
  unLogged: IconDefinition = faUsers;
  navState: boolean = false;
  
  toggle = (_: MouseEvent, state: boolean)  => {
    this.navState = state;
  }
}
