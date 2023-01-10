import { useEffect, useState } from "react";
import { useNavigate, useParams, useSearchParams } from "react-router-dom";
import { useHandleAsync } from "../hooks/useHandleAsync";
import { ListContainer } from "../partials/ListContainer/ListContainer";
import Loading from "../partials/loading/Loading";
import { Table } from "../partials/Table/Table";
import { getCityHistory } from "../services/city";
import { getTouristHistory } from "../services/tourist";
import { City, PageResponse, Tourist, Trip } from "../typings/server";

export default function CityHistory() {
  const { id } = useParams();
  const navigation = useNavigate();
  const [page, setPage] = useState(1);
  const [searchParams, _] = useSearchParams();
  const [data, setData] = useState<PageResponse<Trip> | null>(null);
  const asyncHandler = useHandleAsync<PageResponse<Trip>>(setData);

  useEffect(() => {
    if (page <= 1) {
      setPage(1);
      return;
    }
    searchParams.set("page", String(page));
    asyncHandler(getTouristHistory(parseInt(id!), page));
  }, [page]);

  useEffect(() => {
    try {
      if (searchParams.get("page")) {
        setPage(parseInt(searchParams.get("page")!));
        return;
      }
      asyncHandler(getCityHistory(parseInt(id!), 1));
    } catch {
      navigation(-1);
    }
  }, []);

  return (
    <ListContainer title={"Trips in " + (data?.content[0] ?  data.content[0].tourist.fullName : "")} >
      {data ? (
        <Table
          data={data.content}
          idcol="id"
          headers={{
            id: {
              name: "id",
              render: false,
            },
            startDate: {
              name: "Date",
              preprocess: (d) => new Date(d as string).toDateString(),
            },
            tourist: {
              name: "Name",
              preprocess: (T) => (T as Tourist).fullName
            },
            city: {
              name: "City",
              preprocess: (city) => (city as City).name,
            },
          }}
        />
      ) : (
        <Loading width="300px" />
      )}
    </ListContainer>
  );
}