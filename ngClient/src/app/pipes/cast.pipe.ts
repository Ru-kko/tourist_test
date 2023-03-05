import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'as',
  pure: true,
  standalone: true
})
export class CastPipe implements PipeTransform {

  transform<T>(value: any): T {
    return value as T;
  }

}
