import "./styles/home.css";
import { city, travelers } from "../assets";
import { Link } from "react-router-dom";

export function Home() {
  return (
    <div className="background">
      <Link to="/cities" className="city-parent">
        <img className="back-city" src={city} />
        <div>
          <h1>CITIES</h1>
          <h1>CITIES</h1>
        </div>
      </Link>
      <Link to="travelers">
        <img className="back-travelers" src={travelers} />
        <div>
          <h1>TOURISTS</h1>
          <h1>TOURISTS</h1>
        </div>
      </Link>
    </div>
  );
}
