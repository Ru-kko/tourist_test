export interface Tourist {
  id?: number;
  fullName: string;
  idCard: string;
  bornDate: string;
  travelFrequency: number;
  travelBudget: number;
}

export interface User {
  id?: number;
  password: string;
  cardId: string;
}

export interface TokenResponse {
  token: string;
  tokenType: string;
  admin: boolean;
  cardId: string;
}
export interface UserDataRegistration {
  password: string;
  tourist: Tourist;
}

export interface City {
  id: number;
  name: string;
  population: number;
  mostTuristicPlace: string;
  mostReserverdHotel: string;
}

export interface Trip {
  id: number;
  startDate: string;
  tourist: Tourist;
  city: City;
}

export interface PageResponse<T> {
  content: T[],
  lenght: number,
  totalPages: number
}


export type UserRegistrationRaw = Tourist & User;
