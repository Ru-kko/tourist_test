import { City } from "./City";
import { Tourist } from "./Tourist";

export interface Trip {
  id?: number;
  startDate: string;
  tourist: Tourist;
  city: City;
}