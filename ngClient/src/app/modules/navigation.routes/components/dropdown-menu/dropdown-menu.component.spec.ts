import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DropdownMenuComponent } from './dropdown-menu.component';

describe('DropdownMunuComponent', () => {
  let component: DropdownMenuComponent<string>;
  let fixture: ComponentFixture<DropdownMenuComponent<string>>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [DropdownMenuComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(DropdownMenuComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should be visible when passed props are true', () => {
    component.props = {
      ...component.props,
      open: true,
    };
    expect(fixture.debugElement.children.length).toBe(0);

    fixture.detectChanges();
    expect(fixture.debugElement.children.length).toBeGreaterThan(0);
  });

  it('should have two buttons', () => {
    component.props = {
      ...component.props,
      open: true,
      buttons: [
        {
          text: 'frist button',
        },
        {
          text: 'second button',
          link: true,
          href: '#',
        },
      ],
    };
    fixture.detectChanges();

    const elm = fixture.nativeElement as HTMLElement;
    const frist = elm.querySelector('button');
    const second = elm.querySelector('a');

    expect(frist?.innerText).toBe(component.props.buttons[0].text);

    expect(second?.innerText).toBe(component.props.buttons[1].text);
    expect(second?.href).toBe(
      window.location.origin + '/' + component.props.buttons[1].href
    );
  });

  it('should change the container position', () => {
    component.props = {
      ...component.props,
      open: true,
      x: 110,
      y: 420,
    };
    fixture.detectChanges();

    const elm = (fixture.nativeElement as HTMLElement).querySelector('ul');

    expect(elm?.style.top).toContain(component.props.y);
    expect(elm?.style.left).toContain(component.props.x);
  });
});
