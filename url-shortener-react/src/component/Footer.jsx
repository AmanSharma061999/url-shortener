import React from "react";
import { FaFacebook, FaTwitter, FaInstagram, FaLinkedin } from "react-icons/fa";

const Footer = () => {
  return (
    <footer className="relative mt-16 overflow-hidden">
      {/* Top glowing border */}
      <div className="absolute top-0 left-0 right-0 h-[2px] bg-gradient-to-r from-sky-400 via-violet-400 to-emerald-400 opacity-70" />

      {/* Subtle moving shine */}
      <div className="pointer-events-none absolute inset-0 opacity-35">
        <div className="absolute -left-1/2 top-0 h-full w-1/2 rotate-12 bg-gradient-to-r from-transparent via-white/70 to-transparent animate-[footerShine_7s_linear_infinite]" />
      </div>

      {/* Glass footer body */}
      <div className="border-t border-white/10 bg-white/5 backdrop-blur-md">
        <div className="relative max-w-7xl mx-auto px-4 sm:px-8 lg:px-14 py-10">
          <div className="flex flex-col md:flex-row items-center justify-between gap-8">
            {/* Left */}
            <div className="text-center md:text-left">
              <p className="text-sm text-white/80">
                © {new Date().getFullYear()}{" "}
                <span className="font-semibold text-white">Linklytics</span>.
                All rights reserved.
              </p>
              <p className="mt-1 text-xs text-white/60">
                Shorten • Track • Optimize
              </p>
            </div>

            {/* Social icons */}
            <div className="flex items-center gap-4">
              <a
                href="#"
                aria-label="Facebook"
                className="group relative flex h-10 w-10 items-center justify-center rounded-full border border-white/10 bg-white/5 text-white/80 transition hover:-translate-y-1 hover:bg-white/10"
              >
                <span className="absolute inset-0 rounded-full bg-blue-400/25 blur-md opacity-0 transition group-hover:opacity-100" />
                <FaFacebook className="relative" size={18} />
              </a>

              <a
                href="#"
                aria-label="Twitter"
                className="group relative flex h-10 w-10 items-center justify-center rounded-full border border-white/10 bg-white/5 text-white/80 transition hover:-translate-y-1 hover:bg-white/10"
              >
                <span className="absolute inset-0 rounded-full bg-sky-300/25 blur-md opacity-0 transition group-hover:opacity-100" />
                <FaTwitter className="relative" size={18} />
              </a>

              <a
                href="#"
                aria-label="Instagram"
                className="group relative flex h-10 w-10 items-center justify-center rounded-full border border-white/10 bg-white/5 text-white/80 transition hover:-translate-y-1 hover:bg-white/10"
              >
                <span className="absolute inset-0 rounded-full bg-pink-300/25 blur-md opacity-0 transition group-hover:opacity-100" />
                <FaInstagram className="relative" size={18} />
              </a>

              <a
                href="#"
                aria-label="LinkedIn"
                className="group relative flex h-10 w-10 items-center justify-center rounded-full border border-white/10 bg-white/5 text-white/80 transition hover:-translate-y-1 hover:bg-white/10"
              >
                <span className="absolute inset-0 rounded-full bg-blue-300/25 blur-md opacity-0 transition group-hover:opacity-100" />
                <FaLinkedin className="relative" size={18} />
              </a>
            </div>

            {/* Links */}
            <div className="flex gap-6 text-sm text-white/70">
              <a href="#" className="hover:text-white transition">
                Privacy
              </a>
              <a href="#" className="hover:text-white transition">
                Terms
              </a>
              <a href="#" className="hover:text-white transition">
                Contact
              </a>
            </div>
          </div>
        </div>
      </div>

      <style>{`
        @keyframes footerShine {
          0% { transform: translateX(-40%) rotate(12deg); }
          100% { transform: translateX(240%) rotate(12deg); }
        }
      `}</style>
    </footer>
  );
};

export default Footer;
