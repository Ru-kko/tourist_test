import axios from "axios";
import { PageResponse, Tourist, Trip } from "../typings/server";

export async function getAllTourists(page?: number) {
  const res = await axios.request<PageResponse<Tourist>>({
    method: "GET",
    url:
      import.meta.env.VITE_SERVER + "/tourist" + (page ? "?page=" + page : ""),
  });

  return res.data;
}

export async function getTouristHistory(touristId: number, page: number) {
  const res = await axios.request<PageResponse<Trip>>({
    url:
      import.meta.env.VITE_SERVER + "/tourist/" + touristId + "?page=" + page,
    method: "GET",
  });
  
  return res.data;
}
