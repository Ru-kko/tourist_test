import { MouseEvent } from "react";

import "./Table.css";

export function Table<T>(props: Props<T>) {
  return (
    <div className="table-container">
      <table>
        <caption>{props.title}</caption>
        <thead>
          <tr>
            {Object.entries<Header>(props.headers).map(([key, inf]) =>
              inf.render === false ? (
                ""
              ) : (
                <th id={key} scope="col" key={key}>
                  {inf.name}
                </th>
              )
            )}
          </tr>
        </thead>
        <tbody>
          {props.data.map((inf) => (
            <tr
              key={String(inf[props.idcol])}
              onClick={(e) => {
                props.onRowClick?.(inf, e);
              }}
              className={props.onRowClick ? "clikcable-row" : ""}
            >
              {Object.entries<Header>(props.headers).map(([key, colData]) =>
                colData.render === false ? (
                  ""
                ) : (
                  <td
                    key={key}
                    data-label={String(props.headers[key as keyof T].name)}
                  >
                    {String(inf[key as keyof T])}
                  </td>
                )
              )}
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

type Header = {
  name: string;
  id?: boolean;
  render?: boolean;
  label?: string;
};

interface Props<T> {
  title: string;
  idcol: keyof T;
  onRowClick?: (row: T, e?: MouseEvent<HTMLTableRowElement>) => void;

  headers: {
    [K in keyof T]: Header;
  };
  data: T[];
}
