import { useNavigate } from "react-router-dom";

const NotFound = () => {
  const navigate = useNavigate();

  return (
    <div className="min-h-[calc(100vh-64px)] flex flex-col items-center justify-center text-white px-4">
      <h1 className="text-4xl font-bold mb-3">404 – Page Not Found</h1>
      <p className="text-white/70 mb-6 text-center">
        The page you’re looking for doesn’t exist.
      </p>

      <button
        onClick={() => navigate("/")}
        className="bg-custom-gradient px-6 py-2 rounded-md font-semibold"
      >
        Go Home
      </button>
    </div>
  );
};

export default NotFound;
