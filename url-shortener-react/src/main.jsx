import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import "./index.css";
import App from "./App.jsx";
import { AuthProvider } from "./contextApi/ContextApi.jsx";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import { getApp } from "./routing/getApp";

const queryClient = new QueryClient({
  defaultOptions: {
    queries: {
      refetchOnWindowFocus: false,
      retry: 1,
    },
  },
});

const AppRoot = getApp();

createRoot(document.getElementById("root")).render(
  <StrictMode>
    <QueryClientProvider client={queryClient}>
      <AuthProvider>
        <AppRoot />
      </AuthProvider>
    </QueryClientProvider>
  </StrictMode>,
);

// <StrictMode> – Enables extra runtime checks in development to highlight unsafe or deprecated React patterns.

// <QueryClientProvider> – Provides the React Query client to the app, enabling data fetching and caching features.

// <AuthProvider> - Wraps the app with authentication context, allowing components to access auth state and functions.
