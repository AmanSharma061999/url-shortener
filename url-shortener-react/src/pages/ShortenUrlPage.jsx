import { useEffect } from "react";
import { useParams } from "react-router-dom";

const ShortenUrlPage = () => {
  const { url } = useParams();

  useEffect(() => {
    if (!url) return;

    const backend = import.meta.env.VITE_BACKEND_URL?.replace(/\/$/, "");
    window.location.href = `${backend}/${url}`;
  }, [url]);

  return <p>Redirecting...</p>;
};

export default ShortenUrlPage;
