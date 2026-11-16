import {createFileRoute, useNavigate} from '@tanstack/react-router'
import {useMutation, useQueryClient} from "@tanstack/react-query";
import login from "../features/auth_related/api_calls/login";
import {useState} from "react";

export const Route = createFileRoute('/login')({
    component: RouteComponent,
})

function RouteComponent() {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const navigate = useNavigate();

    const queryClient = useQueryClient();
    const loginMutation = useMutation({
        mutationFn: login,
        onSuccess: async () => {
            await queryClient.invalidateQueries({queryKey: ["currentUser"]})
            await navigate({
                to: "/about",
                replace: true,
            })
        }
    });

    return (
        <div>
            <form>
                <label htmlFor="username">Username</label>
                <input
                    type="text"
                    id="username"
                    value={username}
                    onChange={(event) => setUsername(event.target.value)}
                />

                <label htmlFor="password">Password</label>
                <input
                    type="password"
                    id="password"
                    value={password}
                    onChange={(event) => setPassword(event.target.value)}
                />

                <button
                    type="submit"
                    onClick={(event) => {
                        event.preventDefault();
                        loginMutation.mutate({ username, password });
                    }}
                    disabled={loginMutation.isPending}
                >
                    Submit
                </button><br/>
                <h1>
                    {loginMutation.isError && "Your shit went wrong"}
                </h1>
            </form>
        </div>
    );
}
