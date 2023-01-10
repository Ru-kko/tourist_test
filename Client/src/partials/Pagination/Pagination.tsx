import { getPagingRange } from "../../util/paginationRange";

import "./Pagination.css"

export default function Pagination(props: Props) {
  return (
    <div className="pagination">
      <button
        className={props.actual === 1 ? "pag-not-clicable" : ""}
        onClick={() => {
          if (props.actual > 1) props.onClick(props.actual - 1);
        }}
      >
        &laquo;
      </button>
      {getPagingRange(props.actual, props.pages).map((i) => (
        <button
          key={i}
          className={i === props.actual ? "page-actual" : ""}
          onClick={() => props.onClick(i)}
        >
          {i}
        </button>
      ))}
      <button
        className={props.actual === props.pages ? "pag-not-clicable" : ""}
        onClick={() => {
          if (props.actual < props.pages) props.onClick(props.actual + 1);
        }}
      >
        &raquo;
      </button>
    </div>
  );
}

interface Props {
  pages: number;
  actual: number;
  onClick: (n: number) => void;
}
