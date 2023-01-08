import { configureStore } from "@reduxjs/toolkit";
import { SessionReducer } from "./reducers/sessionStore";

export const store = configureStore({
  reducer: {
    session: SessionReducer
  }
});

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;