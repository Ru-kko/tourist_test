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
    logOut: (state) => {
      Cookies.remove("session");
      
      state = null;
      return state;
    },
    logIn: (state, action: PayloadAction<TokenResponse>) => {
      Cookies.set("session", JSON.stringify(action), {
        expires: 20,
      });
      state = action.payload;
      return state;
    },
  },
});

export const SessionActions = SessionSlice.actions;
export const SessionReducer = SessionSlice.reducer;
export const selectSession = (state: RootState) => state.session;
