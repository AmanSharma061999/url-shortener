import React, {
  createContext,
  useContext,
  useEffect,
  useMemo,
  useState,
} from "react";
import api from "../api/api";

const AuthContext = createContext(); //A global box where auth data will live

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null);
  const [authLoading, setAuthLoading] = useState(true);

  /*
    fetchMe function answers one question:
     “Am I authenticated right now?”
  */
  const fetchMe = async () => {
    try {
      const res = await api.get("/auth/me");
      setUser(res.data);
    } catch {
      setUser(null);
    } finally {
      setAuthLoading(false);
    }
  };

  useEffect(() => {
    fetchMe();
  }, []);

  const login = async (payload) => {
    await api.post("/auth/public/login", payload);
    await fetchMe();
  };

  const logout = async () => {
    await api.post("/auth/logout");
    setUser(null);
  };

  const value = useMemo(
    () => ({ user, authLoading, login, logout, fetchMe }),
    [user, authLoading],
  );

  return (
    <AuthContext.Provider value={value}> {children} </AuthContext.Provider>
  );
};

export const useAuth = () => useContext(AuthContext);

/* 
  useState → stores auth-related state (user, authLoading)
  useEffect → runs code when component mounts
  createContext → creates a global store
  useContext → allows components to read that store
  useMemo → optimizes object creation (preventsunnecessary re-renders)
*/

/*
  export const AuthProvider = ({ children }) => {
    This component wraps your entire app 
    children = everything inside it (<App />)
*/
