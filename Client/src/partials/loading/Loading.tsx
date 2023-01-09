import React from "react";

import "./Loading.css";


interface Props {
  width: string;
}

export default function Loading(props:Props) {
  return (
    <div className="loading-container" style={{width: props.width}}>
      <span style={{width: props.width}}/>
    </div>
  );
}
