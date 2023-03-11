import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { NavigationModule } from '../navigation.routes';
import { TripsApiService } from './services/trips-api.service';
import { TripsPageComponent } from './trips-page.component';
import { TripsRouterModule } from './trips-router.module';

@NgModule({
  declarations: [TripsPageComponent],
  providers: [TripsApiService],
  imports: [
    CommonModule,
    TripsRouterModule,
    NavigationModule,
    HttpClientModule,
  ],
})
export class TripsModule {}
