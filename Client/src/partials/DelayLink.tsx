import { MouseEventHandler, useEffect } from "react";
import { Link, LinkProps, useLocation, useNavigate } from "react-router-dom";

interface IProps {
  delayTime: number;
}

export function DelayLink(porps: IProps & LinkProps) {
  const location = useLocation();
  const navigate = useNavigate();
  let timeOut: number;

  useEffect(() => {
    return () => {
      if (timeOut) {
        clearTimeout(timeOut);
      }
    } 
  }, []);

  const handleClick: MouseEventHandler<HTMLAnchorElement> = (e) => {
    porps.onClick?.(e);

    if (location.pathname === porps.to) return;

    if (e.defaultPrevented) return;

    e.preventDefault();

    timeOut = setTimeout(() => {
      navigate(porps.to);
    }, porps.delayTime);
  };

  return <Link {...porps} onClick={handleClick}></Link>;
}
