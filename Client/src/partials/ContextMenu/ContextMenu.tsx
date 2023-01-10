import { RefObject } from "react";

import "./ContextMenu.css"
export function ContextMenu(props: Props) {
  return (
    <div
      ref={props.reference}
      className="contex-menu"
      style={{
        top: props.y + "px",
        left: props.x + "px",
        position: "fixed",
      }}
    >
      <ul>
        {props.options.map((index) => (
          <li key={index.name}>
            <button onClick={index.onClick}>{index.name}</button>
          </li>
        ))}
      </ul>
    </div>
  );
}

interface Props {
  y: number;
  x: number;
  options: ContextMenuOptions[];
  reference: RefObject<HTMLDivElement>;
}

export interface ContextMenuOptions {
  name: string;
  onClick: () => void;
}
