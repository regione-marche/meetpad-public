import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { ApiService } from '@eng-ds/ng-core';

import { BaseService, WrapperGetData } from '@common';

import { MediaLibrary } from '../model/media-library.model';
import { HttpParams } from '@angular/common/http';

@Injectable()
export class MediaLibraryService extends BaseService {
    constructor(protected api: ApiService) {
        super(api);
    }

    private _list(
        params: HttpParams
    ): Observable<WrapperGetData<MediaLibrary>> {
        return this.api
            .request<WrapperGetData<MediaLibrary>>(
                'getMediaLibrary',
                null,
                params
            )
            .pipe(
                map((result: WrapperGetData<MediaLibrary>) => {
                    const arr = [];
                    if (result && result.list) {
                        for (const k in result.list) {
                            if (result.list[k]) {
                                arr.push(MediaLibrary.fromDto(result.list[k]));
                            }
                        }
                    }
                    // tslint:disable-next-line:forin
                    return {
                        list: arr,
                        totalNumber: result.totalNumber
                    } as WrapperGetData<MediaLibrary>;
                })
            );
    }

    list(
        criteriaDto: any,
        value: string = ''
    ): Observable<WrapperGetData<MediaLibrary>> {
        let params = this._getPaginationParamsFromCriteria(criteriaDto);
        params = params.set('search', value);
        return this._list(params);
    }

    private _getPaginationParamsFromCriteria(criteria: any): HttpParams {
        const query = {};
        if (criteria['sort']) {
            const [col, direction] = criteria['sort'].split(',');
            query['orderColumn'] = col;
            query['orderDirection'] = direction;
        }
        if (criteria['size']) {
            query['recordForPage'] = criteria['size'];
        }
        if (criteria['page'] === 0 || criteria['page']) {
            query['pageNumber'] = criteria['page'];
        }

        delete criteria['sort'];
        delete criteria['page'];
        delete criteria['size'];

        const params = new HttpParams({
            fromObject: query
        });
        return params;
    }
}
