export function getPagingRange(n:number, total:number) {
  let length = 5;
  
  if (length > total) length = total;

  let start = n - Math.floor(length / 2);
  start = Math.max(start, 1);
  start = Math.min(start, 1+ total - length);
 
  return Array.from({length: length}, (_, i) => start + i);
}