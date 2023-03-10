import { Tourist } from ".";

export interface AuthTokenResponse {
  token: string;
  tokenType: string;
  admin: boolean;
  cardId: string;
}

export interface UserRegistration {
  password: string;
  tourist: Tourist;
}

export interface User {
  id?: number;
  password: string;
  cardId: string;
}