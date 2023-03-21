import { Component, Input } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-delayed-link',
  templateUrl: './delayed-link.component.html',
  styleUrls: ['./delayed-link.component.css'],
})
export class DelayedLinkComponent {
  private clicked = false;

  @Input() delay = 500;
  @Input() href = '#';
  @Input() onClick?: (e: MouseEvent) => void;

  constructor(private router: Router) {}

  clickDelay(e: MouseEvent) {
    e.preventDefault();
    if (this.clicked) return;
    this.onClick?.(e);
    setTimeout(() => {
      this.router.navigate([this.href]);
    }, this.delay);
  }
}
