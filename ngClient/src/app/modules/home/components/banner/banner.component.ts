import { Component } from '@angular/core';

@Component({
  selector: 'app-home-banner',
  templateUrl: './banner.component.html',
  styleUrls: ['./banner.component.css'],
})
export class BannerComponent {
  class = "";
  onClick() {
    this.class = "out"
  }
}
