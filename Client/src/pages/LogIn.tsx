import { FormEventHandler, useEffect, useState } from "react";
import {
  User,
  UserDataRegistration,
  UserRegistrationRaw,
} from "../typings/server";
import { LogIn, SignUp } from "../services/session";
import Loading from "../partials/loading/Loading";
import {
  RootState,
  useAppSelector,
  useStoreDispatch,
  SessionActions,
} from "../store";
import { useNavigate } from "react-router-dom";

import "./styles/login.css";
import Form, { InputProps } from "../partials/Form/Form";

export default function LogInPage() {
  /**
   * *true* = loggin
   * *false* = SingUp
   */
  const [state, setState] = useState(true);
  const [loading, setLoading] = useState(false);
  const [formData, updateFormData] = useState<Partial<UserRegistrationRaw>>({});
  const navigation = useNavigate();
  const dispatch = useStoreDispatch();
  const sessionData = useAppSelector((st: RootState) => st.session);

  const singUpConf: (InputProps[] | InputProps)[] = [
    {
      label: "Full Name",
      type: "text",
      required: true,
      onChange: (_, value) => {
        updateFormData({ ...formData, fullName: value });
      },
    },
    {
      label: "Born Date",
      type: "date",
      required: true,
      onChange: (_, value) => {
        updateFormData({ ...formData, bornDate: value });
      },
    },
    [
      {
        type: "number",
        name: "travelBudget",
        required: true,
        label: "Budget",
        onChange: (_, value) => {
          updateFormData({ ...formData, travelBudget: parseInt(value) });
        },
      },
      {
        type: "number",
        required: true,
        label: "Frequency",
        onChange: (_, value) => {
          updateFormData({ ...formData, travelBudget: parseInt(value) });
        },
      },
    ],
    [
      {
        type: "password",
        label: "Passwrod",
        required: true,
        onChange: (_, value) => {
          updateFormData({ ...formData, password: value });
        },
      },
      {
        type: "number",
        label: "Card Id",
        required: true,
        onChange: (_, val) => {
          updateFormData({ ...formData, cardId: val });
        },
      },
    ],
    [
      {
        type: "submit",
        label: "SignUp",
      },
      {
        type: "button",
        label: "Sing In",
        onClick: () => {
          setState(true);
        },
      },
    ],
  ];
  const singInConfig: (InputProps[] | InputProps)[] = [
    {
      type: "number",
      label: "Card Id",
      required: true,
      onChange: (_, val) => {
        updateFormData({ ...formData, cardId: val });
      },
    },
    {
      type: "password",
      label: "Passwrod",
      required: true,
      onChange: (_, val) => {
        updateFormData({ ...formData, password: val });
      },
    },
    [
      {
        type: "submit",
        label: "Sign In",
      },
      {
        type: "button",
        label: "Sing Up",
        onClick: () => {
          console.log("click");
          
          setState(false);
        },
      },
    ],
  ];

  useEffect(() => {
    if (sessionData) {
      navigation(-1);
    }
  }, []);

  const handleSession: FormEventHandler<HTMLFormElement> = (e) => {
    e.preventDefault();
    setLoading(false);
    if (state) {
      LogIn({ cardId: formData.cardId!, password: formData.password! })
        .then((e) => {
          dispatch(SessionActions.logIn(e));
          setLoading(false);
          navigation(-1);
        })
        .catch(() => alert("Opps! your password or cardid do not match"));

      return;
    }
    if (
      formData.bornDate &&
      formData.cardId &&
      formData.password &&
      formData.travelBudget &&
      formData.travelFrequency &&
      formData.fullName
    ) {
      const data: UserDataRegistration = {
        password: formData.password,
        tourist: {
          idCard: formData.cardId,
          fullName: formData.fullName,
          bornDate: formData.bornDate,
          travelBudget: formData.travelBudget,
          travelFrequency: formData.travelBudget,
        },
      };
      SignUp(data).then((d) => {
        dispatch(SessionActions.logIn(d));
        setLoading(false);
        navigation(-1);
      });
    }
  };

  return (
    <div className="loggin-container">
      <div className="log-title">
        <h1>{state ? "Sign In" : "Sign Up"}</h1>
      </div>
      {loading ? (
        <Loading width="200px" />
      ) : (
        <Form
          onSubmit={handleSession}
          data={state ? singInConfig : singUpConf}
        />
      )}
    </div>
  );
}
