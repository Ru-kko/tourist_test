import { configureStore } from "@reduxjs/toolkit";
import { TypedUseSelectorHook, useDispatch, useSelector } from "react-redux";
import { SessionReducer } from "./reducers/sessionStore";

export const store = configureStore(
  {
    reducer: {
      session: SessionReducer,
    },
    devTools: true
  }
);

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;

export const useStoreDispatch: () => AppDispatch = useDispatch;
export const useAppSelector: TypedUseSelectorHook<RootState> = useSelector;
export * from "./reducers/sessionStore";
