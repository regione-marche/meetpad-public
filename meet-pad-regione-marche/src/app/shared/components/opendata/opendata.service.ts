import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import {retry, catchError, map} from 'rxjs/operators';
import { ApiService } from '@eng-ds/ng-core';


@Injectable({
    providedIn: 'root'
})
export class OpendataService {

    constructor(
        private http: HttpClient,
        private api: ApiService
        ) {}

    getOpendata(): Observable<any> {
        return this.http.get(this.api.getUrlByApiName('getReportList') )
            .pipe(
                map((result:any) => {
                    return {
                        list: result.list,
                        totalNumber: result.totalNumber
                    };
                }),
                retry(1),
                catchError(this.handleError)
            );
    }

// Error handling
    handleError(error) {
        let errorMessage = '';
        if (error.error instanceof ErrorEvent) {
// Get client-side error
            errorMessage = error.error.message;
        } else {
// Get server-side error
            errorMessage = `Error Code: ${error.status}\nMessage: ${error.message}`;
        }
        // window.alert(errorMessage);
        return throwError(errorMessage);
    }
}
