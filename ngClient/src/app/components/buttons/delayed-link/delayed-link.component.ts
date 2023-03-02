import { Component, Input } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  standalone: true,
  selector: 'delayed-link',
  templateUrl: './delayed-link.component.html',
  styleUrls: ['./delayed-link.component.css'],
})
export class DelayedLinkComponent {
  private clicked: Boolean = false;

  @Input() delay: number = 500;
  @Input() href: String = '#';
  @Input() onClick?: (e: MouseEvent) => void;

  constructor(private router: Router) {}

  clickDelay(e: MouseEvent) {
    e.preventDefault();
    if (this.clicked) return;
    setTimeout(() => {
      this.router.navigate([this.href]);
      this.onClick?.(e);
    }, this.delay);
  }
}
