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
  type: string;
  cardId: string;
}
export interface UserDataRegistration {
  password: string;
  tourist: Tourist;
}

export type UserRegistrationRaw = Tourist & User;
