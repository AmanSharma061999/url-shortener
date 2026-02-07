import "./App.css";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import LandingPage from "./component/LandingPage";
import AboutPage from "./component/AboutPage";
import Navbar from "./component/Navbar";
import Footer from "./component/Footer";
import RegisterPage from "./component/RegisterPage";
import LoginPage from "./component/LoginPage";
import { Toaster } from "react-hot-toast";
import DashboardLayout from "./component/DashBoard/DashboardLayout";
import ProtectedRoute from "./routing/ProtectedRoute";
import Unauthorized from "./pages/Unauthorized";
import NotFound from "./pages/NotFound";

function App() {
  return (
    <div className="space-bg text-white">
      <BrowserRouter>
        <Navbar />
        <Toaster position="bottom-center" />

        <div className="relative z-10">
          <Routes>
            <Route path="/" element={<LandingPage />} />
            <Route path="/about" element={<AboutPage />} />
            <Route path="/register" element={<RegisterPage />} />
            <Route path="/login" element={<LoginPage />} />
            <Route
              path="/dashboard"
              element={
                <ProtectedRoute>
                  <DashboardLayout />
                </ProtectedRoute>
              }
            />
            <Route path="/unauthorized" element={<Unauthorized />} />

            {/* Catch-all route (must be LAST) */}
            <Route path="*" element={<NotFound />} />
          </Routes>
          <Footer />
        </div>
      </BrowserRouter>
    </div>
  );
}

export default App;
