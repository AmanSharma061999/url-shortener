import React from "react";
import { FaLink, FaShareAlt, FaEdit, FaChartLine } from "react-icons/fa";

const AboutPage = () => {
  return (
    <div className="lg:px-14 sm:px-8 px-5 min-h-[calc(100vh-64px)] pt-10 relative z-10">
      {/* Glass panel */}
      <div className="rounded-3xl border border-white/15 bg-white/5 backdrop-blur-xl w-full sm:py-10 py-8 px-6 sm:px-10 shadow-lg">
        <h1 className="sm:text-4xl text-white text-3xl font-bold italic mb-3">
          About Linklytics
        </h1>

        <p className="text-white/75 text-sm mb-8 xl:w-[60%] lg:w-[70%] sm:w-[80%] w-full">
          Linklytics simplifies URL shortening for efficient sharing. Easily
          generate, manage, and track your shortened links. Linklytics
          simplifies URL shortening for efficient sharing. Easily generate,
          manage, and track your shortened links.
        </p>

        <div className="space-y-6 xl:w-[60%] lg:w-[70%] sm:w-[80%] w-full">
          <div className="flex items-start gap-4">
            <div className="h-11 w-11 rounded-xl bg-white/10 backdrop-blur-md border border-white/15 flex items-center justify-center shadow-sm">
              <FaLink className="text-sky-300 text-xl" />
            </div>
            <div>
              <h2 className="sm:text-2xl font-bold text-white">
                Simple URL Shortening
              </h2>
              <p className="text-white/70">
                Experience the ease of creating short, memorable URLs in just a
                few clicks. Our intuitive interface ensures you can start
                shortening without hassle.
              </p>
            </div>
          </div>

          <div className="flex items-start gap-4">
            <div className="h-11 w-11 rounded-xl bg-white/10 backdrop-blur-md border border-white/15 flex items-center justify-center shadow-sm">
              <FaShareAlt className="text-emerald-300 text-xl" />
            </div>
            <div>
              <h2 className="sm:text-2xl font-bold text-white">
                Powerful Analytics
              </h2>
              <p className="text-white/70">
                Track clicks, geography, and referral sources to optimize your
                sharing strategy with confidence.
              </p>
            </div>
          </div>

          <div className="flex items-start gap-4">
            <div className="h-11 w-11 rounded-xl bg-white/10 backdrop-blur-md border border-white/15 flex items-center justify-center shadow-sm">
              <FaEdit className="text-violet-300 text-xl" />
            </div>
            <div>
              <h2 className="sm:text-2xl font-bold text-white">
                Enhanced Security
              </h2>
              <p className="text-white/70">
                Robust security measures and encryption keep your links and data
                safe.
              </p>
            </div>
          </div>

          <div className="flex items-start gap-4">
            <div className="h-11 w-11 rounded-xl bg-white/10 backdrop-blur-md border border-white/15 flex items-center justify-center shadow-sm">
              <FaChartLine className="text-rose-300 text-xl" />
            </div>
            <div>
              <h2 className="sm:text-2xl font-bold text-white">
                Fast and Reliable
              </h2>
              <p className="text-white/70">
                Fast redirects and reliable uptime so your short links always
                work smoothly.
              </p>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default AboutPage;
