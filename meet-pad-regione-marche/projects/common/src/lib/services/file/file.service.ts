import { Injectable } from '@angular/core';

import {
    HttpRequest,
    HttpClient,
    HttpEventType,
    HttpResponse
} from '@angular/common/http';

import { Observable, Subject, of } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';

import { ToastrService } from 'ngx-toastr';

import { DateModel } from '@eng-ds/ng-toolkit';

import {
    ApiService,
    ConfigService,
    PathParams,
    I18nService
} from '@eng-ds/ng-core';
import { LoaderService } from '../loader/loader.service';
import { FileType } from '../../enums/file-type.enum';
import { BaseFile } from '../../models/base-file.model';
import { OnlyOffice } from '../../models/only-office.model';
import { SectionLoading } from '../../enums/section-loading.enum';

@Injectable()
export class FileService {
    progress$ = new Subject<number>();

    // Subject a cui iscriversi per vedere tutti gli upload
    uploads$: Subject<
        Map<string, Subject<{ name: string; progress: number }>>
    > = new Subject<Map<string, Subject<{ name: string; progress: number }>>>();
    // Mappa di uploadProgress
    filesUpload$: Map<
        string,
        Subject<{ name: string; progress: number }>
    > = new Map();

    downloads$: Subject<
        Map<string, Subject<{ name: string; progress: number }>>
    > = new Subject<Map<string, Subject<{ name: string; progress: number }>>>();
    // Mappa di downloadProgress
    filesDownload$: Map<
        string,
        Subject<{ name: string; progress: number }>
    > = new Map();

    numberFiles: Subject<number> = new Subject();
    isUploadCompleted: boolean = false;
    isEnableProgressBar: boolean = false;
    isLock : boolean = false;

    totalLenghtFile : any;

    constructor(
        private api: ApiService,
        private http: HttpClient,
        private loaderService: LoaderService,
        private toastr: ToastrService,
        private i18nService: I18nService,
        private configService: ConfigService
    ) {}

    get loading(): Observable<boolean> {
        return this.loaderService.getLoading$(
            SectionLoading.INDICTION_FILE_MODAL
        );
    }

    get loadingForShared(): Observable<boolean> {
        return this.loaderService.getLoading$(SectionLoading.SHARED_FILE_MODAL);
    }

    get loadingForSignature(): Observable<boolean> {
        return this.loaderService.getLoading$(SectionLoading.SIGNATURE_FILE_MODAL);
    }

    getUploads(): void {
        this.uploads$.next(this.filesUpload$);
    }

    getUploadCompleted(): boolean {
        return this.isUploadCompleted;
    }

    resetFileUploadStatus(){
        this.uploads$ = new Subject<Map<string, Subject<{ name: string; progress: number }>>>();
    }

    deleteFile(id: string): Observable<any> {
        return this.api.request('deleteFile', null, null, { id });
    }

    enableProgressBar(){
        this.isEnableProgressBar = true;
    }

    disableProgressBar(){
        this.isEnableProgressBar = false;
    }

    enableLock(){
        this.isLock = true;
    }

    disableLock(){
        this.isLock = false;
    }

    edit(fileData: any, type: FileType, file: File = null): Observable<any> {
        const url = this.configService
            .getApiConfig('editFile')
            .url.replace('{id}', fileData.id);
        const formData: FormData = new FormData();
        formData.append('file', file);
        formData.append('name ', fileData.name);
        formData.append('type ', type);

        switch (type) {
            case FileType.INTERETION:
                formData.append(
                    'visibility',
                    JSON.stringify(fileData.visibility)
                );
                formData.append(
                    'category',
                    fileData.category ? fileData.category.key : null
                );
                break;
            case FileType.SHARED:
                formData.append(
                    'visibility',
                    JSON.stringify(fileData.visibility)
                );
                formData.append('url', fileData.url ? fileData.url : null);
                break;
            case FileType.ADDITIONAL:
            case FileType.PRELIMINARY:
                formData.append(
                    'visibility',
                    JSON.stringify(fileData.visibility)
                );
                formData.append(
                    'category',
                    fileData.category ? fileData.category.key : null
                );
                if (fileData.cityReference) {
                    formData.append('cityReference', fileData.cityReference);
                }
                break;
            case FileType.SIGNATURE:
                formData.append(
                    'visibility',
                    JSON.stringify(fileData.visibility)
                );
                break;
        }

        // create a http-post request and pass the form
        // tell it to report the upload progress
        const req = new HttpRequest('PUT', url, formData, {
            reportProgress: true
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
                    data$.next(event.body as any);
                    data$.complete();
                }
            });

