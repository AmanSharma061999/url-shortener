import React, { useState } from "react";
import { useForm } from "react-hook-form";
import TextField from "./TextField";
import { Link, useNavigate } from "react-router-dom";
import toast from "react-hot-toast";
import { useAuth } from "../contextApi/ContextApi";

const LoginPage = () => {
  const navigate = useNavigate();
  const [loader, setLoader] = useState(false);

  const { login } = useAuth();

  const {
    register,
    handleSubmit,
    reset,
    formState: { errors },
  } = useForm({
    defaultValues: {
      username: "",
      password: "",
    },
    mode: "onTouched",
  });

  const LoginHandler = async (payload) => {
    setLoader(true);
    try {
      await login(payload);
      toast.success("Login Successful");
      reset();
      navigate("/dashboard");
    } catch (error) {
      console.log(error);
      toast.error("Login ");
    } finally {
      setLoader(false);
    }
  };
  return (
    <div className="min-h-[calc(100vh-64px)] flex justify-center items-center px-4 py-10">
      <div className="relative w-full max-w-md group">
        {/* softer ambient glow (reduced brightness) */}
        <div className="pointer-events-none absolute -inset-10 rounded-[32px] bg-gradient-to-r from-emerald-400/12 via-sky-400/10 to-violet-400/12 blur-3xl opacity-60" />

        {/* subtle hover ring (not too bright) */}
        <div className="pointer-events-none absolute -inset-[1px] rounded-3xl bg-gradient-to-r from-sky-400 via-violet-400 to-emerald-400 opacity-0 blur-md transition duration-300 group-hover:opacity-25" />

        <form
          onSubmit={handleSubmit(LoginHandler)}
          className="relative w-full rounded-3xl border border-white/[0.08] bg-white/[0.03] backdrop-blur-xl shadow-2xl py-8 px-6 sm:px-8"
        >
          <h1 className="text-center font-bold tracking-tight text-white text-2xl sm:text-3xl">
            Login Here
          </h1>

          <p className="mt-1 text-center text-sm text-white/65">
            Log In your account to start shortening links.
          </p>

          <div className="my-6 h-px w-full bg-gradient-to-r from-transparent via-white/15 to-transparent" />

          <div className="flex flex-col gap-4">
            <TextField
              label="UserName"
              required
              id="username"
              type="text"
              message="Username is required"
              placeholder="Type your username"
              register={register}
              errors={errors}
              className="text-white/90"
            />

            <TextField
              label="Password"
              required
              id="password"
              type="password"
              message="Password is required"
              placeholder="Type your password"
              register={register}
              min={6}
              errors={errors}
              className="text-white/90"
            />
          </div>

          <button
            disabled={loader}
            type="submit"
            className="mt-6 w-full rounded-md bg-custom-gradient py-2.5 font-semibold text-white shadow-sm transition hover:-translate-y-0.5 hover:shadow-md active:translate-y-0 disabled:opacity-60 disabled:cursor-not-allowed"
          >
            {loader ? "Logging in.." : "Login"}
          </button>
          <p className="text-center text-sm text-white/70 mt-6">
            New to Bitly?
            <Link
              className="font-semibold text-emerald-300 hover:text-emerald-200 transition"
              to="/register"
            >
              <span> Sign Up!</span>
            </Link>
          </p>
        </form>
      </div>
    </div>
  );
};

export default LoginPage;

/*
register is a function provided by react-hook-form.
It is used to register input fields into the form so that their values can be tracked and validated.
*/

/*

*/
