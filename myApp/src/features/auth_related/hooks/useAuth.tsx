import { useQuery } from "@tanstack/react-query";
import getCurrentUser from "../api_calls/getCurrentUser";

export default function useCurrentUser() {
  return useQuery({
      queryKey: ["user", "currentUser"],
      queryFn: getCurrentUser,
      staleTime: Infinity,
      retry: false
  })
};