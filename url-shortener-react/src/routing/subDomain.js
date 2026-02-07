import MainAppRouter from "./MainAppRouter";
import RedirectAppRouter from "./RedirectAppRouter";

export const subDomainList = [
  {
    subdomain: "www",
    app: MainAppRouter,
    main: true,
  },
  {
    subdomain: "url",
    app: RedirectAppRouter,
    main: false,
  }
];