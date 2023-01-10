import { useEffect, useState } from "react";
import { useNavigate, useSearchParams } from "react-router-dom";
import { useContextMenu } from "../hooks/useContextMenu";
import { ListContainer } from "../partials/ListContainer/ListContainer";
import Loading from "../partials/loading/Loading";
import Pagination from "../partials/Pagination/Pagination";
import { Table } from "../partials/Table/Table";
import { getAllTourists } from "../services/tourist";
import { useAppSelector } from "../store";
import { PageResponse, Tourist } from "../typings/server";

export function Tourists() {
  const [page, setPage] = useState(1);
  const [data, setData] = useState<PageResponse<Tourist> | null>();
  const [searchParams, _] = useSearchParams();
  const [ContextMenu, OpenContextMenu] = useContextMenu();
  const User = useAppSelector((st) => st.session);
  const navigation = useNavigate();

  const handleAsycn = (p: Promise<PageResponse<Tourist>>) => {
    setData(null);
    p.then((d) => {
      setData(d);
    });
  };
  useEffect(() => {
    searchParams.set("page", String(page));
    handleAsycn(getAllTourists(page));
  }, [page]);

  useEffect(() => {
    const pageParam = searchParams.get("page");

    try {
      if (pageParam && parseInt(pageParam) >= 1) {
        setPage(parseInt(pageParam));
        return;
      }
    } catch {
      setPage(1);
    }
  }, []);

  return (
    <ListContainer title="Tourist">
      {ContextMenu}
      {data ? (
        <>
          <Table
            onRowClick={(t, e) => {
              const options = [
                {
                  name: "History",
                  onClick: () => {
                    navigation("/travelers/" + t.id);
                    console.log(t.idCard, User?.cardId);
                  },
                },
              ];
              if (t.idCard === User?.cardId) {
                options.push({
                  name: "update",
                  onClick: () => {
                    navigation("/update");
                  },
                });
              }
              OpenContextMenu(e.clientX, e.clientY, options);
            }}
            data={data.content}
            idcol="id"
            headers={{
              id: {
                name: "id",
                render: false,
              },
              fullName: {
                name: "Name",
              },
              idCard: {
                name: "Card Id",
              },
              bornDate: {
                name: "Born Date",
              },
              travelFrequency: {
                name: "Frequency",
              },
              travelBudget: {
                name: "Average Budget",
              },
            }}
          />
          <Pagination actual={page} pages={data.totalPages} onClick={setPage} />
        </>
      ) : (
        <Loading width="200px" />
      )}
    </ListContainer>
  );
}
