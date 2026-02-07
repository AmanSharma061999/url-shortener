import React, { useEffect, useState } from "react";
import api from "../../api/api";
import ShortenItem from "./ShortenItem";

const ShortenUrlList = ({ startDate, endDate }) => {
  const [data, setData] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  const [openShortCode, setOpenShortCode] = useState("");
  const [analyticsLoading, setAnalyticsLoading] = useState(false);
  const [analyticsData, setAnalyticsData] = useState([]);

  /*
    openShortCode: string- stores hich ShortUrl is currently open for analytics, also ensures only one is open at a <time datetime="
    
    analyticsLoading: bbolean - indicates if analytics data is being fetched

    analyticsData : array - stores fetched analytics data for the currently open ShortUrl
  */

  // fetchMyUrls:Fetches only the logged-in user’s URLs
  // withCredentials: true Sends cookies (JWT stored in HttpOnly cookie)
  const fetchMyUrls = async () => {
    setLoading(true);
    try {
      const res = await api.get("/api/urls/myurls", {
        withCredentials: true,
      });
      setData(res.data || []);
    } catch (err) {
      setError(err?.message || "Failed to load URLs");
    } finally {
      setLoading(false);
    }
  };

  const fetchAnalytics = async (shortCode) => {
    setAnalyticsLoading(true);
    setAnalyticsData([]);
    try {
      const startDateTime = `${startDate}T00:00:00`;
      const endDateTime = `${endDate}T23:59:59`;

      // Calls analytics endpoint for one short URL
      const res = await api.get(
        `/api/urls/analytics/${shortCode}?startDate=${startDateTime}&endDate=${endDateTime}`,
        { withCredentials: true },
      );
      setAnalyticsData(res.data || []);
    } catch {
      setAnalyticsData([]);
    } finally {
      setAnalyticsLoading(false);
    }
  };

  /*

  Controls open / close behavior for URL analytics
  Ensures only one URL’s analytics is visible at a time
  Uses openShortCode to track the currently expanded URL

  */
  const toggleAnalytics = async (shortCode) => {
    if (openShortCode === shortCode) {
      setOpenShortCode("");
      setAnalyticsData([]);
      return;
    }
    setOpenShortCode(shortCode);
    await fetchAnalytics(shortCode);
  };

  useEffect(() => {
    fetchMyUrls();
  }, []);

  if (loading) return <div className="text-white/70 mt-6">Loading...</div>;
  if (error) return <div className="text-red-400 mt-6">{error}</div>;

  return (
    <div className="my-6 space-y-4">
      {data.map((item) => (
        <ShortenItem
          key={item.id}
          {...item}
          isOpen={openShortCode === item.shortUrl}
          onToggleAnalytics={() => toggleAnalytics(item.shortUrl)}
          analyticsLoading={openShortCode === item.shortUrl && analyticsLoading}
          analyticsData={openShortCode === item.shortUrl ? analyticsData : []}
        />
      ))}
    </div>
  );
};

export default ShortenUrlList;
