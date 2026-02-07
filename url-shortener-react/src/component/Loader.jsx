import { ClipLoader } from "react-spinners";

const Loader = ({ message = "Loading your dashboard..." }) => {
  return (
    <div className="min-h-[calc(100vh-64px)] flex items-center justify-center bg-black/60">
      <div className="flex flex-col items-center gap-6">
        <ClipLoader
          color="#34d399" // emerald-400
          size={92}
          speedMultiplier={0.9}
        />

        {/* Text */}
        <p className="text-white/80 text-sm tracking-wide animate-pulse">
          {message}
        </p>
      </div>
    </div>
  );
};

export default Loader;
