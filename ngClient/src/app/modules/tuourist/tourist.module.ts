import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TouristPageComponent } from './tourist-page.component';
import { TouristRouterModule } from './tourist-router.module';
import { HttpClientModule } from '@angular/common/http';
import { NavigationModule } from '../navigation.routes';



@NgModule({
  declarations: [
    TouristPageComponent
  ],
  imports: [
    TouristRouterModule,
    NavigationModule,
    CommonModule,
    HttpClientModule
  ]
})
export class TouristModule { }
