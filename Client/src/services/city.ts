import axios from "axios";
import { City, PageResponse, Trip } from "../typings/server";

export async function getAllCities(page: number, name?: string) {
  const url = new URLSearchParams()
  url.set("page", page + "");
  if (name){
    url.set("name", name)
  }
  const res = await axios.request<PageResponse<City>>({
    url: import.meta.env.VITE_SERVER + "/city?" + url.toString() ,
    method: "GET",
  });

  return res.data;
}
export async function updateCity(city: City) {
  const res = await axios.request<City>({
    url: import.meta.env.VITE_SERVER + "/city",
    method: "PUT",
    data: city,
  });

  return res.data;
}

export async function reserveCity(id: number, date: string) {
  await axios.request<Trip>({
    url: import.meta.env.VITE_SERVER + "/city/" + id + "?day=" + date,
    method: "POST",
  });
}

export async function createCity(city:City) {
  await axios.request({
    url: import.meta.env.VITE_SERVER + "/city",
    method: "POST",
    data: city
  })
}

export async function deleteCity(id: number) {
  axios.request<void>({
    url: import.meta.env.VITE_SERVER + "/city",
    method: "DELETE",
    data: {
      id: id,
    },
  });
}

export async function getCityHistory(id:number, page: number) {
  const res = await axios.request<PageResponse<Trip>>({
    url: import.meta.env.VITE_SERVER + "/city/" + id + "?page=" + page,
    method: "GET"
  })
  return res.data
}
