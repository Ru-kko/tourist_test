import { Outlet } from "react-router-dom";
import { Navigation } from "../Navigation/Navigation";
import "./AppContainer.css";

export function AppContainer() {
  return (
    <div className="app-container">
      <Navigation />
      <main>
        <Outlet />
      </main>
    </div>
  );
}
