import { NgModule } from '@angular/core';
import { NavigationModule } from '../';
import { CityRoutingModule } from './city-routing.module';
import { CityComponent } from './city.component';

@NgModule({
  declarations: [CityComponent],
  imports: [CityRoutingModule, NavigationModule],
})
export class CityModule {}