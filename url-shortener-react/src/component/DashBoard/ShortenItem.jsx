import React, { useEffect, useState } from "react";
import dayjs from "dayjs";
import { FaExternalLinkAlt } from "react-icons/fa";
import { MdOutlineAdsClick, MdAnalytics } from "react-icons/md";
import { IoCopy } from "react-icons/io5";
import { LiaCheckSolid } from "react-icons/lia";
import { Link } from "react-router-dom";
import { Hourglass } from "react-loader-spinner";
import Graph from "./Graph";

const ShortenItem = ({
  originalUrl,
  shortUrl,
  clickCount,
  createdDate,
  isOpen,
  onToggleAnalytics,
  analyticsLoading,
  analyticsData,
}) => {
  const [copied, setCopied] = useState(false);

  const subdomainBase =
    import.meta.env.VITE_REACT_SUBDOMAIN?.replace(/\/$/, "") || "";
  const shortLink = `${subdomainBase}/${shortUrl}`;

  const handleCopy = async () => {
    try {
      await navigator.clipboard.writeText(shortLink);
      setCopied(true);
    } catch (err) {
      console.error("Copy failed", err);
    }
  };

  useEffect(() => {
    if (!copied) return;
    const t = setTimeout(() => setCopied(false), 900);
    return () => clearTimeout(t);
  }, [copied]);

  return (
    <div className="relative overflow-hidden rounded-2xl border border-white/10 bg-white/5 shadow-xl backdrop-blur-md">
      {/* soft green glow */}
      <div className="pointer-events-none absolute -top-10 -right-10 h-44 w-44 rounded-full bg-emerald-300/10 blur-3xl" />
      <div className="pointer-events-none absolute -bottom-12 -left-12 h-48 w-48 rounded-full bg-green-400/10 blur-3xl" />

      {/* Main row */}
      <div className="relative p-5 flex flex-col gap-4 sm:flex-row sm:items-start sm:justify-between">
        {/* Left */}
        <div className="flex-1 min-w-0">
          {/* Short URL */}
          <div className="flex items-center gap-2">
            <span className="text-xs text-white/60">Short URL</span>
            <a
              href={shortLink}
              target="_blank"
              rel="noreferrer"
              className="text-emerald-200 font-semibold hover:underline truncate"
              title={shortLink}
            >
              {shortLink}
            </a>
            <FaExternalLinkAlt className="text-emerald-200/70 text-sm" />
          </div>

          {/* Original URL */}
          <div className="mt-2">
            <span className="text-xs text-white/60">Original</span>
            <p
              title={originalUrl}
              className="mt-1 text-sm text-white/85 truncate hover:whitespace-normal hover:break-words"
            >
              {originalUrl}
            </p>
          </div>

          {/* Meta */}
          <div className="mt-3 flex flex-wrap items-center gap-6 text-sm text-white/70">
            <div className="flex items-center gap-2">
              <MdOutlineAdsClick className="text-emerald-200/70" />
              <span>{clickCount}</span>
              <span>{clickCount === 1 ? "Click" : "Clicks"}</span>
            </div>

            <div>
              <span className="text-xs text-white/50">Created</span>{" "}
              {dayjs(createdDate).format("MMM DD, YYYY")}
            </div>
          </div>
        </div>

        {/* Right actions */}
        <div className="flex shrink-0 items-center gap-3 sm:justify-end">
          <button
            type="button"
            onClick={handleCopy}
            className="flex items-center gap-2 rounded-xl border border-white/10 bg-white/10 px-4 py-2 text-sm font-semibold text-white hover:bg-white/15"
          >
            {copied ? "Copied" : "Copy"}
            {copied ? <LiaCheckSolid /> : <IoCopy />}
          </button>

          <button
            type="button"
            onClick={onToggleAnalytics}
            className="flex items-center gap-2 rounded-xl border border-emerald-300/20 bg-emerald-300/10 px-4 py-2 text-sm font-semibold text-emerald-100 hover:bg-emerald-300/15"
          >
            Analytics <MdAnalytics />
          </button>
        </div>
      </div>

      {/* Analytics panel */}
      <div
        className={`${isOpen ? "block" : "hidden"} border-t border-white/10`}
      >
        <div className="relative min-h-[360px] p-4">
          {analyticsLoading ? (
            <div className="min-h-[320px] flex justify-center items-center">
              <Hourglass
                visible
                height="50"
                width="50"
                colors={["#86efac", "#34d399"]}
              />
            </div>
          ) : (
            <>
              {(analyticsData?.length || 0) === 0 && (
                <div className="absolute inset-0 flex flex-col justify-center items-center text-center px-6">
                  <h1 className="text-white/80 text-lg font-semibold">
                    No analytics yet
                  </h1>
                  <p className="text-white/60 text-sm">
                    Share the short link to see engagement
                  </p>
                </div>
              )}
              <Graph graphData={analyticsData || []} />
            </>
          )}
        </div>
      </div>
    </div>
  );
};

export default ShortenItem;
