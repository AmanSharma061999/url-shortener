import React from "react";
import Card from "./Card";

import { useNavigate } from "react-router-dom";
import { useAuth } from "../contextApi/ContextApi";

const LandingPage = () => {
  const navigate = useNavigate();
  const { user, authLoading } = useAuth();

  const handleManageLinks = () => {
    if (authLoading) return;
    if (!user) {
      return navigate("/login");
    }
    navigate("/dashboard");
  };

  const handleCreateLink = () => {
    if (authLoading) return;
    if (!user) return navigate("/login");

    navigate("/dashboard", { state: { openCreate: true } });
  };

  return (
    <div className="min-h-[calc(100vh-64px)] lg:px-14 sm:px-8 px-4 relative z-10">
      {/* HERO ROW */}
      <div className="lg:flex-row flex-col lg:py-5 pt-16 lg:gap-10 gap-8 flex justify-between items-center">
        {/* LEFT */}
        <div className="flex-1">
          <h1 className="font-bold font-roboto text-white md:text-5xl text-3xl md:leading-[55px] sm:leading-[45px] leading-10 lg:w-full md:w-[70%]">
            Linklytics Simplifies URL Shortening for Efficient Sharing
          </h1>

          <p className="text-white/80 text-sm my-5">
            Linklytics streamlines the process of shortening URLs, making it
            easier than ever to share links across various platforms. Our
            user-friendly interface allows you to create, manage, and track your
            shortened links with just a few clicks.
          </p>

          <div className="flex item-center gap-3">
            <button
              className="bg-custom-gradient w-40 text-white rounded-md py-2 shadow-sm transition hover:-translate-y-0.5 hover:shadow-md"
              onClick={handleManageLinks}
            >
              Manage Links
            </button>

            <button
              className="border border-white/30 w-40 text-white rounded-md py-2 bg-white/5 backdrop-blur-md transition hover:bg-white/10"
              onClick={handleCreateLink}
            >
              Create Short Link
            </button>
          </div>
        </div>

        {/* RIGHT — HERO IMAGE */}
        <div className="flex-1 flex justify-center">
          <div className="relative hero-art">
            {/* ambient outer glow */}
            <div className="pointer-events-none absolute -inset-12 rounded-[50px] bg-gradient-to-r from-emerald-400/20 via-sky-400/15 to-violet-400/20 blur-3xl opacity-80" />

            <div className="flex-1 flex justify-center">
              <div className="relative hero-art">
                {/* ambient glow only */}
                <div className="pointer-events-none absolute -inset-16 bg-gradient-to-r from-emerald-400/20 via-sky-400/15 to-violet-400/20 blur-3xl opacity-70" />

                {/* image WITHOUT slab */}
                <img
                  src="/images/img2.png"
                  alt="Linklytics hero"
                  className="hero-img relative w-[340px] sm:w-[420px] md:w-[460px] object-contain"
                />
              </div>
            </div>
          </div>
        </div>
      </div>

      {/* FEATURE SECTION */}
      <div className="sm:pt-14 pt-10">
        <div className="relative rounded-3xl border border-white/15 bg-white/5 backdrop-blur-xl p-6 sm:p-8 shadow-lg overflow-hidden animate-[panelIn_.7s_ease-out_both] motion-reduce:animate-none">
          {/* soft emerald + violet glow */}
          <div className="pointer-events-none absolute -top-24 left-1/2 h-64 w-64 -translate-x-1/2 rounded-full bg-emerald-400/20 blur-3xl" />
          <div className="pointer-events-none absolute -bottom-24 right-16 h-56 w-56 rounded-full bg-violet-400/15 blur-3xl" />

          {/* heading */}
          <p className="relative text-emerald-300 font-semibold tracking-wide lg:w-[60%] md:w-[60%] text-center mx-auto sm:text-2xl text-xl drop-shadow">
            Trusted by thousands of users worldwide, Linklytics is the go-to
            solution.
          </p>

          {/* divider */}
          <div className="relative mx-auto mt-5 h-[3px] w-36 rounded-full bg-gradient-to-r from-sky-400 via-violet-400 to-emerald-400 opacity-80" />

          {/* cards */}
          <div className="relative pt-6 pb-2 grid lg:gap-7 gap-4 xl:grid-cols-4 lg:grid-cols-3 sm:grid-cols-2 grid-cols-1 mt-4">
            <Card
              title="Simple URL Shortening"
              desc="Experience the ease of creating short, memorable URLs in just a few clicks. Our intuitive interface and quick setup process ensure you can start shortening URLs without any hassle."
            />
            <Card
              title="Powerful Analytics"
              desc="Gain insights into your link performance with our comprehensive analytics dashboard. Track clicks, geographical data, and referral sources to optimize your marketing strategies."
            />
            <Card
              title="Enhanced Security"
              desc="Rest assured with our robust security measures. All shortened URLs are protected with advanced encryption, ensuring your data remains safe and secure."
            />
            <Card
              title="Fast and Reliable"
              desc="Enjoy lightning-fast redirects and high uptime with our reliable infrastructure. Your shortened URLs will always be available and responsive, ensuring a seamless experience for your users."
            />
          </div>

          {/* animation */}
          <style>{`
            @keyframes panelIn {
              0% { opacity: 0; transform: translateY(12px); }
              100% { opacity: 1; transform: translateY(0); }
            }
          `}</style>
        </div>
      </div>
    </div>
  );
};

export default LandingPage;
