import { PropsWithChildren } from "react";

import "./ListContainer.css"
export function ListContainer(props: PropsWithChildren<Props>) {
  return (
    <div className="list-container">
      <h1>{props.title}</h1>
      {props.children}
      <div className="list-footer"></div>
    </div>
  );
}

interface Props {
  title: string;
}
