import { Injectable } from '@angular/core';
import { HttpParams, HttpRequest, HttpHeaders, HttpClient, HttpEventType, HttpResponse } from '@angular/common/http';
import { Observable, Subject, of } from 'rxjs';
import { ApiService, ConfigService } from '@eng-ds/ng-core';
import { BaseService, BaseFile, ComboBox, WrapperGetData, User, FileType } from '@common';
import { Documentation } from '../../models/documentation';
import { catchError, map } from 'rxjs/operators';
import { DateModel } from '@eng-ds/ng-toolkit';

@Injectable({
    providedIn: 'root'
})
export class DocumentsService extends BaseService {
    constructor(
        protected api: ApiService,
        private configService: ConfigService,
        private http: HttpClient,
    ) {
        super(api);
    }
    progress$ = new Subject<number>();
    getDocuments(
        conferenceId: string,
        allVisibility: boolean
    ): Observable<Documentation> {
        let params = new HttpParams();
        params = params.set('key', allVisibility.toString());
        return this.api.request<Documentation>('getDocuments', null, params, {
            conferenceId
        });
    }

    syncronizeDocuments(
        conferenceId: string,
        allVisibility: boolean
    ): Observable<Documentation> {

        let params = new HttpParams();
        params = params.set('key', allVisibility.toString());
        return this.api.request<Documentation>('syncronizeDocuments', null, params, {
            conferenceId
        });
    }

    deleteDocumentList(
        idDocumentList : Number[]
    ): Observable<Documentation> {
        return this.api.request<Documentation>('deleteDocumentList', { "idDocumentList": idDocumentList }, null, null);
    }

    unlockSignatureFile(
        documentId: string
    ): Observable<any> {
        return this.api.request<Documentation>('unlockSignatureFile', null, null, {
            documentId
        });
    }
    
    unlockSignatureFileWithCallabck(documentId: number, callbackbody: string, padesCades : boolean = false,
        calamaioRemota : string = ""): Observable<any> {
        return this.api.request<any>('unlockSignatureFileWithCallabck', { 
            "idDocumento": documentId,
            "fk_tipo_firma":3,
            "callbackbody": btoa(callbackbody),
            "calamaioRemota" : calamaioRemota,
            "padesCades": padesCades,
        }, null, {
            documentId
        });
    }

    getCalamaioStatus(): Observable<any> {
        return this.api.request<any>('getCalamaioStatus');
    }

    getCalamaioFillxml(documentId: number, padesCades : boolean, calamaioRemota : string = null,
        fileName : string = null): Observable<any> {
        return this.api.request<any>('postCalamaioFillxml', {
            "idDocumento": documentId,
            "fk_tipo_firma":3,
            "padesCades": padesCades,
            "calamaioRemota" : calamaioRemota,
            "fileName": fileName,
        });
    }

    postCalamaioSign(xmlDocument: string): Observable<any> {
    
        const url = this.configService.getApiConfig('postCalamaioSign').url;

        // create a http-post request and pass the XML content
        const req = new HttpRequest('POST', url, xmlDocument, {
            headers: new HttpHeaders({'Content-Type': 'application/xml'}) ,
            responseType: "text"
        });

        const data$ = new Subject<any>();

        this.http
            .request(req)
            .pipe( catchError(res => {

                    if(res.statusText === "OK" && res.body.indexOf('Success') !== -1){
                        return of(res);
                    }else{          
                        return of(null);
                    }
                    
                })
            )
            .subscribe(result => {
                if(result !== null && result.body){
                    data$.next(result.body);
                    data$.complete();   
                }else if(result === null){
                    data$.next(null);
                    data$.complete();
                }            
            });

        return data$.asObservable();
    }
    getManagerSignatures(conferenceId: string): Observable<User[]> {
       return this.api.request<WrapperGetData<User>>('getManagerSignatures', null, null, {conferenceId}).pipe(map(response => response.list));
    }

     getManagerSignaturesComboBox(conferenceId: string): ComboBox<string>[] {
        const comboBox: ComboBox<string>[] = [];
        comboBox.push({ key: '-1', value: 'Scegli un firmatario' });
        this.getManagerSignatures(conferenceId).subscribe(participant => {
            participant.forEach(p =>
            {
                comboBox.push({
                    key: p.idUser,
                    value: p.name + ' ' + p.lastname
                })
            })
        });
        return comboBox;
    }
    uplaoadAndMarkDocumentToSign(
        conferenceId: string,
        fileData: any,
        file: File,
        type: FileType,
        managerCST: string
    ): Observable<BaseFile> {
        const url = this.configService
            .getApiConfig('markDocumentToSign')
            .url.replace('{idConference}', conferenceId);
        const formData: FormData = new FormData();

        formData.append('fileComplient', fileData.fileComplient);
        if(file) {
            formData.append('file', file, file.name);
            formData.append('name ', file.name);
        }
        formData.append('type ', type);

        switch (type) {

            case FileType.INDICTION:
                formData.append(
                    'visibility',
                    JSON.stringify(fileData.visibility)
                );
                if (
                    fileData.protocolNumber &&
                    fileData.protocolNumber != 'undefined'
                ) {
                    formData.append('protocolNumber', fileData.protocolNumber);
                }
                if (
                    fileData.inventoryNumber &&
                    fileData.inventoryNumber != 'undefined'
                ) {
                    formData.append(
                        'inventoryNumber',
                        fileData.inventoryNumber
                    );
                }
                if (
                    (fileData.protocolDate as DateModel).date &&
                    (fileData.protocolDate as DateModel).date !== undefined
                ) {
                    formData.append(
                        'protocolDate',
                        (fileData.protocolDate as DateModel).date.format(
                            'DD-MM-YYYY'
                        )
                    );
                }
                if(managerCST !== undefined){
                    formData.append('managerCST', managerCST);
                }
                break;
        }

        // create a http-post request and pass the form
        // tell it to report the upload progress
        const req = new HttpRequest('POST', url, formData, {
            reportProgress: false
        });

        const data$ = new Subject<BaseFile>();

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
            .subscribe(event => {
                if (event.type === HttpEventType.UploadProgress) {
                    // calculate the progress percentage
                    const percentDone = Math.round(
                        (100 * event.loaded) / event.total
                    );

                    // pass the percentage into the progress-stream
                    this.progress$.next(percentDone);
                } else if (event instanceof HttpResponse) {
                    // Close the progress-stream if we get an answer form the API
                    // The upload is complete
                    // this.progress$.complete();
                    data$.next(event.body as BaseFile);
                    data$.complete();
                }
            });

        return data$.asObservable();
    }

    getPermissionsToSign(conferenceId: string): Observable<boolean> {
        const data$ = new Subject<boolean>();
        this.api.request<boolean>('getPermissionsToSign', null, null, {conferenceId}).
        pipe(     
            catchError(err => {
            data$.error(err);
            data$.complete();
            return of(err);
        })
        ).subscribe(res => {
            data$.next(res as boolean);
            data$.complete();
        });
        return data$.asObservable();
     }
}
