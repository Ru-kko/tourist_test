import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BannerComponent } from './components/banner/banner.component';
import { DelayedLinkComponent } from './components/buttons/delayed-link/delayed-link.component';
import { CloseBtnComponent } from './components/buttons/close-btn/close-btn.component';
import { LoadingBarComponent } from './components/loading-bar/loading-bar.component';
import { PaginationComponent } from './components/pagination/pagination.component';
import { CommonModule } from '@angular/common';

@NgModule({
  declarations: [
    AppComponent,
  ],
  imports: [
    PaginationComponent,
    LoadingBarComponent,
    CloseBtnComponent,
    BrowserModule,
    CommonModule,
    AppRoutingModule,
    DelayedLinkComponent,
    BannerComponent
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
