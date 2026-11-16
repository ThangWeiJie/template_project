import {api} from "../../../api_setup";

export default async function getCurrentUser() {
    try {
        const res = await api.get<User>("/me");
        return res.data;
    } catch (e) {
        console.log(e);
    }
}