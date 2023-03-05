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
        loadChildren: ()  => import('../city/city.module').then(({CityModule}) => CityModule),
      },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class NavigationRoutingModule {}
