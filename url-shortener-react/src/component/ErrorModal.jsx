import React from "react";

const ErrorModal = ({
  open,
  title = "Something went Wrong",
  message = "Please try again later",
  primaryText = "OK",
  onPrimary,
  secondaryText,
  onSecondary,
}) => {
  if (!open) return null;

  return (
    <div className="fixed inset-0 z-[9999] flex items-center justify-center px-4">
      {/* Backdrop */}
      <div
        className="absolute inset-0 bg-black/50 backdrop-blur-sm"
        onClick={onPrimary}
      />

      {/* Modal */}
      <div className="relative w-full max-w-md rounded-2xl border border-white/15 bg-white/10 backdrop-blur-xl shadow-2xl p-6 text-white">
        <h2 className="text-xl font-bold">{title}</h2>
        <p className="mt-2 text-white/75 text-sm leading-relaxed">{message}</p>

        <div className="mt-6 flex justify-end gap-3">
          {secondaryText && (
            <button
              onClick={onSecondary}
              className="rounded-md border border-white/20 bg-white/5 px-4 py-2 text-sm font-semibold text-white hover:bg-white/10"
            >
              {secondaryText}
            </button>
          )}

          <button
            onClick={onPrimary}
            className="rounded-md bg-custom-gradient px-4 py-2 text-sm font-semibold text-white shadow-sm hover:-translate-y-0.5 hover:shadow-md active:translate-y-0"
          >
            {primaryText}
          </button>
        </div>
      </div>
    </div>
  );
};

export default ErrorModal;
