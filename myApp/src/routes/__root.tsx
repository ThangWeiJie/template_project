import {Outlet, createRootRouteWithContext, Link} from "@tanstack/react-router";
import {QueryClient, useQuery} from "@tanstack/react-query";
import {TanStackRouterDevtools} from "@tanstack/react-router-devtools";
import {api} from "../api_setup";

export const Route = createRootRouteWithContext<{ queryClient: QueryClient }>()({
  component: RootComponent
});

function RootComponent() {
  const { data } = useQuery({
    queryKey: ["user"],
    queryFn: () => getUser("user", "password")
  });

  return (
    <>
      <div className="p-2 flex gap-2">
        <Link to="/" className="[&.active]:font-bold">
          Home
        </Link>{' '}
        <Link to="/about" className="[&.active]:font-bold">
          About
        </Link>
      </div>
      <hr/>
      <Outlet/>
      <TanStackRouterDevtools/>
    </>
  )
}