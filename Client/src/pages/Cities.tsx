import { ReactNode, useEffect, useState } from "react";
import { useNavigate, useSearchParams } from "react-router-dom";
import { useContextMenu } from "../hooks/useContextMenu";
import { useHandleAsync } from "../hooks/useHandleAsync";
import { ContextMenuOptions } from "../partials/ContextMenu/ContextMenu";
import { FloatingMenu } from "../partials/FolatingMenu/FloatingMenu";
import Form from "../partials/Form/Form";
import { ListContainer } from "../partials/ListContainer/ListContainer";
import Loading from "../partials/loading/Loading";
import Pagination from "../partials/Pagination/Pagination";
import { Table } from "../partials/Table/Table";
import {
  createCity,
  deleteCity,
  getAllCities,
  reserveCity,
  updateCity,
} from "../services/city";
import { useAppSelector } from "../store";
import { City, PageResponse } from "../typings/server";

export default function Cities() {
  const [page, SetPage] = useState(1);
  const [data, setData] = useState<PageResponse<City> | null>(null);
  const [ContextMenu, OpenContextMenu] = useContextMenu();
  const [searchParams, _] = useSearchParams();
  const [Floating, SetFloating] = useState<ReactNode>();
  const [searchTxt, updateSearch] = useState("");
  const session = useAppSelector((s) => s.session);
  const asyncHandler = useHandleAsync(setData);
  const navigation = useNavigate();

  useEffect(() => {
    searchParams.set("page", String(page));
    asyncHandler(getAllCities(page));
  }, [page]);
  useEffect(() => {
    try {
      const pageParam = parseInt(searchParams.get("page")!);
      if (pageParam >= 1) {
        SetPage(pageParam);
        return;
      }
      SetPage(1);
    } catch {
      SetPage(1);
    }
  }, []);

  return (
    <ListContainer
      title="Cities"
      footer={
        <div className="cities-footer">
          {session?.admin ? (
            <button
              onClick={() =>
                SetFloating(<CityCreator setClose={SetFloating} />)
              }
            >
              +
            </button>
          ) : (
            ""
          )}
          <input
            type="text"
            placeholder="Search"
            onChange={(e) => updateSearch(e.target.value)}
          />
          <button onClick={() => asyncHandler(getAllCities(1, searchTxt))}>
            Search
          </button>
        </div>
      }
    >
      {Floating ? Floating : ""}
      {ContextMenu}
      {data ? (
        <>
          <Table
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
              population: {
                name: "Population",
              },
              mostTuristicPlace: {
                name: "BestPlace",
              },
              mostReserverdHotel: {
                name: "BestHotel",
              },
            }}
            onRowClick={(d, e) => {
              let options: ContextMenuOptions[] = [
                {
                  name: "History",
                  onClick: () => {
                    navigation("/cities/" + d.id);
                  },
                },
              ];
              if (session) {
                options.push({
                  name: "Reservate",
                  onClick: () => {
                    SetFloating(<CityReserv city={d} setClose={SetFloating} />);
                  },
                });
              }

              if (session?.admin) {
                options.push(
                  {
                    name: "Edit",
                    onClick: () => {
                      SetFloating(
                        <CityEditor SetClose={SetFloating} city={d} />
                      );
                    },
                  },
                  {
                    name: "Delete",
                    onClick: () => {
                      deleteCity(d.id).then(() =>
                        asyncHandler(getAllCities(page))
                      );
                    },
                  }
                );
              }
              OpenContextMenu(e.clientX, e.clientY, options);
            }}
          />
          <Pagination pages={data.totalPages} actual={page} onClick={SetPage} />
        </>
      ) : (
        <Loading width="300px" />
      )}
    </ListContainer>
  );
}

function CityEditor(props: { city: City; SetClose: (b: any) => void }) {
  const [changes, setChages] = useState(props.city);
  const update = (name: string, val: string) => {
    setChages({ ...changes, [name]: val });
  };
  return (
    <FloatingMenu title="Edit" closeFn={props.SetClose}>
      <Form
        data={[
          {
            readonly: true,
            required: true,
            label: "ID",
            type: "number",
            default: props.city.id + "",
          },
          {
            required: true,
            label: "Name",
            name: "name",
            type: "text",
            default: props.city.name,
            onChange: update,
          },
          {
            required: true,
            type: "number",
            label: "Population",
            name: "population",
            default: props.city.population + "",
            onChange: update,
          },
          [
            {
              required: true,
              type: "text",
              label: "BestPlace",
              default: props.city.mostTuristicPlace,
              onChange: update,
            },
            {
              type: "text",
              required: true,
              label: "BestHotel",
              default: props.city.mostReserverdHotel,
              onChange: update,
            },
          ],
          {
            type: "submit",
            required: true,
            label: "Save",
          },
        ]}
        onSubmit={(e) => {
          e.preventDefault();
          updateCity(changes);
          props.SetClose(false);
        }}
      />
    </FloatingMenu>
  );
}

function CityReserv(props: { city: City; setClose: (b: any) => void }) {
  const [isLoading, setLoading] = useState(false);
  const [date, setDate] = useState("");

  return (
    <FloatingMenu title="Reserve" closeFn={props.setClose}>
      {isLoading ? (
        <Loading width="30%" />
      ) : (
        <Form
          data={[
            {
              type: "number",
              label: "ID",
              readonly: true,
              required: true,
              default: props.city.id + "",
            },
            {
              type: "date",
              label: "Date",
              required: true,
              onChange: (_, val) => setDate(val),
            },
            {
              type: "submit",
              label: "Reserve",
            },
          ]}
          onSubmit={(e) => {
            e.preventDefault();
            setLoading(true);
            reserveCity(props.city.id, date)
              .then(() => props.setClose(false))
              .catch((e) => {
                if (e.code === "ERR_BAD_REQUEST") {
                  alert("That day is full");
                }
                props.setClose(false);
              });
          }}
        />
      )}
    </FloatingMenu>
  );
}

function CityCreator(props: { setClose: (b: any) => void }) {
  const [newCity, setCity] = useState<Partial<City>>({});
  const update = (key: string, val: string) => {
    setCity({ ...newCity, [key]: val });
  };

  return (
    <FloatingMenu title="Create" closeFn={props.setClose}>
      <Form
        data={[
          {
            required: true,
            label: "Name",
            name: "name",
            type: "text",
            onChange: update
          },
          {
            required: true,
            type: "number",
            label: "Population",
            name: "population",
            onChange: update,
          },
          [
            {
              required: true,
              type: "text",
              label: "BestPlace",
              name: "mostTuristicPlace",
              onChange: update,
            },
            {
              type: "text",
              required: true,
              label: "BestHotel",
              name: "mostReserverdHotel",
              onChange: update,
            },
          ],
          {
            type: "submit",
            required: true,
            label: "Save",
          },
        ]}
        onSubmit={(e) => {
          e.preventDefault();
          createCity(newCity as City).catch(() => {
            alert("Someting was worng")
          })
          props.setClose(false);
        }}
      />
    </FloatingMenu>
  );
}
