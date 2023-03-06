import { Component } from '@angular/core';
import { Headers } from '../navigation.routes/components';

@Component({
  selector: 'app-city',
  templateUrl: './city.component.html',
  styleUrls: ['./city.component.css'],
})
export class CityComponent {
  testData: City[] = [
    {
      id: 10,
      name: 'dsfds',
      population: 100000,
      mostTuristicPlace: 'foo',
      mostReserverdHotel: 'bar',
    },
    {
      id: 10,
      name: 'fdsfds',
      population: 100000,
      mostTuristicPlace: 'gfd',
      mostReserverdHotel: 'bar',
    },
    {
      id: 18,
      name: 'sdas',
      population: 100000,
      mostTuristicPlace: 'fgd',
      mostReserverdHotel: 'fgdg',
    },
    {
      id: 11,
      name: 'dfsfs',
      population: 100000,
      mostTuristicPlace: 'ddfg',
      mostReserverdHotel: 'fgd',
    },
    {
      id: 23,
      population: 100000,
      name: 'wew',
      mostTuristicPlace: 'fodsao',
      mostReserverdHotel: 'bdsaar',
    },
    {
      id: 54,
      name: 'fsdfsd',
      population: 100000,
      mostTuristicPlace: 'hgfo',
      mostReserverdHotel: 'asr',
    },
  ];
  headers: Headers<City> = {
    id: {
      name: 'id',
      render: false,
    },
    name: {
      name: 'Name',
      preprocess: (s) => (<string>s).toUpperCase(),
    },
    population: {
      name: 'Population',
    },
    mostTuristicPlace: {
      name: 'Most Turistic Place',
    },
    mostReserverdHotel: {
      name: 'Most Reserverd Hotel',
    },
  };
}

export interface City {
  id: number;
  name: string;
  population: number;
  mostTuristicPlace: string;
  mostReserverdHotel: string;
}
