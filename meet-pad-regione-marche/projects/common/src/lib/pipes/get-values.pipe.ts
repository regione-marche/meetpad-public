import { Pipe, PipeTransform } from '@angular/core';

@Pipe({ name: 'getValues' })
export class GetValuesPipe implements PipeTransform {
    transform(map: Map<any, any>): any[] {
        const ret = [];
        if (map) {
            map.forEach((val, key) => {
                ret.push({
                    key,
                    val
                });
            });
        }
        return ret;
    }
}
