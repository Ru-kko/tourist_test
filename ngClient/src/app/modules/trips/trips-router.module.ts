import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { TripsPageComponent } from './trips-page.component';

const routes: Routes = [
  {
    path: '', component: TripsPageComponent
  }
]

@NgModule({
  declarations: [],
  imports: [
    RouterModule.forChild(routes)
  ],
  exports: [RouterModule]
})
export class TripsRouterModule {}
