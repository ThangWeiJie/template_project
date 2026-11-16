import {createFileRoute, isRedirect, Link, Outlet, redirect} from "@tanstack/react-router";
import getCurrentUser from "../features/auth_related/api_calls/getCurrentUser";

export const Route = createFileRoute('/_authenticated')({
    component: AuthenticatedComponentRoot,
    beforeLoad: async ({location}) => {
        try {
            const user = await getCurrentUser();
            if (!user) {
                throw redirect({
                    to: '/login',
                    search: {
                        redirect: location.href
                    }
                });
            }
        } catch (e) {
            if (isRedirect(e)) throw e;

            throw redirect({
                to: '/login',
                search: {
                    redirect: location.href
                },
            })
        }
    },
})

function AuthenticatedComponentRoot() {
    return (
        <>
            <div className="p-2 flex gap-2">
                <Link to="/" className="[&.active]:font-bold">
                    Home
                </Link>{' '}
                <Link to="/about" className="[&.active]:font-bold">
                    About
                </Link>{' '}
                <Link to="/main" className="[&.active]:font-bold">
                    Main
                </Link>
                <br/>
                <Outlet />
            </div>
        </>
    );
}