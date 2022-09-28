import { Injectable } from '@angular/core';
import { BaseService, WrapperGetData, WrapperPostPutData } from '@common';
import { ApiService } from '@eng-ds/ng-core';
import { HttpParams } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { SearchProtocol } from '../models/searchProtocol.model';
import { I18nService } from '@eng-ds/ng-core';
import { ToastrService } from 'ngx-toastr';
import { ErrorMessage } from '@eng-ds/ng-toolkit';
import { ProtocolState } from '../../enum/protocol-state.enum';

@Injectable()
export class ProtocolService extends BaseService {
    constructor(protected api: ApiService,
        private i18nService: I18nService,
        private toastrService: ToastrService,) {
        super(api);
        
    }

    private _getProtocols(
        params: HttpParams
    ): Observable<WrapperGetData<SearchProtocol>> {
        return this.api
            .request<WrapperGetData<SearchProtocol>>(
                'getProtocols',
                null,
                params
            )
            .pipe(
                map((result: WrapperGetData<SearchProtocol>) => {
                    const arr = [];
                    // tslint:disable-next-line:forin
                    for (const k in result.list) {
                        arr.push(SearchProtocol.fromDto(result.list[k]));
                    }

                    return {
                        list: arr,
                        totalNumber: result.totalNumber
                    } as WrapperGetData<SearchProtocol>;
                })
            );
    }

    getProtocols(
        criteriaDto: any,
        value: string = ''
    ): Observable<WrapperGetData<SearchProtocol>> {
        let params = this.getPaginationParamsFromCriteria(criteriaDto);
        if (value !== '' && value !== null) {
            params = params.set('value', value);
        }
        return this._getProtocols(params);
    }

    getPaginationParamsFromCriteria(criteria: any): HttpParams {
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


    updateProtocolState(
        protocol: SearchProtocol
    ): Observable<WrapperPostPutData> {
        const protocolId = protocol.id;
        return this.api.request('updateProtocolState', null, null, {
            protocolId: protocolId
        });
    }

    protocolSaveComplete(protocol: SearchProtocol): void {
        this.toastrService.info(
            this.i18nService.translate(
                'PROTOCOLS.TOASTR.SUCCESS_CONFIRM.TEXT'
            ),
            this.i18nService.translate(
                'PROTOCOLS.TOASTR.SUCCESS_CONFIRM.TITLE'
            )
        );
        protocol.protocolState = ProtocolState.PROTINCORSO;
    }

    saveError(error: ErrorMessage): void {
        this.toastrService.error(
            this.i18nService.translate(
                'PROTOCOLS.TOASTR.ERROR.TEXT'
            ),
            this.i18nService.translate(
                'PROTOCOLS.TOASTR.ERROR.TITLE'
            )
        );
    }
}
