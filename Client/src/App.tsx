import { Route, Routes } from "react-router-dom";
import { Home } from "./pages/Home";
import { AppContainer } from "./partials";

import "./styles.css";

function App() {
  return (
    <>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="*" element={<AppContainer />}>
          
        </Route>
      </Routes>
    </>
  );
}

export default App;
