import { ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';

import { ListComponent } from './list.component';

describe('ListComponent', () => {
  let component: ListComponent<TestHeader>;
  let fixture: ComponentFixture<ListComponent<TestHeader>>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ListComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(ListComponent<TestHeader>);
    component = fixture.componentInstance;
    component.headers = {
      foo: {
        name: 'FOO',
      },
      bar: {
        name: 'BAR',
      },
    };
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('Should render only one field on head', () => {
    component.headers = {
      foo: {
        render: false,
        name: 'not-render',
      },
      bar: {
        name: 'Should Render',
      },
    };

    fixture.detectChanges();

    const headers = fixture.debugElement.queryAll(By.css('thead th'));

    expect(headers.length).toBe(1);
    expect((headers[0].nativeElement as HTMLTableCellElement).innerText).toBe(
      component.headers.bar.name
    );
  });

  it('should have two rows', () => {
    component.data = [
      {
        foo: 'frist foo',
        bar: 'frist bar',
      },
      {
        foo: 'second foo',
        bar: 'second bar',
      },
    ];
    fixture.detectChanges();

    const rows = fixture.debugElement.queryAll(By.css('tbody>tr'));

    expect(rows.length).toBe(2);
  });

  it('should make changes before on passed data', () => {
    component.headers = {
      ...component.headers,
      bar: {
        name: 'BAR',
        preprocess: (d) => d.toUpperCase(),
      },
    };

    component.data = [
      {
        foo: 'frist foo',
        bar: 'bar1',
      },
    ];

    fixture.detectChanges();

    const field = (fixture.nativeElement as HTMLElement).querySelector('td[data-label="BAR"]') as HTMLTableColElement;

    expect(field?.innerText).toBe(component.data[0].bar.toUpperCase());
  });
});

interface TestHeader {
  foo: string;
  bar: string;
}
