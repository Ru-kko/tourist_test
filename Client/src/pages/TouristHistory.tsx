import { useEffect, useState } from "react";
import { render } from "react-dom";
import { useNavigate, useParams, useSearchParams } from "react-router-dom";
import { ListContainer } from "../partials/ListContainer/ListContainer";
import Loading from "../partials/loading/Loading";
import { Table } from "../partials/Table/Table";
import { getTouristHistory } from "../services/tourist";
import { City, PageResponse, Tourist, Trip } from "../typings/server";

export default function TouristHistory() {
  const { id } = useParams();
  const navigation = useNavigate();
  const [page, setPage] = useState(1);
  const [searchParams, _] = useSearchParams();
  const [data, setData] = useState<PageResponse<Trip>>();

  const asyncHandler = (p: Promise<PageResponse<Trip>>) => {
    setData(undefined);
    p.then((d) => setData(d));
  };

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
      asyncHandler(getTouristHistory(parseInt(id!), 1));
    } catch {
      navigation(-1);
    }
  }, []);

  return (
    <ListContainer title={"Trips of " + (data?.content[0] ?  data.content[0].tourist.fullName : "")} >
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
