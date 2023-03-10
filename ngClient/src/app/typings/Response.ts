export interface PageResponse<T> {
  content: T[];
  lenght: number;
  totalPages: number;
}

export function EmptyResponse<T>(): PageResponse<T> {
  return {
    content: [] as T[],
    lenght: 0,
    totalPages: 0
  }
}
