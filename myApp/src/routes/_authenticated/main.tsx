import {createFileRoute} from '@tanstack/react-router'

export const Route = createFileRoute('/_authenticated/main')({
    component: RouteComponent,
})

function RouteComponent() {
    return (
        <div>Welcome to the main page</div>
    );
}