        // return the progress.observables
        return data$.asObservable();
    }

    upload(
        conferenceId: string,
        fileData: any,
        file: File,
        type: FileType,
        enableProgressBar: boolean = false
    ): Observable<BaseFile> {
        const url = this.configService
            .getApiConfig('uploadFile')
            .url.replace('{conferenceId}', conferenceId);
        const formData: FormData = new FormData();

        formData.append('fileComplient', fileData.fileComplient);
        if(file) {
            formData.append('file', file, file.name);
            formData.append('name ', file.name);
        }
        formData.append('type ', type);

        switch (type) {

            case FileType.SIGNATURE:
                formData.append('padesCades', fileData.padesCades);
                formData.append('signed', fileData.signed);
                formData.append(
                    'signers',
                    JSON.stringify(fileData.visibility)
                );
                
                if(fileData.remoteSignature) {
                    formData.append('remoteSignature', fileData.remoteSignature);
                    formData.append('calamaioRemoteUsername', fileData.calamaioRemoteUsername);
                    formData.append('calamaioRemotePassword', fileData.calamaioRemotePassword);
                    formData.append('calamaioRemoteOTP', fileData.calamaioRemoteOTP);
                    formData.append('calamaioRemoteDomain', fileData.calamaioRemoteDomain);
                    formData.append('calamaioRemoteDocumentId', fileData.calamaioRemoteDocumentId);
                }

                if(fileData.calamaioSignature) {
                    formData.append('calamaioSignature', fileData.calamaioSignature);
                    formData.append('calamaioRemoteDocumentId', fileData.calamaioRemoteDocumentId);
                }
            case FileType.INTERETION:
            case FileType.SHARED:
                formData.append(
                    'visibility',
                    JSON.stringify(fileData.visibility)
                );
                break;
            case FileType.ADDITIONAL:
            case FileType.PRELIMINARY:
                formData.append(
                    'visibility',
                    JSON.stringify(fileData.visibility)
                );
                formData.append(
                    'category',
                    fileData.category ? fileData.category.key : null
                );
                if (fileData.cityReference) {
                    formData.append('cityReference', fileData.cityReference);
                }
                break;
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
                break;
        }

        // create a http-post request and pass the form
        // tell it to report the upload progress
        const req = new HttpRequest('POST', url, formData, {
            reportProgress: true
        });

        if(enableProgressBar === true)
            this.enableProgressBar();
        else
            this.disableProgressBar();

        const data$ = new Subject<BaseFile>();
        const _p$ = new Subject<{ name: string; progress: number }>();
        var fileNameProgress = '';
        if(file != null && file.name)
            fileNameProgress = file.name;
        const name = fileNameProgress.concat(Math.random().toString());
       
        if(this.isEnableProgressBar){
            this.filesUpload$.set(name, _p$);
            this.progress$.next(0);
            this.uploads$.next(this.filesUpload$);
        }
        this.isUploadCompleted = false;
        // send the http-request and subscribe for progress-updates
        this.http
            .request(req)
            .pipe(
                catchError(err => {
                    if(this.isEnableProgressBar){
                        _p$.error(err);
                        this.filesUpload$.delete(name);
                    }
                    data$.error(err);
                    data$.complete();
                    return of(err);
                })
            )
            .subscribe(event => {
                if (event.type === HttpEventType.UploadProgress) {
                    // calculate the progress percentage
                    const percentDone = Math.round((100 * event.loaded) / event.total );
                    if(this.isEnableProgressBar){
                        if (fileNameProgress.length > 35) { 
                            _p$.next({ name: fileNameProgress.substring(0, 35) + '...', progress: percentDone });
                        }else{
                            _p$.next({ name: fileNameProgress, progress: percentDone });
                        }
                        // pass the percentage into the progress-stream
                        this.progress$.next(percentDone);
                    }
                } else if (event instanceof HttpResponse) {
                    // Close the progress-stream if we get an answer form the API
                    // The upload is complete
                    // this.progress$.complete();
                    if(this.isEnableProgressBar){
                        _p$.complete();
                        this.filesUpload$.delete(name);
                    }
                    this.isUploadCompleted = true;
                    data$.next(event.body as BaseFile);
                    data$.complete();
                }
            });

        return data$.asObservable();
    }

    uploadConsoleFile(
        conferenceId: string,
        fileData: any,
        file: File
    ): Observable<BaseFile> {
        const url = this.configService
            .getApiConfig('uploadConsoleFile')
            .url.replace('{idConference}', conferenceId);
        const formData: FormData = new FormData();

        formData.append('file', file, file.name);
        formData.append('name ', file.name);

        if (
            (fileData.meetingDate as DateModel).date &&
            (fileData.meetingDate as DateModel).date !== undefined
        ) {
            formData.append(
                'meetingDate',
                (fileData.meetingDate as DateModel).date.format('DD-MM-YYYY')
            );
        }
        formData.append('type ', fileData.type);
        formData.append('visibility', JSON.stringify(fileData.visibility));
        formData.append(
            'category',
            fileData.category ? fileData.category.key : null
        );

        // create a http-post request and pass the form
        // tell it to report the upload progress
        const req = new HttpRequest('POST', url, formData, {
            reportProgress: true
        });

        const data$ = new Subject<BaseFile>();

        const _p$ = new Subject<{ name: string; progress: number }>();
        const name = file.name.concat(Math.random().toString());
        this.filesUpload$.set(name, _p$);

        this.uploads$.next(this.filesUpload$);
        // send the http-request and subscribe for progress-updates
        this.http
            .request(req)
            .pipe(
                catchError(err => {
                    _p$.error(err);
                    this.filesUpload$.delete(name);

                    // this.uploads$.next(this.filesUpload$);
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

                    _p$.next({ name: file.name, progress: percentDone });

                    // pass the percentage into the progress-stream
                    this.progress$.next(percentDone);
                } else if (event instanceof HttpResponse) {
                    // Close the progress-stream if we get an answer form the API
                    // The upload is complete
                    // this.progress$.complete();
                    _p$.complete();
                    this.filesUpload$.delete(name);

                    // this.uploads$.next(this.filesUpload$);
                    data$.next(event.body as BaseFile);
                    data$.complete();
                }
            });

        return data$.asObservable();
    }

    postModelUrlFile(
        conferenceId: string,
        fileData: any,
        type: FileType
    ): Observable<any> {
        const url = this.configService
            .getApiConfig('uploadFile')
            .url.replace('{conferenceId}', conferenceId);
        const formData: FormData = new FormData();

        formData.append('type', type);

        if (fileData.model) {
            formData.append('model', fileData.model.key);
        }

        if (fileData.cityReference) {
            formData.append('cityReference', fileData.cityReference);
        }

        if (fileData.visibility) {
            formData.append('visibility', JSON.stringify(fileData.visibility));
        } else {
            formData.append('visibility', JSON.stringify([]));
        }

        if (fileData.url) {
            formData.append('url', fileData.url);
        }
        
        if (fileData.fileComplient) {
            formData.append('fileComplient', fileData.fileComplient);
        }

        if (fileData.category) {
            formData.append(
                'category',
                fileData.category ? fileData.category.key : null
            );
        }

        formData.append('name', fileData.name);

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

    download(
        file: BaseFile,
        queryParams: any = {},
        enableProgressBar: boolean = false,
        lock: boolean = false
    ): Observable<HttpResponse<Blob>> {

        if(enableProgressBar === true)
            this.enableProgressBar();
        else
            this.disableProgressBar();
        
        if(lock === true) 
        {
            this.enableLock();
        }  
        else 
        {
            this.disableLock();

        }
            
        const _p$ = new Subject<{ name: string; progress: number }>();
        const data$ = new Subject<HttpResponse<Blob>>();
       
        var fileNameProgress = '';
        if(file.name) {
            fileNameProgress = file.name;
        }
            
        const name = fileNameProgress.concat(Math.random().toString());
        
        if(this.isEnableProgressBar){
            this.filesDownload$.set(name, _p$);
            this.progress$.next(0);
            this.downloads$.next(this.filesDownload$);
        }
        this.isUploadCompleted= false;

        this.http
        .request('get', file.url, {
            observe: 'response',
            responseType: 'blob',
            params: queryParams
            // headers: {
            //     'access-control-expose-headers': 'content-disposition'
            // }
        })
            .pipe(
                catchError(err => {
                    if(this.isEnableProgressBar){
                        _p$.error(err);
                        this.filesDownload$.delete(name);
                    }
                    data$.error(err);
                    data$.complete();
                    return of(err);
                })
            )
            .subscribe(event => {
               this.totalLenghtFile =  event.headers.get('file_length');
               console.log("stato download totale variabile " + this.totalLenghtFile );
               if(this.isLock) {
                queryParams  = {
                    lock: true
                }
               }

               this.http.request('get', file.url, {
                    reportProgress: true,
                    observe: 'events',
                    responseType: 'blob',
                    params: queryParams
                    // headers: {
                    //     'access-control-expose-headers': 'content-disposition'
                    // }
                })
                .pipe(
                    catchError(err => {
                        if(this.isEnableProgressBar){
                            _p$.error(err);
                            this.filesDownload$.delete(name);
                        }
                        data$.error(err);
                        data$.complete();
                        return of(err);
                    })
                )
                .subscribe(event => {
                    // let total = event.headers.get['test'];
                    // console.log("stato download totale variabile " + total);
                    if (event.type === HttpEventType.DownloadProgress) {
                        // calculate the progress percentage
                        console.log("url: " + file.url);
                        console.log("res: " + event);
                        console.log("stato download caricato " + event.loaded);
                        console.log("stato download totale " + event.total);
                        if (event.total !== undefined) {
                            const percentDone = Math.round((100 * event.loaded) / event.total );
                            if(this.isEnableProgressBar){
                                if (fileNameProgress.length > 35) { 
                                    _p$.next({ name: fileNameProgress.substring(0, 35) + '...', progress: percentDone });
                                }else{
                                    _p$.next({ name: fileNameProgress, progress: percentDone });
                                }
                                // pass the percentage into the progress-stream
                                this.progress$.next(percentDone);
                            }
                        } else {
                            const percentDone = Math.round((100 * event.loaded) / this.totalLenghtFile );
                            if(this.isEnableProgressBar){
                                if (fileNameProgress.length > 35) { 
                                    _p$.next({ name: fileNameProgress.substring(0, 35) + '...', progress: percentDone });
                                }else{
                                    _p$.next({ name: fileNameProgress, progress: percentDone });
                                }
                                // pass the percentage into the progress-stream
                                this.progress$.next(percentDone);
                            }
                        }
                        
                    } else if (event instanceof HttpResponse) {
                        // Close the progress-stream if we get an answer form the API
                        // The upload is complete
                        // this.progress$.complete();
                        if(this.isEnableProgressBar){
                            _p$.complete();
                            this.filesDownload$.delete(name);
                        }
                        this.isUploadCompleted = true;
                        const blob = event.body;
                        let fileName = null;
                        const contentType = event.headers.get('content-type');

                        // c'è un errore nella gestione della chiamata lato server
                        // perchè questo caso non dovrebbe essere un blob
                        if (contentType.indexOf('application/json') > -1) {
                            this._handleDownloadError();
                            return;
                        }

                        // IE/EDGE seems not returning some response header
                        if (event.headers.get('content-disposition')) {
                            const contentDisposition = event.headers.get(
                                'content-disposition'
                            );
                            fileName = contentDisposition
                                .substring(contentDisposition.indexOf('=') + 1)
                                .replace(/['"]+/g, '');
                        } else {
                            fileName =
                                'unnamed.' +
                                contentType.substring(contentType.indexOf('/') + 1);
                        }

                        if (window.navigator.msSaveOrOpenBlob) {
                            // Internet Explorer
                            window.navigator.msSaveOrOpenBlob(
                                new Blob([blob], { type: contentType }),
                                fileName
                            );
                        } else {
                            const el: any = document.getElementById('target');
                            el.href = window.URL.createObjectURL(blob);
                            el.download = fileName;
                            el.click();
                        }
                        data$.next(event.body as HttpResponse<Blob>);
                        data$.complete();
                    }
                });
            });

        /*
        this.http
        .request('get', file.url, {
            reportProgress: true,
            observe: 'events',
            responseType: 'blob',
            params: queryParams
            // headers: {
            //     'access-control-expose-headers': 'content-disposition'
            // }
        })
            .pipe(
                catchError(err => {
                    if(this.isEnableProgressBar){
                        _p$.error(err);
                        this.filesDownload$.delete(name);
                    }
                    data$.error(err);
                    data$.complete();
                    return of(err);
                })
            )
            .subscribe(event => {
               // let total = event.headers.get['test'];
               // console.log("stato download totale variabile " + total);
                if (event.type === HttpEventType.DownloadProgress) {
                    // calculate the progress percentage
                    console.log("url: " + file.url);
                    console.log("res: " + event);
                    console.log("stato download caricato " + event.loaded);
                    console.log("stato download totale " + event.total);
                    const percentDone = Math.round((100 * event.loaded) / event.total );
                    if(this.isEnableProgressBar){
                        if (fileNameProgress.length > 35) { 
                            _p$.next({ name: fileNameProgress.substring(0, 35) + '...', progress: percentDone });
                        }else{
                            _p$.next({ name: fileNameProgress, progress: percentDone });
                        }
                        // pass the percentage into the progress-stream
                        this.progress$.next(percentDone);
                    }
                } else if (event instanceof HttpResponse) {
                    // Close the progress-stream if we get an answer form the API
                    // The upload is complete
                    // this.progress$.complete();
                    if(this.isEnableProgressBar){
                        _p$.complete();
                        this.filesDownload$.delete(name);
                    }
                    this.isUploadCompleted = true;
                    const blob = event.body;
                        let fileName = null;
                        const contentType = event.headers.get('content-type');

                        // c'è un errore nella gestione della chiamata lato server
                        // perchè questo caso non dovrebbe essere un blob
                        if (contentType.indexOf('application/json') > -1) {
                            this._handleDownloadError();
                            return;
                        }

                        // IE/EDGE seems not returning some response header
                        if (event.headers.get('content-disposition')) {
                            const contentDisposition = event.headers.get(
                                'content-disposition'
                            );
                            fileName = contentDisposition
                                .substring(contentDisposition.indexOf('=') + 1)
                                .replace(/['"]+/g, '');
                        } else {
                            fileName =
                                'unnamed.' +
                                contentType.substring(contentType.indexOf('/') + 1);
                        }

                        if (window.navigator.msSaveOrOpenBlob) {
                            // Internet Explorer
                            window.navigator.msSaveOrOpenBlob(
                                new Blob([blob], { type: contentType }),
                                fileName
                            );
                        } else {
                            const el: any = document.getElementById('target');
                            el.href = window.URL.createObjectURL(blob);
                            el.download = fileName;
                            el.click();
                        }
                    data$.next(event.body as HttpResponse<Blob>);
                    data$.complete();
                }
            });
            */

        return data$.asObservable();
        /*
        return this.http
            .request('get', file.url, {
                reportProgress: true,
                observe: 'events',
                responseType: 'blob',
                params: queryParams
                // headers: {
                //     'access-control-expose-headers': 'content-disposition'
                // }
            })
            .pipe(
                tap(
                    
                res => { 
                    let total = parseFloat("1");
                    let flag = 1;
                    if (res instanceof HttpResponse) {
                        flag = 2;
                        total = res.headers.get['test'];
                    }
                    if(res.type === HttpEventType.DownloadProgress) {
                        // calculate the progress percentage
                        console.log("url: " + file.url);
                        console.log("res: " + res);
                        console.log("stato download caricato " + res.loaded);
                        console.log("stato download totale " + res.total);
                        console.log("stato download totale variabile " + total);
                        console.log("flag" + flag);
                        const percentDone = Math.round((100 * res.loaded) / res.total );
                        // pass the percentage into the progress-stream
                        if(this.isEnableProgressBar){
                            if (fileNameProgress.length > 35) { 
                                _p$.next({ name: fileNameProgress.substring(0, 35) + '...', progress: percentDone });
                            }else{
                                _p$.next({ name: fileNameProgress, progress: percentDone });
                            }
                            console.log("stato download " + percentDone);
                            this.progress$.next(percentDone);
                        }
            
                    }  else if (res instanceof HttpResponse) {
                        // Close the progress-stream if we get an answer form the API
                        // The upload is complete
                        if(this.isEnableProgressBar){
                            this.progress$.complete();
                            _p$.complete();
                            this.filesDownload$.delete(name);
                        }
                        this.isUploadCompleted= true;
                        // const blob = new Blob([res.body], {type: res.headers.get('content-type')});
                        // let url = window.URL.createObjectURL(blob);
                        // window.open(url, '_blank').focus();
                        const blob = res.body;
                        let fileName = null;
                        const contentType = res.headers.get('content-type');

                        // c'è un errore nella gestione della chiamata lato server
                        // perchè questo caso non dovrebbe essere un blob
                        if (contentType.indexOf('application/json') > -1) {
                            this._handleDownloadError();
                            return;
                        }

                        // IE/EDGE seems not returning some response header
                        if (res.headers.get('content-disposition')) {
                            const contentDisposition = res.headers.get(
                                'content-disposition'
                            );
                            fileName = contentDisposition
                                .substring(contentDisposition.indexOf('=') + 1)
                                .replace(/['"]+/g, '');
                        } else {
                            fileName =
                                'unnamed.' +
                                contentType.substring(contentType.indexOf('/') + 1);
                        }

                        if (window.navigator.msSaveOrOpenBlob) {
                            // Internet Explorer
                            window.navigator.msSaveOrOpenBlob(
                                new Blob([blob], { type: contentType }),
                                fileName
                            );
                        } else {
                            const el: any = document.getElementById('target');
                            el.href = window.URL.createObjectURL(blob);
                            el.download = fileName;
                            el.click();
                        }
                    }
                }),
                catchError(_ => {
                    if(this.isEnableProgressBar){
                        _p$.error(_ );
                        this.filesDownload$.delete(name);
                    }
                    this._handleDownloadError();
                    return of(null);
                })
            );
            */
            
    }

    private _handleDownloadError(): void {
        this.loaderService.hideLoading();   
        this.toastr.error(
            this.i18nService.translate('TOASTR.FILE_DOWNLOAD_ERROR.TEXT'),
            this.i18nService.translate('TOASTR.FILE_DOWNLOAD_ERROR.TITLE')
        );
    }

    downloadByApiName(
        apiName: string,
        paths: PathParams = {},
        query: any = {},
        enableProgressBar: boolean = false,
        lock: boolean = false
    ): Observable<HttpResponse<Blob>> {
        return this.download(
            new BaseFile({ url: this.api.getUrlByApiName(apiName, paths) }),
            query,
            enableProgressBar,
            lock
        );
    }

    getOOData(token: string): Observable<OnlyOffice> {
        return this.api.request('getOOData', null, null, { token });
    }
}
