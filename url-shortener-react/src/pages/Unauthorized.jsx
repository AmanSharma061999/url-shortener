import { useNavigate } from "react-router-dom";

const Unauthorized = () => {
  const navigate = useNavigate();

  return (
    <div className="min-h-screen flex items-center justify-center bg-gradient-to-br from-black via-slate-900 to-black text-white px-4">
      <div className="max-w-xl w-full rounded-3xl border border-white/10 bg-white/5 backdrop-blur-xl shadow-2xl p-10 text-center">
        <h1 className="text-6xl font-bold text-red-400 mb-4">403</h1>

        <h2 className="text-2xl font-semibold mb-2">Unauthorized Access</h2>

        <p className="text-white/70 mb-8">
          Your session has expired or you don’t have permission to access this
          page. Please log in again to continue.
        </p>

        <div className="flex justify-center gap-4">
          <button
            onClick={() => navigate("/login")}
            className="bg-custom-gradient px-6 py-2 rounded-md font-semibold shadow-md hover:-translate-y-0.5 transition"
          >
            Go to Login
          </button>

          <button
            onClick={() => navigate("/")}
            className="border border-white/20 px-6 py-2 rounded-md text-white/80 hover:bg-white/10 transition"
          >
            Go Home
          </button>
        </div>
      </div>
    </div>
  );
};

export default Unauthorized;
