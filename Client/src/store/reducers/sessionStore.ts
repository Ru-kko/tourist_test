import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import { RootState } from "..";

import { TokenResponse } from "../../typings/server";

function getInitialState(): TokenResponse | {} {
  if (document.cookie.length > 5) {
    return JSON.parse(document.cookie);
  }
  return {};
}

export const SessionSlice = createSlice({
  name: "session",
  initialState: getInitialState(),
  reducers: {
    logOut: (state) => {
      document.cookie = "";
      state = {};
    },
    logIn: (state, action: PayloadAction<TokenResponse>) => {
      document.cookie = JSON.stringify(action);
      state = action;
    },
  },
});

export const SessionActions = SessionSlice.actions;
export const SessionReducer = SessionSlice.reducer
export const selectSession = (state: RootState) => state.session