import { Route, Routes } from "react-router-dom";
import Cities from "./pages/Cities";
import CityHistory from "./pages/CityHistory";
import { Home } from "./pages/Home";
import LogIn from "./pages/LogIn";
import TouristHistory from "./pages/TouristHistory";
import { Tourists } from "./pages/Tourists";
import { AppContainer } from "./partials";

import "./styles.css";

function App() {
  return (
    <>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route element={<AppContainer />}>
         <Route path="/login" element={<LogIn />}/> 
         <Route path="/travelers" element={<Tourists />} />
         <Route path="/travelers/:id" element={<TouristHistory />} />
         <Route path="/cities" element={<Cities />} />
         <Route path="/cities/:id" element={<CityHistory />} />
        </Route>
      </Routes>
    </>
  );
}

export default App;
