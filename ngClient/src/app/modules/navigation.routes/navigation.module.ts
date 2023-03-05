import { NgModule } from '@angular/core';
import { NavigationModuleComponent } from './navigation-module.component';
import { NavigationRoutingModule } from './navigation-routing.module';
import { CloseBtnComponent, FormComponent, ListComponent, LoadingBarComponent, PaginationComponent } from './components'

@NgModule({
  declarations: [
    CloseBtnComponent,
    FormComponent,
    ListComponent,
    NavigationModuleComponent,
    LoadingBarComponent,
    PaginationComponent
  ],
  imports: [
    NavigationRoutingModule,
  ]
})
export class NavigationModule { }
