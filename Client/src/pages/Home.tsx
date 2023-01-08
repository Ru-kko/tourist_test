import "./styles/home.css";
import { city, travelers } from "../assets";
import { useEffect, useState } from "react";
import { DelayLink } from "../partials";

export function Home() {
  const [outAnim, setOut] = useState<string>()

  const handleClick = () => {
    setOut(" background-out")
  }

  useEffect(() => {
    return () => console.log("out");
  }, []);

  return (
    <div className={"background" + (outAnim ?? "")} >
      <DelayLink to="/cities" className="city-parent" delayTime={700} onClick={handleClick}>
        <img className="back-city" src={city} />
        <h1>CITIES</h1>
      </DelayLink>
      <DelayLink to="travelers" delayTime={700} onClick={handleClick} >
        <img className="back-travelers" src={travelers} />
        <h1>TOURISTS</h1>
      </DelayLink>
    </div>
  );
}
