import { Injectable } from '@angular/core';
import { Protocol } from '../../models/protocol/protocolling.model';
import { Observable,Subject } from 'rxjs';
import { HttpParams, HttpRequest, HttpClient } from '@angular/common/http';
import { BaseService, WrapperGetData, WrapperPostPutData,LoaderService } from '@common';
import { ApiService, ConfigService,I18nService } from '@eng-ds/ng-core';
import { map } from 'rxjs/operators';
import { ToastrService } from 'ngx-toastr';
import { ProtocolState } from '@app/core/enums/protocol-state.enum';
import { ErrorMessage } from '@eng-ds/ng-toolkit';

@Injectable({
    providedIn: 'root'
})
export class ProtocollingService extends BaseService {

    constructor(
        protected api: ApiService,
        private configService: ConfigService,
        private http: HttpClient,
        private loaderService: LoaderService,
        private toastrService: ToastrService,
        public i18nService: I18nService,
    ) {
        super(api);
    } 

    _getProtocollings(
        conferenceId: string,
        params: HttpParams
    ): Observable<WrapperGetData<Protocol>> {
        return this.api
            .request<WrapperGetData<Protocol>>('getProtocollings', null, params, {
                conferenceId
            })
            .pipe(
                map((result: WrapperGetData<Protocol>) => {
                    const arr = [];
                    // tslint:disable-next-line:forin
                    for (const k in result.list) {
                        arr.push(Protocol.fromDto(result.list[k]));
                    }
                    return {
                        list: arr,
                        totalNumber: result.totalNumber
                    } as WrapperGetData<Protocol>;
                })
            );
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

    getProtocollings(
        criteriaDto: any,
        conferenceId: string,
        searchQuery: boolean = false
    ): Observable<WrapperGetData<Protocol>> {
        let params = this.getPaginationParamsFromCriteria(criteriaDto);
        // passare searchQuery
        params = params.set('key', searchQuery.toString());
        return this._getProtocollings(conferenceId, params);
    }

    updateProtocolState(
        protocol: Protocol
    ): Observable<WrapperPostPutData> {
        const protocolId = protocol.id;
        return this.api.request('updateProtocolState', null, null, {
            protocolId: protocolId
        });
    }

    protocolSaveComplete(protocol: Protocol): void {
        this.toastrService.info(
            this.i18nService.translate(
                'CONFERENCE.WIZARD.PROTOCOL.TOASTR.SUCCESS_CONFIRM.TEXT'
            ),
            this.i18nService.translate(
                'CONFERENCE.WIZARD.PROTOCOL.TOASTR.SUCCESS_CONFIRM.TITLE'
            )
        );
        protocol.protocolState = ProtocolState.PROTINCORSO;
    }

    saveError(error: ErrorMessage): void {
        this.toastrService.error(
            this.i18nService.translate(
                'CONFERENCE.WIZARD.PROTOCOL.TOASTR.ERROR.TEXT'
            ),
            this.i18nService.translate(
                'CONFERENCE.WIZARD.PROTOCOL.TOASTR.ERROR.TITLE'
            )
        );
    }
} 