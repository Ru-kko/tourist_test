import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import axios from "axios";
import Cookies from "js-cookie";
import { RootState } from "..";

import { TokenResponse } from "../../typings/server";

function getInitialState(): TokenResponse | null {
  const cookie = Cookies.get("session");

  if (cookie && cookie?.length > 5) {
    const token: TokenResponse = JSON.parse(cookie);
    axios.defaults.headers.common["Authorization"] =
      token.tokenType + token.token;
    return token;
  }
  return null;
}

export const SessionSlice = createSlice({
  name: "session",
  initialState: getInitialState(),
  reducers: {
    logOut: (_) => {
      Cookies.remove("session");
      axios.defaults.headers.common["Authorization"] = "";
      return null;
    },
    logIn: (_, action: PayloadAction<TokenResponse>) => {
      Cookies.set("session", JSON.stringify(action.payload), {
        expires: 20,
      });
      axios.defaults.headers.common["Authorization"] =
        action.payload.tokenType + action.payload.token;
      return action.payload;
    },
  },
});

export const SessionActions = SessionSlice.actions;
export const SessionReducer = SessionSlice.reducer;
export const selectSession = (state: RootState) => state.session;
