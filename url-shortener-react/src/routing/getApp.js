import { subDomainList } from "./subDomain.js";
import { getSubDomain } from "./getSubDomain.js";

export const getApp = () => {
  const subdomain = getSubDomain(window.location.hostname);

  //default (main) app
  const mainApp = subDomainList.find((app) => app.main);  

  // if no subdomain (example: localhost or domain.com)
  if(!subdomain) 
    return mainApp.app;

  // match based on subdomain (example: url.localhost or url.domain.com)
  const matchedApp = subDomainList.find((app) => app.subdomain === subdomain);

  return matchedApp ? matchedApp.app : mainApp.app;



}