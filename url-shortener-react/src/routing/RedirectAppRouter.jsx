// src/routers/RedirectAppRouter.jsx
import { BrowserRouter, Routes, Route } from "react-router-dom";
import ShortenUrlPage from "../pages/ShortenUrlPage";

const RedirectAppRouter = () => {
  return (
    <BrowserRouter>
      <Routes>
        {/* Public short URL redirect */}
        <Route path="/:url" element={<ShortenUrlPage />} />
      </Routes>
    </BrowserRouter>
  );
};

export default RedirectAppRouter;
