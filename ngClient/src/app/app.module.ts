import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BannerComponent } from './components/banner/banner.component';
import { DelayedLinkComponent } from './components/buttons/delayed-link/delayed-link.component';
import { CloseBtnComponent } from './components/buttons/close-btn/close-btn.component';
import { LoadingBarComponent } from './components/loading-bar/loading-bar.component';

@NgModule({
  declarations: [
    AppComponent,
  ],
  imports: [
    LoadingBarComponent,
    CloseBtnComponent,
    BrowserModule,
    AppRoutingModule,
    DelayedLinkComponent,
    BannerComponent
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
