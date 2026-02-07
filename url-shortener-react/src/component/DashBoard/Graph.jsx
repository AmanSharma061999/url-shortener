import React, { useMemo } from "react";
import { Bar } from "react-chartjs-2";
import {
  Chart as ChartJS,
  BarElement,
  CategoryScale,
  LinearScale,
  Legend,
  Tooltip,
} from "chart.js";

ChartJS.register(BarElement, Tooltip, CategoryScale, LinearScale, Legend);

const Graph = ({ graphData = [] }) => {
  const hasAnyClicks = useMemo(
    () => graphData.some((x) => Number(x.count) > 0),
    [graphData],
  );

  const clickCounts = useMemo(() => {
    if (graphData.length > 0) return graphData.map((x) => Number(x.count) || 0);
    return Array(14).fill(0);
  }, [graphData]);

  const labels = useMemo(() => {
    if (graphData.length > 0) return graphData.map((x) => x.clickDate);
    return Array.from({ length: 14 }, (_, i) => `Day ${i + 1}`);
  }, [graphData]);

  const data = {
    labels,
    datasets: [
      {
        label: "Total Clicks",
        data: clickCounts,
        backgroundColor: hasAnyClicks
          ? "rgba(52, 211, 153, 0.45)"
          : "rgba(255, 255, 255, 0.06)",
        borderColor: hasAnyClicks
          ? "rgba(52, 211, 153, 0.9)"
          : "rgba(255, 255, 255, 0.12)",
        borderWidth: 1,
        borderRadius: 10,
        barThickness: 18,
        categoryPercentage: 0.75,
        barPercentage: 0.9,
      },
    ],
  };

  const options = {
    maintainAspectRatio: false,
    responsive: true,
    plugins: {
      legend: {
        display: true,
        labels: {
          // ✅ more visible legend
          color: "rgba(255,255,255,0.95)",
          font: { size: 14, weight: "700" },
          boxWidth: 14,
          boxHeight: 14,
        },
      },
      tooltip: {
        enabled: true,
        backgroundColor: "rgba(10, 14, 30, 0.92)",
        titleColor: "rgba(255,255,255,0.98)",
        bodyColor: "rgba(255,255,255,0.92)",
        borderColor: "rgba(255,255,255,0.18)",
        borderWidth: 1,
        padding: 10,
        displayColors: false,
      },
    },
    scales: {
      x: {
        grid: {
          color: "rgba(255,255,255,0.08)",
          drawBorder: false,
        },
        ticks: {
          // ✅ more visible x ticks
          color: "rgba(255,255,255,0.85)",
          font: { size: 12, weight: "600" },
          maxRotation: 0,
          autoSkip: true,
        },
        title: {
          display: true,
          text: "Date",
          // ✅ stronger title
          color: "rgba(255,255,255,0.95)",
          font: { size: 15, weight: "800" },
        },
      },
      y: {
        beginAtZero: true,
        grid: {
          color: "rgba(255,255,255,0.08)",
          drawBorder: false,
        },
        ticks: {
          color: "rgba(255,255,255,0.85)",
          font: { size: 12, weight: "600" },
        },
        title: {
          display: true,
          text: "Clicks",
          color: "rgba(255,255,255,0.95)",
          font: { size: 15, weight: "800" },
        },
      },
    },
  };

  return (
    <div className="relative w-full h-[320px] sm:h-[360px] rounded-3xl border border-white/[0.10] bg-white/[0.03] backdrop-blur-xl shadow-2xl p-4 sm:p-6 overflow-hidden">
      {/* subtle glow behind chart */}
      <div className="pointer-events-none absolute -inset-12 bg-gradient-to-r from-emerald-400/10 via-sky-400/8 to-violet-400/10 blur-3xl opacity-70" />
      <div className="relative h-full">
        <Bar data={data} options={options} />
      </div>
    </div>
  );
};

export default Graph;
