import { Component } from '@angular/core';
import { DelayedLinkComponent } from '../buttons/delayed-link/delayed-link.component';

@Component({
  standalone: true,
  selector: 'home-Banner',
  templateUrl: './banner.component.html',
  styleUrls: ['./banner.component.css'],
  imports: [DelayedLinkComponent],
})
export class BannerComponent {
  class: String = "";
  onClick() {
    this.class = "out"
    console.log(this.class);
  }
}
