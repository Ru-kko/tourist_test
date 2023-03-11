import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { NavigationModuleComponent } from './navigation-module.component';

const routes: Routes = [
  {
    path: '',
    component: NavigationModuleComponent,
    children: [
      {
        path: 'city',
        loadChildren: () =>
          import('../city/city.module').then(({ CityModule }) => CityModule),
      },
      {
        path: 'session',
        loadChildren: () =>
          import('../Auth/auth.module').then(({ AuthModule }) => AuthModule),
      },
      {
        path: 'tourist',
        loadChildren: () =>
          import('../tuourist/tourist.module').then(
            ({ TouristModule }) => TouristModule
          ),
      },
      {
        path: 'city/:id',
        loadChildren: () =>
          import('../trips/trips.module').then(
            ({ TripsModule }) => TripsModule
          ),
      },
      {
        path: 'tourist/:id',
        loadChildren: () =>
          import('../trips/trips.module').then(
            ({ TripsModule }) => TripsModule
          ),
      }
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class NavigationRoutingModule {}
