import { PropsWithChildren, ReactNode } from "react";

import "./ListContainer.css"
export function ListContainer(props: PropsWithChildren<Props>) {
  return (
    <div className="list-container">
      <h1>{props.title}</h1>
      {props.children}
      <div className="list-footer">{props.footer}</div>
    </div>
  );
}

interface Props {
  title: string;
  footer?: ReactNode;
}
