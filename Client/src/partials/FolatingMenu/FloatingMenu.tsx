import { PropsWithChildren } from "react";

import "./FloatingMenu.css";
export function FloatingMenu(props: PropsWithChildren<{ title: string, closeFn: (b:boolean) => void }>) {
  return (
    <>
      <div className="floating-menu-background"></div>
      <div className="floating-menu">
        <div className="float-content">
          <button className="floating-menu-close" onClick={() => props.closeFn(false) } >X</button>
          <h1>{props.title}</h1>
          {props.children}
        </div>
      </div>
    </>
  );
}
