import { ReactNode, useEffect, useState } from "react";
import { useNavigate, useSearchParams } from "react-router-dom";
import { useContextMenu } from "../hooks/useContextMenu";
import { ListContainer } from "../partials/ListContainer/ListContainer";
import { Table } from "../partials/Table/Table";
import {
  deleteTourist,
  getAllTourists,
  updateTurist,
} from "../services/tourist";
import { SessionActions, useAppSelector, useStoreDispatch } from "../store";
import { PageResponse, Tourist } from "../typings/server";
import Loading from "../partials/loading/Loading";
import Pagination from "../partials/Pagination/Pagination";

import "./styles/cities.css";
import { useHandleAsync } from "../hooks/useHandleAsync";
import { FloatingMenu } from "../partials/FolatingMenu/FloatingMenu";
import Form from "../partials/Form/Form";

export function Tourists() {
  const [page, setPage] = useState(1);
  const [data, setData] = useState<PageResponse<Tourist> | null>();
  const [searchParams, _] = useSearchParams();
  const [ContextMenu, OpenContextMenu] = useContextMenu();
  const [Floating, SetFloating] = useState<ReactNode>();
  const dispatch = useStoreDispatch();
  const User = useAppSelector((st) => st.session);
  const navigation = useNavigate();
  const handleAsync = useHandleAsync(setData);

  useEffect(() => {
    searchParams.set("page", String(page));
    handleAsync(getAllTourists(page));
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
      {Floating ?? ""}
      {data ? (
        <>
          <Table
            onRowClick={(t, e) => {
              const options = [
                {
                  name: "History",
                  onClick: () => {
                    navigation("/travelers/" + t.id);
                  },
                },
              ];
              if (t.idCard === User?.cardId) {
                options.push(
                  {
                    name: "update",
                    onClick: () => {
                      SetFloating(
                        <UpdateTouristsCopm
                          tourist={t}
                          setClose={SetFloating}
                        />
                      );
                    },
                  },
                  {
                    name: "Delete",
                    onClick: () => {
                      deleteTourist().then(() =>
                        dispatch(SessionActions.logOut())
                      );
                    },
                  }
                );
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
              name: {
                name: "Name",
              },
              lastName: {
                name: "Last Name",
              },
              idCard: {
                name: "Card Id",
              },
              bornDate: {
                name: "Born Date",
                preprocess: (d) => new Date(d as string).toDateString(),
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

function UpdateTouristsCopm(props: {
  tourist: Tourist;
  setClose: (b: any) => void;
}) {
  const [changes, setChanges] = useState(props.tourist);
  const dispatch = useStoreDispatch();
  const update = (key: string, val: string) => {
    setChanges({ ...changes, [key]: val });
  };
  return (
    <FloatingMenu title="Update" closeFn={props.setClose}>
      <Form
        data={[
          [
            {
              name: "id",
              required: true,
              readonly: true,
              default: props.tourist.id + "",
              type: "number",
              label: "ID",
            },
            {
              name: "idCard",
              type: "number",
              label: "Card Id",
              default: props.tourist.idCard,
              required: true,
              onChange: update,
            },
          ],
          [
            {
              name: "name",
              label: "Name",
              type: "text",
              default: props.tourist.name,
              required: true,
              onChange: update,
            },
            {
              name: "lastName",
              label: "Last Name",
              type: "text",
              default: props.tourist.lastName,
              onChange: update,
            },
          ],
          {
            name: "bornDate",
            label: "Born Date",
            type: "date",
            default: props.tourist.bornDate,
            required: true,
            onChange: update,
          },
          [
            {
              name: "travelFrequency",
              label: "Travel Frequency",
              type: "number",
              required: true,
              default: props.tourist.travelFrequency + "",
              onChange: update,
            },
            {
              name: "travelBudget",
              label: "Travel Budget",
              type: "number",
              required: true,
              default: props.tourist.travelBudget + "",
              onChange: update,
            },
          ],
          {
            name: "submit",
            type: "submit",
            label: "Submit",
          },
        ]}
        onSubmit={(e) => {
          e.preventDefault();
          updateTurist(changes)
            .then((d) => {
              dispatch(SessionActions.logIn(d.token));
            })
            .finally(() => props.setClose(null));
        }}
      />
    </FloatingMenu>
  );
}
