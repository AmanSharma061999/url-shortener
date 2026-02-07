import { Navigate, useLocation } from "react-router-dom";
import { useAuth } from "../contextApi/ContextApi";

const ProtectedRoute = ({ children }) => {
  const { user, authLoading } = useAuth();
  const location = useLocation();

  if (authLoading) return <div className="p-6 text-white">Loading...</div>;

  if (!user) {
    return <Navigate to="/login" replace state={{ from: location.pathname }} />;
  }

  return children;
};

export default ProtectedRoute;
