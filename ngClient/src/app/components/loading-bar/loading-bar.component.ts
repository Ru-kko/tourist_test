import { Component, Input } from '@angular/core';

@Component({
  standalone: true,
  selector: 'app-loading-bar',
  templateUrl: './loading-bar.component.html',
  styleUrls: ['./loading-bar.component.css']
})
export class LoadingBarComponent {
  @Input() width: String = "200px"
}
