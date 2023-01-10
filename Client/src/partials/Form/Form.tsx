import { FormEventHandler, MouseEventHandler } from "react";
import "./Form.css";

export default function Form(props: Props) {
  return (
    <form onSubmit={props.onSubmit}>
      {props.data.map((inp, i) =>
        inp instanceof Array ? (
          <div className="form-horizontal-row" key={i + ""}>
            {inp.map((d, ii) => (
              <InputForm {...d} key={ii}/>
            ))}
          </div>
        ) : (
          <InputForm {...inp} key={i + ""} />
        )
      )}
    </form>
  );
}

function InputForm(props: InputProps) {
  return (
    <div>
      <input
        className={props.type === "submit" ? "submit-btn" : ""}
        type={props.type}
        name={props.name}
        required={props.required}
        readOnly={props.readonly}
        defaultValue={props.type === "button" ? props.label : props.default}
        onClick={props.onClick}
        onChange={(e) => props.onChange?.(e.target.name, e.target.value)}
      />
      {props.type === "button" || props.type === "submit" ? (
        ""
      ) : (
        <>
          <div className="cut" />
          <span>{props.label}</span>
        </>
      )}
    </div>
  );
}

export interface InputProps {
  name?: string;
  type: "text" | "password" | "date" | "number" | "submit" | "button";
  required?: boolean;
  default?: string;
  readonly?: boolean;
  label: string;
  onChange?: (name: string, vale: string) => void;
  onClick?: MouseEventHandler<HTMLInputElement>;
}

interface Props {
  onSubmit?: FormEventHandler<HTMLFormElement>;
  data: (InputProps[] | InputProps)[];
}
