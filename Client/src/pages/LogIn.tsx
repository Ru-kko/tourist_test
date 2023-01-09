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
type ParseFn<T> = (data: { [K in keyof Partial<T>]: T[K] }) => void;

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
        <Loading width="10rem" />
      ) : (
        <form className="loggin-form" onSubmit={handleSession}>
          {state ? (
            <LongginForm
              fn={(inf) => updateFormData({ ...formData, ...inf })}
            />
          ) : (
            <RegisterForm
              fn={(inf) => updateFormData({ ...formData, ...inf })}
            />
          )}
          <div className="log-horizontal-row">
            <input
              type="submit"
              className="submit-btn"
              value={state ? "SignIn" : "SignUp"}
            />
            or
            <button
              onClick={() => {
                setState(!state);
                updateFormData({});
              }}
            >
              {!state ? "SignIn" : "SignUp"}
            </button>
          </div>
        </form>
      )}
    </div>
  );
}

const LongginForm = (props: { fn: ParseFn<User> }) => (
  <>
    <div>
      <input
        type="number"
        name="cardId"
        required
        onChange={(e) => props.fn({ [e.target.name]: e.target.value })}
      />
      <div className="cut" />
      <span>Card Id</span>
    </div>
    <div>
      <input
        type="password"
        name="password"
        required
        onChange={(e) => props.fn({ [e.target.name]: e.target.value })}
      />
      <div className="cut" />
      <span>Password</span>
    </div>
  </>
);

const RegisterForm = (props: { fn: ParseFn<User> }) => (
  <>
    <div>
      <input
        type="text"
        name="fullName"
        required
        onChange={(e) => props.fn({ [e.target.name]: e.target.value })}
      />
      <div className="cut" />
      <span>Full Name</span>
    </div>
    <div>
      <input
        type="date"
        name="bornDate"
        required
        onChange={(e) => props.fn({ [e.target.name]: e.target.value })}
      />
      <div className="cut" />
      <span>Born Date</span>
    </div>
    <div className="log-horizontal-row">
      <div>
        <input
          type="number"
          name="travelFrequency"
          required
          onChange={(e) => props.fn({ [e.target.name]: e.target.value })}
        />
        <div className="cut" />
        <span>TravelFrequecy</span>
      </div>
      <div>
        <input
          type="number"
          name="travelBudget"
          required
          onChange={(e) => props.fn({ [e.target.name]: e.target.value })}
        />
        <div className="cut"></div>
        <span>TravelBudget</span>
      </div>
    </div>
    <div className="log-horizontal-row">
      <div>
        <input
          type="number"
          name="cardId"
          required
          onChange={(e) => props.fn({ [e.target.name]: e.target.value })}
        />
        <div className="cut" />
        <span>CardId</span>
      </div>
      <div>
        <input
          type="password"
          name="password"
          required
          onChange={(e) => props.fn({ [e.target.name]: e.target.value })}
        />
        <div className="cut" />
        <span>Password</span>
      </div>
    </div>
  </>
);
