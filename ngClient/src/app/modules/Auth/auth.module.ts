import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AuthPageComponent } from './auth-page.component';
import { AuthRouterModule } from './auth-router.module';
import { NavigationModule } from '../navigation.routes';
import { HttpClientModule } from '@angular/common/http';

@NgModule({
  declarations: [AuthPageComponent],
  imports: [AuthRouterModule, CommonModule, NavigationModule, HttpClientModule],
})
export class AuthModule {}
