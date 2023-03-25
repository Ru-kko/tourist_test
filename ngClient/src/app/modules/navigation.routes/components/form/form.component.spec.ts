import { ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { CastPipe } from 'src/app/pipes/cast.pipe';

import { FormComponent, InputComponent } from './form.component';

describe('FromComponent', () => {
  let component: FormComponent;
  let fixture: ComponentFixture<FormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [FormComponent, InputComponent],
      imports: [CastPipe],
    }).compileComponents();

    fixture = TestBed.createComponent(FormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('The component should has four inputs', () => {
    component.data = [
      {
        name: 'frist',
        type: 'text',
        label: 'frist',
      },
      {
        name: 'second',
        type: 'text',
        label: 'second',
      },
      {
        name: 'third',
        type: 'text',
        label: 'third',
      },
      {
        name: 'fourth',
        type: 'text',
        label: 'fourth',
      },
    ];
    fixture.detectChanges();

    const inputs = fixture.debugElement.queryAll(By.css('input'));

    expect(inputs.length).toBe(component.data.length);
  });

  it("Should select two nodes that they comply with 'form>div:has(input)' sentence", () => {
    component.data = [
      [
        {
          name: 'frist',
          type: 'text',
          label: 'frist',
        },
        {
          name: 'second',
          type: 'password',
          label: 'second',
        },
      ],
      [
        {
          name: 'third',
          type: 'date',
          label: 'third',
        },
        {
          name: 'fourth',
          type: 'number',
          label: 'fourth',
        },
      ],
    ];

    fixture.detectChanges();

    const elements = fixture.debugElement.queryAll(
      By.css('form>div:has(input)')
    );

    expect(elements.length).toBe(2);
  });

  it('Should pass de submit event avoiding submit and button type inputs', () => {
    component.data = [
      {
        name: 'frist',
        type: 'text',
        default: 'bar',
        label: 'frist',
      },
      {
        name: 'second',
        type: 'text',
        default: 'foo',
        label: 'second',
      },
      {
        name: 'third',
        type: 'submit',
        label: 'third',
      },
      {
        name: 'fourth',
        type: 'button',
        label: 'fourth',
      },
    ];
    const spy = spyOn(component.Submit, 'emit');

    const form = fixture.debugElement.query(By.css('form')).nativeElement;
    
    fixture.detectChanges();
    
    form?.dispatchEvent(new Event('submit'));

    const expectedArgs = new FormData(form);
    const args = spy.calls.mostRecent().args[0];
    
    expect(args).toBeInstanceOf(FormData);
    expect(args?.get('frist')).toBe(expectedArgs.get('frist'));
    expect(args?.get('second')).toBe(expectedArgs.get('second'));
    expect(args?.has('third')).toBeFalse();
    expect(args?.has('fourth')).toBeFalse();
  });
});
