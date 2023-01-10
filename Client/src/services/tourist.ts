import axios from "axios";
import { PageResponse, Tourist } from "../typings/server";

export async function getAllTourists(page?: number) {
  const res = await axios.request<PageResponse<Tourist>>({
    method: "GET",
    url:
      import.meta.env.VITE_SERVER + "/tourist" + (page ? "?page=" + page : ""),
  });

  const newCont = res.data.content.map((i) => ({
    ...i,
    bornDate: new Date(i.bornDate).toDateString(),
  }));
  
  return { ...res.data, content: newCont };
}
