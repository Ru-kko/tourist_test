export function useHandleAsync<T>(setter: (d: T | null) => void) {
  return (fn: Promise<T>, err?: (er: Error) => void) => {
    setter(null);
    fn.then((d) => {
      setter(d);
    }).catch(err);
  };
}
