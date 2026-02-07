export const getSubDomain = (hostname) => {
  const parts = hostname.split(".");
  const isLocalhost = parts[parts.length - 1] === "localhost";

  // url.localhost -> "url", localhost -> ""
  if (isLocalhost) {
    return parts.length > 1 ? parts[0] : "";
  }

  // url.domain.com -> "url", domain.com -> ""
  return parts.length > 2 ? parts[0]: "";
};