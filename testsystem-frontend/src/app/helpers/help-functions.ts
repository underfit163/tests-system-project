import {format, parse} from "date-fns";

export function parseDateToString(date: Date):string {
  return format(date, 'yyyy-MM-dd');
}

export function parseStringToDate(date: string):Date {
  return parse(date, 'yyyy-MM-dd', new Date());
}
