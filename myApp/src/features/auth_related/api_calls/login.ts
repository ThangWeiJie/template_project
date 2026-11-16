import {api} from "../../../api_setup";

export default async function login(credentials: { username: string, password: string }) {
    const result = await api.post("/login", {
        username: credentials.username,
        password: credentials.password
    });
}

