export interface Tourist {
  id?: number;
  cardId: string;
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

export type UserRegistration = Tourist & User;
