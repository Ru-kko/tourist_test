import { NgModule } from '@angular/core';
import { NavigationModuleComponent } from './navigation-module.component';
import { NavigationRoutingModule } from './navigation-routing.module';
import { CloseBtnComponent, FormComponent, InputComponent, ListComponent, LoadingBarComponent, PaginationComponent } from './components';
import { NavigationComponent } from './components/navigation/navigation.component'
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { CastPipe } from 'src/app/pipes/cast.pipe';
import { PageContainerComponent } from './components/page-container/page-container.component';
import { CommonModule } from '@angular/common';

@NgModule({
  declarations: [
    CloseBtnComponent,
    FormComponent,
    ListComponent,
    NavigationModuleComponent,
    LoadingBarComponent,
    PaginationComponent,
    NavigationComponent,
    InputComponent,
    PageContainerComponent
  ],
  imports: [
    FontAwesomeModule,
    NavigationRoutingModule,
    CastPipe,
    CommonModule
  ],
  exports: [
    PageContainerComponent,
    CloseBtnComponent,
    FormComponent,
    ListComponent,
    NavigationModuleComponent,
    LoadingBarComponent,
    PaginationComponent
  ]
})
export class NavigationModule { }
