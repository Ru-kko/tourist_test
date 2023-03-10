import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { TouristPageComponent } from './tourist-page.component';

const routes: Routes = [
  {
    path: '',
    component: TouristPageComponent,
  },
];

@NgModule({
  declarations: [],
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class TouristRouterModule {}
