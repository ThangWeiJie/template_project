import {Outlet, createRootRouteWithContext, Link} from "@tanstack/react-router";
import {QueryClient} from "@tanstack/react-query";
import {TanStackRouterDevtools} from "@tanstack/react-router-devtools";

export const Route = createRootRouteWithContext<{ queryClient: QueryClient }>()({
  component: RootComponent
});

function RootComponent() {
  return (
    <>
      <Outlet/>
      <TanStackRouterDevtools/>
    </>
  )
}