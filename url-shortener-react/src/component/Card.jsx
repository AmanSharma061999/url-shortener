import React from "react";

const Card = ({ title, desc }) => {
  return (
    <div className="group relative w-full h-full">
      <div className="absolute -inset-0.5 rounded-2xl bg-gradient-to-r from-sky-400 via-violet-400 to-emerald-400 opacity-20 blur transition duration-300 group-hover:opacity-50" />

      <div className="relative flex h-full flex-col rounded-2xl border border-white/30 bg-white/60 backdrop-blur-md p-6 shadow-md transition duration-300 group-hover:-translate-y-1 group-hover:shadow-xl">
        <h3 className="text-lg font-semibold tracking-tight text-slate-900">
          {title}
        </h3>

        <p className="mt-3 text-sm leading-relaxed text-slate-700 line-clamp-4">
          {desc}
        </p>

        <div className="flex-grow" />

        <div className="mt-6 h-1 w-12 rounded-full bg-gradient-to-r from-sky-500 to-violet-500 transition-all duration-300 group-hover:w-20" />
      </div>
    </div>
  );
};

export default Card;
