import React, { useState } from "react";
import { Link, useLocation, useNavigate } from "react-router-dom";
import { IoIosMenu } from "react-icons/io";
import { RxCross2 } from "react-icons/rx";
import { useAuth } from "../contextApi/ContextApi";

const Navbar = () => {
  const navigate = useNavigate();
  const path = useLocation().pathname;

  const [navbarOpen, setNavbarOpen] = useState(false);
  const { user, authLoading, logout } = useAuth();
  const isLoggedIn = !!user;

  const onLogOutHandler = async () => {
    try {
      await logout();
      setNavbarOpen(false);
      navigate("/");
    } catch (e) {
      console.log(e);
    }
  };

  const isActive = (p) => path === p;

  const linkBase =
    "group relative px-3 py-2 rounded-lg text-sm font-semibold transition-all duration-200";
  const linkActive = "text-white bg-white/15 shadow-sm";
  const linkIdle =
    "text-white/80 hover:text-white hover:bg-custom-gradient hover:shadow-md";

  return (
    <div className="sticky top-0 z-50">
      {/* Glass top bar */}
      <div className="h-16 border-b border-white/10 bg-white/5 backdrop-blur-md">
        <div className="lg:px-14 sm:px-8 px-4 h-full flex items-center justify-between">
          {/* Logo */}
          <Link to="/" onClick={() => setNavbarOpen(false)} className="group">
            <h1 className="font-bold text-3xl text-white italic tracking-tight select-none">
              Linklytics
            </h1>
            <div className="h-[2px] w-0 bg-gradient-to-r from-sky-400 via-violet-400 to-emerald-400 rounded-full transition-all duration-300 group-hover:w-full" />
          </Link>

          {/* Desktop Nav */}
          <div className="hidden sm:flex items-center gap-2">
            <Link
              to="/"
              className={`${linkBase} ${isActive("/") ? linkActive : linkIdle}`}
            >
              Home
              <span
                className={`absolute left-3 right-3 -bottom-0.5 h-[2px] rounded-full bg-white/80 transition-transform duration-300 ${
                  isActive("/")
                    ? "scale-x-100"
                    : "scale-x-0 group-hover:scale-x-100"
                }`}
              />
            </Link>

            <Link
              to="/about"
              className={`${linkBase} ${
                isActive("/about") ? linkActive : linkIdle
              }`}
            >
              About
              <span
                className={`absolute left-3 right-3 -bottom-0.5 h-[2px] rounded-full bg-white/80 transition-transform duration-300 ${
                  isActive("/about")
                    ? "scale-x-100"
                    : "scale-x-0 group-hover:scale-x-100"
                }`}
              />
            </Link>

            {/* dashboard only if logged in (and after auth check completes) */}
            {!authLoading && isLoggedIn && (
              <Link
                to="/dashboard"
                className={`${linkBase} ${
                  isActive("/dashboard") ? linkActive : linkIdle
                }`}
              >
                Dashboard
                <span
                  className={`absolute left-3 right-3 -bottom-0.5 h-[2px] rounded-full bg-white/80 transition-transform duration-300 ${
                    isActive("/dashboard")
                      ? "scale-x-100"
                      : "scale-x-0 group-hover:scale-x-100"
                  }`}
                />
              </Link>
            )}

            {!authLoading && !isLoggedIn ? (
              <>
                <Link
                  to="/login"
                  className="ml-3 rounded-md border border-white/20 px-4 py-2 text-sm font-semibold text-white transition hover:bg-white/10"
                >
                  Login
                </Link>

                <Link
                  to="/register"
                  className="ml-2 rounded-md bg-custom-gradient px-4 py-2 text-sm font-semibold text-white shadow-sm transition hover:-translate-y-0.5 hover:shadow-md active:translate-y-0 focus:ring-2 focus:ring-white/40"
                >
                  SignUp
                </Link>
              </>
            ) : !authLoading && isLoggedIn ? (
              <button
                onClick={onLogOutHandler}
                className="ml-3 rounded-md bg-rose-600 px-4 py-2 text-sm font-semibold text-white shadow-sm transition hover:bg-rose-700 hover:-translate-y-0.5 hover:shadow-md active:translate-y-0 focus:ring-2 focus:ring-white/30"
              >
                LogOut
              </button>
            ) : (
              <div className="ml-3 h-9 w-24 rounded-md bg-white/10 animate-pulse" />
            )}
          </div>

          {/* Mobile toggle */}
          <button
            onClick={() => setNavbarOpen(!navbarOpen)}
            className="sm:hidden rounded-md p-1 transition hover:bg-white/10 active:scale-95"
            aria-label="Toggle menu"
          >
            {navbarOpen ? (
              <RxCross2 className="text-white text-3xl" />
            ) : (
              <IoIosMenu className="text-white text-3xl" />
            )}
          </button>
        </div>
      </div>

      {/* Mobile menu */}
      <div
        className={`sm:hidden overflow-hidden transition-all duration-300 ${
          navbarOpen ? "max-h-96" : "max-h-0"
        }`}
      >
        <div className="border-b border-white/10 bg-white/5 backdrop-blur-md shadow-lg px-4 py-4 flex flex-col gap-2">
          <Link
            to="/"
            onClick={() => setNavbarOpen(false)}
            className={`rounded-lg px-3 py-2 text-sm font-semibold transition ${
              isActive("/")
                ? "bg-white/15 text-white"
                : "text-white/85 hover:bg-custom-gradient hover:text-white"
            }`}
          >
            Home
          </Link>

          <Link
            to="/about"
            onClick={() => setNavbarOpen(false)}
            className={`rounded-lg px-3 py-2 text-sm font-semibold transition ${
              isActive("/about")
                ? "bg-white/15 text-white"
                : "text-white/85 hover:bg-custom-gradient hover:text-white"
            }`}
          >
            About
          </Link>

          {!authLoading && isLoggedIn && (
            <Link
              to="/dashboard"
              onClick={() => setNavbarOpen(false)}
              className={`rounded-lg px-3 py-2 text-sm font-semibold transition ${
                isActive("/dashboard")
                  ? "bg-white/15 text-white"
                  : "text-white/85 hover:bg-custom-gradient hover:text-white"
              }`}
            >
              Dashboard
            </Link>
          )}

          {!authLoading && !isLoggedIn ? (
            <>
              <Link
                to="/login"
                onClick={() => setNavbarOpen(false)}
                className="mt-2 rounded-md border border-white/20 text-white py-2 text-center font-semibold shadow-sm transition hover:bg-white/10"
              >
                Login
              </Link>

              <Link
                to="/register"
                onClick={() => setNavbarOpen(false)}
                className="rounded-md bg-custom-gradient text-white py-2 text-center font-semibold shadow-sm transition hover:-translate-y-0.5 hover:shadow-md"
              >
                SignUp
              </Link>
            </>
          ) : !authLoading && isLoggedIn ? (
            <button
              onClick={onLogOutHandler}
              className="mt-2 rounded-md bg-rose-600 text-white py-2 font-semibold shadow-sm transition hover:bg-rose-700 hover:-translate-y-0.5 hover:shadow-md"
            >
              LogOut
            </button>
          ) : (
            <div className="mt-2 h-10 w-full rounded-md bg-white/10 animate-pulse" />
          )}
        </div>
      </div>
    </div>
  );
};

export default Navbar;
