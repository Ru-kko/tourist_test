import { Component } from '@angular/core';

@Component({
  selector: 'home-Banner',
  templateUrl: './banner.component.html',
  styleUrls: ['./banner.component.css'],
})
export class BannerComponent {
  class: String = "";
  onClick() {
    this.class = "out"
  }
}
