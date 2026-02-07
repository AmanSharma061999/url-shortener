import { useQuery } from "@tanstack/react-query";
import api from "../api/api";

const toYMD = (d) => new Date(d).toISOString().slice(0, 10);

export const useFetchTotalClicks = (startDate, endDate) => {
const start = typeof startDate === 'string' ? startDate.slice(0,10) : toYMD(startDate);
const end = typeof endDate === 'string' ? endDate.slice(0,10) : toYMD(endDate);

console.log(start, end);

  
  return useQuery({
    queryKey: ['totalClicks', start, end],
    // enabled: !!startDate && !!endDate,
    queryFn: async () => {
      const response = api.get('/api/urls/totalClicks?', {
        params: {
          startDate: start,
          endDate: end 
        }
      });
      return (await response).data;
    },
    retry: 1,
  });
};








/*

Axios sends the request.
useQuery decides when and how often to send it.

Problems

Issue	                      Why it’s bad

* No caching	              Every page visit refetches
* Duplicate calls	          Two components → 2 network calls
* No background refresh	    Stale data
* No retry	                Temporary error = broken UI
* Manual loading & error	  Boilerplate everywhere
* Hard to refetch	          You must write more code

*/