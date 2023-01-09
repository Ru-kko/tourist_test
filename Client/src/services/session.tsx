// import axios from "axios";
import axios from "axios";
import { TokenResponse, User, UserDataRegistration} from "../typings/server";

export async function LogIn(user: User) {
  const res = await axios.request<TokenResponse>({
    method: "POST",
    url: import.meta.env.VITE_SERVER + "/login",
    data: user,
  });

  return res.data;
}

export async function SignUp(user: UserDataRegistration) {
  const res = await axios.request<TokenResponse>({
    method: "POST",
    url: import.meta.env.VITE_SERVER + "/register",
    data: user
  });

  return res.data;
}
