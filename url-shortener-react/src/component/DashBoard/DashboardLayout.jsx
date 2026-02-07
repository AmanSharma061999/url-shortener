import React, { useEffect, useMemo, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import Graph from "./Graph";
import { useFetchTotalClicks } from "../../hooks/useQuery";
import ShortenPopUp from "./ShortenPopUp";
import ShortenUrlList from "./ShortenUrlList";
import Loader from "../Loader";

const formatDate = (d) => new Date(d).toISOString().slice(0, 10);

const DashboardLayout = () => {
  const today = useMemo(() => new Date(), []);
  const location = useLocation();
  const navigate = useNavigate();

  const defaultStartDate = useMemo(() => {
    const d = new Date(today);
    d.setDate(d.getDate() - 14);
    return formatDate(d);
  }, [today]);

  const defaultEndDate = useMemo(() => formatDate(today), [today]);

  const [startDate, setStartDate] = useState(defaultStartDate);
  const [endDate, setEndDate] = useState(defaultEndDate);

  const [shortenPopUp, setShortenPopUp] = useState(() =>
    Boolean(location.state?.openCreate),
  );

  const { data, isLoading, isError, error } = useFetchTotalClicks(
    startDate,
    endDate,
  );

  useEffect(() => {
    if (location.state?.openCreate) {
      navigate(location.pathname, { replace: true, state: null });
    }
  }, [location.state, location.pathname, navigate]);

  const graphData = useMemo(() => {
    if (!data) return [];
    return Object.entries(data)
      .map(([clickDate, count], idx) => ({
        id: idx + 1,
        clickDate,
        count,
      }))
      .sort((a, b) => a.clickDate.localeCompare(b.clickDate));
  }, [data]);

  const hasAnyClicks = useMemo(
    () => graphData.some((x) => Number(x.count) > 0),
    [graphData],
  );

  if (isLoading) {
    return <Loader message="Fetching analytics..." />;
  }

  if (isError) {
    if (error?.response?.status === 403) {
      navigate("/unauthorized", { replace: true });
      return null;
    }

    return (
      <div className="p-6 text-white">
        Error: {error?.message || "Something went wrong"}
      </div>
    );
  }

  return (
    <div className="lg:px-14 sm:px-8 px-4 min-h-[calc(100vh-64px)]">
      <div className="lg:w-[90%] w-full mx-auto py-16">
        <div className="mb-6 flex flex-col gap-4 sm:flex-row sm:items-end sm:justify-between">
          <div className="flex flex-col sm:flex-row gap-3 sm:items-end">
            <div className="flex flex-col">
              <label className="text-white/70 text-sm mb-1">Start Date</label>
              <input
                type="date"
                value={startDate}
                onChange={(e) => setStartDate(e.target.value)}
                className="rounded-md bg-white/10 border border-white/10 px-3 py-2 text-white"
              />
            </div>

            <div className="flex flex-col">
              <label className="text-white/70 text-sm mb-1">End Date</label>
              <input
                type="date"
                value={endDate}
                onChange={(e) => setEndDate(e.target.value)}
                className="rounded-md bg-white/10 border border-white/10 px-3 py-2 text-white"
              />
            </div>
          </div>

          <div className="sm:text-right">
            <button
              className="bg-custom-gradient px-4 py-2 rounded-md text-white font-semibold shadow-sm transition hover:-translate-y-0.5 hover:shadow-md active:translate-y-0"
              onClick={() => setShortenPopUp(true)}
            >
              Create a New Short URL
            </button>
          </div>
        </div>

        <div className="h-96 relative">
          {!hasAnyClicks && (
            <div className="absolute inset-0 flex flex-col justify-center items-center">
              <h1 className="text-white/85 font-serif sm:text-2xl text-[18px] font-bold mb-1">
                No Data For This Time Period
              </h1>
              <h3 className="sm:w-96 w-[90%] text-center sm:text-lg text-sm text-white/60">
                Share your short link to view where your engagements are coming
                from
              </h3>
            </div>
          )}

          <Graph graphData={graphData} />
        </div>

        <div className="mt-10">
          <ShortenUrlList startDate={startDate} endDate={endDate} />
        </div>

        <ShortenPopUp
          open={shortenPopUp}
          setOpen={setShortenPopUp}
          refetch={() => {}}
        />
      </div>
    </div>
  );
};

export default DashboardLayout;
