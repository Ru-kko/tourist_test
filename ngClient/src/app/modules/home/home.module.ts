import { NgModule } from '@angular/core';
import { HomeComponent } from './home.component';
import { BannerComponent } from './components/banner/banner.component';
import { DelayedLinkComponent } from 'src/app/modules/home/components/delayed-link/delayed-link.component';
import { HomeAppRoutingModule } from './app-routing.module';

@NgModule({
  declarations: [
    HomeComponent,
    DelayedLinkComponent,
    BannerComponent
  ],
  imports: [
    HomeAppRoutingModule,
  ]
})
export class HomeModule { }
