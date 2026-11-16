import { createFileRoute } from '@tanstack/react-router'
import useCurrentUser from "../../features/auth_related/hooks/useAuth";

export const Route = createFileRoute('/_authenticated/about')({
  component: AboutComponent,
})

function AboutComponent() {
  const { data } = useCurrentUser();
  return <div>Hello, { data?.username }</div>
}
