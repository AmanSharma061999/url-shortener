import React from "react";
import Modal from "@mui/material/Modal";
import CreateNewShorten from "./CreateNewShorten"; // adjust path

const ShortenPopUp = ({ open, setOpen, refetch }) => {
  const handleClose = () => setOpen(false);

  return (
    <Modal
      open={open}
      onClose={handleClose}
      aria-labelledby="shorten-modal-title"
      aria-describedby="shorten-modal-desc"
      slotProps={{
        backdrop: {
          sx: {
            backgroundColor: "rgba(0,0,0,0.35)",
            backdropFilter: "blur(10px)",
            WebkitBackdropFilter: "blur(10px)",
          },
        },
      }}
    >
      <div className="flex h-full w-full items-center justify-center p-4">
        {/* Glassy card */}
        <div className="relative w-full max-w-xl overflow-hidden rounded-2xl border border-white/20 bg-white/10 shadow-2xl backdrop-blur-xl">
          {/* Sine-wave animation (bottom only) */}
          <div className="pointer-events-none absolute inset-x-0 bottom-0 h-40 opacity-90">
            <svg
              className="absolute inset-0 h-full w-[140%] -left-[20%] waveSvg waveA"
              viewBox="0 0 1200 200"
              preserveAspectRatio="none"
            >
              <path
                d="M0,120 C150,60 300,180 450,120 C600,60 750,180 900,120 C1050,60 1200,180 1350,120 L1350,220 L0,220 Z"
                fill="rgba(134,239,172,0.35)"
              />
            </svg>

            <svg
              className="absolute inset-0 h-full w-[160%] -left-[30%] waveSvg waveB"
              viewBox="0 0 1200 200"
              preserveAspectRatio="none"
            >
              <path
                d="M0,140 C200,90 350,190 550,140 C750,90 900,190 1100,140 C1300,90 1450,190 1650,140 L1650,220 L0,220 Z"
                fill="rgba(34,197,94,0.22)"
              />
            </svg>

            <svg
              className="absolute inset-0 h-full w-[180%] -left-[40%] waveSvg waveC"
              viewBox="0 0 1200 200"
              preserveAspectRatio="none"
            >
              <path
                d="M0,155 C180,125 360,185 540,155 C720,125 900,185 1080,155 C1260,125 1440,185 1620,155 L1620,220 L0,220 Z"
                fill="rgba(187,247,208,0.18)"
              />
            </svg>

            <div className="absolute inset-x-0 bottom-0 h-40 bg-gradient-to-t from-green-300/20 via-green-200/5 to-transparent" />
          </div>

          {/* Content */}
          <div className="relative p-6">
            <CreateNewShorten setOpen={setOpen} refetch={refetch} />
          </div>

          {/* Animations */}
          <style>{`
            .waveSvg { transform: translate3d(0,0,0); }
            .waveA { animation: driftA 7s ease-in-out infinite; }
            .waveB { animation: driftB 9s ease-in-out infinite; }
            .waveC { animation: driftC 11s ease-in-out infinite; }

            @keyframes driftA { 0%,100% { transform: translateX(-2%); } 50% { transform: translateX(2%); } }
            @keyframes driftB { 0%,100% { transform: translateX(3%); } 50% { transform: translateX(-3%); } }
            @keyframes driftC { 0%,100% { transform: translateX(-1.5%); } 50% { transform: translateX(1.5%); } }
          `}</style>
        </div>
      </div>
    </Modal>
  );
};

export default ShortenPopUp;
