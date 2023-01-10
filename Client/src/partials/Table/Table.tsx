import { MouseEvent } from "react";

import "./Table.css";

export function Table<T>(props: Props<T>) {
  return (
    <div className="table-container">
      <table>
        <thead>
          <tr>
            {Object.entries<Header<T, keyof T>>(props.headers).map(
              ([key, inf]) =>
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
              {Object.entries<Header<T, keyof T>>(props.headers).map(
                ([key, colData]) =>
                  colData.render === false ? (
                    ""
                  ) : (
                    <td
                      key={key}
                      data-label={String(props.headers[key as keyof T].name)}
                    >
                      {props.headers[key as keyof T].preprocess != undefined
                        ? props.headers[key as keyof T].preprocess!(
                            inf[key as keyof T]
                          )
                        : String(inf[key as keyof T])}
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

interface Header<T, K extends keyof T> {
  preprocess?: (d: T[K]) => string;
  name: string;
  render?: boolean;
}

interface Props<T> {
  idcol: keyof T;
  onRowClick?: (row: T, e: MouseEvent<HTMLTableRowElement>) => void;
  headers: {
    [K in keyof T]: Header<T, keyof T>;
  };
  data: T[];
}
