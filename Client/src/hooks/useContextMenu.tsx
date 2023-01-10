import { useRef, useState } from "react";
import {
  ContextMenu,
  ContextMenuOptions,
} from "../partials/ContextMenu/ContextMenu";

export function useContextMenu(): [
  JSX.Element,
  (x: number, y: number, z: ContextMenuOptions[]) => void
] {
  const [Component, setContextmenu] = useState(<></>);
  const ref = useRef<HTMLDivElement>(null);

  const Open = (x: number, y: number, options: ContextMenuOptions[]) => {
    const handleClick = (e: MouseEvent) => {
      if (ref.current && !ref.current.contains(e.target as Node)) {
        document.removeEventListener("mousedown", handleClick, true);
        setContextmenu(<></>);
      }
    };
    setContextmenu(
      <ContextMenu options={options} y={y} x={x} reference={ref} />
    );
    document.addEventListener("mousedown", handleClick);
  };

  return [Component, Open];
}
