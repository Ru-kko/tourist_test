import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import Cookies from "js-cookie";
import { RootState } from "..";

import { TokenResponse } from "../../typings/server";

function getInitialState(): TokenResponse | null {
  const cookie = Cookies.get("session");
  
  if (cookie && cookie?.length > 5) {
    return JSON.parse(cookie);
  }
  return null;
}

export const SessionSlice = createSlice({
  name: "session",
  initialState: getInitialState(),
  reducers: {
    logOut: (_) => {
      Cookies.remove("session");
      
      return null;
    },
    logIn: (_, action: PayloadAction<TokenResponse>) => {
      Cookies.set("session", JSON.stringify(action), {
        expires: 20,
      });
      return action.payload;
    },
  },
});

export const SessionActions = SessionSlice.actions;
export const SessionReducer = SessionSlice.reducer;
export const selectSession = (state: RootState) => state.session;
