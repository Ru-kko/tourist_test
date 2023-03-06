import { NgModule } from '@angular/core';
import { NavigationModuleComponent } from './navigation-module.component';
import { NavigationRoutingModule } from './navigation-routing.module';
import { CloseBtnComponent, FormComponent, InputComponent, ListComponent, LoadingBarComponent, PaginationComponent } from './components';
import { NavigationComponent } from './components/navigation/navigation.component'
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { CastPipe } from 'src/app/pipes/cast.pipe';

@NgModule({
  declarations: [
    CloseBtnComponent,
    FormComponent,
    ListComponent,
    NavigationModuleComponent,
    LoadingBarComponent,
    PaginationComponent,
    NavigationComponent,
    InputComponent
  ],
  imports: [
    FontAwesomeModule,
    NavigationRoutingModule,
    CastPipe,
  ]
})
export class NavigationModule { }
