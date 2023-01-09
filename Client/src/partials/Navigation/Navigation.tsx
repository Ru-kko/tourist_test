import { useState } from "react";
import { Link, NavLink } from "react-router-dom";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faUsers,
  faMountainCity,
  faRightToBracket,
  faUser,
} from "@fortawesome/free-solid-svg-icons";
import { SessionActions, useAppSelector, useStoreDispatch } from "../../store";
import "./Navigation.css";

export function Navigation() {
  const [menu, toggleMenu] = useState(false);
  const dispatch = useStoreDispatch();
  const signState = useAppSelector((st) => st.session);

  const handleLogOut = () => {
    dispatch(SessionActions.logOut());
  };

  return (
    <nav className={menu ? "nav-open" : ""}>
      <button onClick={() => toggleMenu(!menu)}>
        <div />
      </button>
      <Link to={"/"}>
        <h1>Technical Test</h1>
      </Link>
      <menu>
        <NavLink
          to="/travelers"
          className={({ isActive }) => (isActive ? "nav-selected" : "")}
        >
          <FontAwesomeIcon icon={faUsers} className="icon" />
          <h2>Tourists</h2>
        </NavLink>
        <NavLink
          to="/cities"
          className={({ isActive }) => (isActive ? "nav-selected" : "")}
        >
          <FontAwesomeIcon icon={faMountainCity} className="icon" />
          <h2>Citites</h2>
        </NavLink>
        {signState ? (
          <button className="session-icon icon" onClick={handleLogOut}>
            <FontAwesomeIcon icon={faRightToBracket} />
          </button>
        ) : (
          <NavLink to="/login" className={"session-icon icon"}>
            <FontAwesomeIcon icon={faUser} />
          </NavLink>
        )}
      </menu>
    </nav>
  );
}
