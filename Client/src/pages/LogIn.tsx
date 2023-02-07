import { FormEventHandler, useEffect, useState } from "react";
import { UserDataRegistration, UserRegistrationRaw } from "../typings/server";
import { LogIn, SignUp } from "../services/session";
import Loading from "../partials/loading/Loading";
import {
  RootState,
  useAppSelector,
  useStoreDispatch,
  SessionActions,
} from "../store";
import { useNavigate } from "react-router-dom";
import Form, { InputProps } from "../partials/Form/Form";

import "./styles/login.css";

export default function LogInPage() {
  const navigation = useNavigate();
  const dispatch = useStoreDispatch();
  /**
   * *true* = loggin
   * *false* = SingUp
   */
  const [state, setState] = useState(false);
  const [loading, setLoading] = useState(false);
  const [formData, updateFormData] = useState<Partial<UserRegistrationRaw>>({});
  const sessionData = useAppSelector((st: RootState) => st.session);

  const update = (key: string, val: string) => {
    updateFormData({ ...formData, [key]: val });
  };

  const singInConfig: (InputProps[] | InputProps)[] = [
    {
      type: "number",
      label: "Card Id",
      name: "cardId",
      required: true,
      onChange: update,
    },
    {
      type: "password",
      label: "Passwrod",
      name: "password",
      required: true,
      onChange: update,
    },
    [
      {
        type: "submit",
        label: "SignIn",
      },
      {
        type: "button",
        label: "SingUp",
        onClick: () => setState(false),
      },
    ],
  ];
  const singUpConf: (InputProps[] | InputProps)[] = [
    [
      {
        label: "Name",
        type: "text",
        name: "name",
        required: true,
        onChange: update,
      },
      {
        label: "Last Name",
        type: "text",
        name: "lastName",
        required: true,
        onChange: update,
      },
    ],

    {
      label: "Born Date",
      type: "date",
      name: "bornDate",
      required: true,
      onChange: update,
    },
    [
      {
        type: "number",
        name: "travelBudget",
        required: true,
        label: "Budget",
        onChange: update,
      },
      {
        type: "number",
        label: "Frequency",
        name: "travelFrequency",
        required: true,
        onChange: update,
      },
    ],
    [
      {
        type: "password",
        label: "Passwrod",
        name: "password",
        required: true,
        onChange: update,
      },
      {
        type: "number",
        label: "Card Id",
        name: "cardId",
        required: true,
        onChange: update,
      },
    ],
    [
      {
        label: "SignUp",
        type: "submit",
      },
      {
        type: "button",
        label: "SingIn",
        onClick: () => {
          setState(true);
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
      formData.name &&
      formData.lastName
    ) {
      const data: UserDataRegistration = {
        password: formData.password,
        tourist: {
          idCard: formData.cardId,
          name: formData.name,
          lastName: formData.lastName,
          bornDate: formData.bornDate,
          travelBudget: parseInt(formData.travelBudget + ""),
          travelFrequency: parseInt(formData.travelBudget + ""),
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
