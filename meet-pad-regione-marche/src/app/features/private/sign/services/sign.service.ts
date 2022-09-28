import { Injectable } from '@angular/core';
import { SearchSign } from '../models/search-sign.models';
import { BaseFile, BaseService, WrapperGetData, WrapperPostPutData } from '@common';
import { Observable, of, Subject } from 'rxjs';
import { Sign } from '../sign';
import { SIGNS } from '../mock-signs';
import { HttpClient, HttpEvent, HttpEventType, HttpParams, HttpRequest, HttpResponse } from '@angular/common/http';
import { catchError, map } from 'rxjs/operators';
import { ApiService, ConfigService } from '@eng-ds/ng-core';
import { ActionForm } from '@app/core';

@Injectable({
  providedIn: 'root'
})
export class SignService extends BaseService {
  progress$ = new Subject<number>();
    constructor(
      protected api: ApiService,
      protected http: HttpClient,
      private configService: ConfigService
      ) {
      super(api);
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
  signSearch(
      criteriaDto: any
  ): Observable<WrapperGetData<SearchSign>> {
      let params = this.getPaginationParamsFromCriteria(criteriaDto);

      params = params.set('conferenceType', '');
      params = params.set('state', '');
      params = params.set('fiscalCode', '');
      params = params.set('requestReference', '');

      params = params.set('province', '');
      params = params.set('city', '');
      return this._advancedSearch(params);
  }

  private _advancedSearch(
    params: HttpParams
  ): Observable<WrapperGetData<SearchSign>> {
    return this.api
        .request<WrapperGetData<SearchSign>>(
            'getAllDocumentInSign',
            null,
            params
        )
        .pipe(
            map((result: WrapperGetData<SearchSign>) => {
                const arr = [];
                // tslint:disable-next-line:forin
                for (const k in result.list) {
                    arr.push(SearchSign.fromDto(result.list[k]));
                }
                return {
                    list: arr,
                    totalNumber: result.totalNumber
                } as WrapperGetData<SearchSign>;
            })
        );
  }

  documentSignSearch(
    criteriaDto: any,
    method: string
  ): Observable<WrapperGetData<SearchSign>> {
    let params = this.getPaginationParamsFromCriteria(criteriaDto);

    return this._documentsSearchByStatus(params, method)
  }

  private _documentsSearchByStatus(
  params: HttpParams,
  method: string
  ): Observable<WrapperGetData<SearchSign>> {
  return this.api
      .request<WrapperGetData<SearchSign>>(
          method,
          null,
          params
      )
      .pipe(
          map((result: WrapperGetData<SearchSign>) => {
              const arr = [];
              // tslint:disable-next-line:forin
              for (const k in result.list) {
                  arr.push(SearchSign.fromDto(result.list[k]));
              }
              return {
                  list: arr,
                  totalNumber: result.totalNumber
              } as WrapperGetData<SearchSign>;
          })
      );
  }

  rejectDocumentList(
    idDocumentList : Number[]
  ): Observable<SearchSign> {
    let params = new HttpParams();
    params.set('idResponsabileCST', '1');
    params.set('idsDocumento', '40,41,2');

    return this.api.request<SearchSign>('rejectDocumentList',{ "idDocumentList": idDocumentList }, null,null);
  }
  signDocumentList(
    idDocumentList : Number[],
    fileData: any
  ): Observable<SearchSign> {
    return this.api.request<SearchSign>('signDocumentList',{ 
                                        "remoteSignature": fileData.remoteSignature, 
                                        "calamaioRemoteUsername": fileData.calamaioRemoteUsername, 
                                         "calamaioRemotePassword": fileData.calamaioRemotePassword, 
                                         "calamaioRemoteOTP": fileData.calamaioRemoteOTP,
                                         "calamaioRemoteDomain": fileData.calamaioRemoteDomain, 
                                         "padesCades" : fileData.padesCades,
                                         "idDocumentList": idDocumentList}, null, null);
  }
  signDocumentList_(
    idDocumentList : any[],
    fileData: any
): Observable<SearchSign> {
    const url = this.configService.getApiConfig('signDocumentList').url;
    const formData: FormData = new FormData();

    formData.append('remoteSignature', fileData.remoteSignature);
    formData.append('calamaioRemoteUsername', fileData.calamaioRemoteUsername);
    formData.append('calamaioRemotePassword', fileData.calamaioRemotePassword);
    formData.append('calamaioRemoteOTP', fileData.calamaioRemoteOTP);
    formData.append('calamaioRemoteDomain', fileData.calamaioRemoteDomain);
    formData.append('calamaioRemoteDocumentId', fileData.calamaioRemoteDocumentId);
    formData.append('padesCades', fileData.padesCades);
    for(var j=0;idDocumentList && j < idDocumentList.length; j++){
      let id = idDocumentList[j];
      formData.append("idDocumentList", id);
    }
   

    // create a http-post request and pass the form
    // tell it to report the upload progress
    const req = new HttpRequest('PATCH', url, formData, {
        reportProgress: false
    });

    const data$ = new Subject<SearchSign>();

    // send the http-request and subscribe for progress-updates
    this.http
        .request(req)
        .pipe(
          catchError(err => {
              data$.error(err);
              data$.complete();
              return of(err);
          })
      )
      .subscribe((__event: HttpEvent<WrapperPostPutData>) => {
          if (__event.type === HttpEventType.UploadProgress) {
              // calculate the progress percentage
              const percentDone = Math.round(
                  (100 * __event.loaded) / __event.total
              );

              // pass the percentage into the progress-stream
              this.progress$.next(percentDone);
          } else if (__event instanceof HttpResponse) {
              // Close the progress-stream if we get an answer form the API
              // The upload is complete
              // this.progress$.complete();
           //   data$.next(__event.body);
              data$.complete();
          }
      });

  // return the progress.observables
  return data$.asObservable();
        
}
  
}


