import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { NavigationModule } from '../';
import { CityRoutingModule } from './city-routing.module';
import { CityComponent } from './city.component';
import { CommonModule } from '@angular/common';
import { CityService } from './services/CityApi.service';

@NgModule({
  declarations: [CityComponent],
  providers: [CityService],
  imports: [
    CityRoutingModule,
    NavigationModule,
    HttpClientModule,
    CommonModule,
  ],
})
export class CityModule {}
