import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

interface WrapperError {
    fields: string[];
    msg: string;
    code: string;
    step: string;
}

interface WrapperGetData<T> {
    list: T[];
    totalNumber: string;
}

interface WrapperPostPutData {
    id: string;
}

interface WrapperPostPutAccreditation extends WrapperPostPutData {
    accreditamentFlag: boolean;
}

// tslint:disable-next-line:no-empty-interface
interface WrapperDeleteData {}

interface WrapperResponse<T = undefined, E = WrapperError> {
    code: string;
    msg: string;
    data: T;
    errors: E[];
}

declare type HttpGetResponse<T> = WrapperResponse<
    WrapperGetData<T>,
    WrapperError
>;

declare type HttpInternalErrorResponse = HttpResponse<
    WrapperResponse<undefined>
> &
    HttpErrorResponse;

export {
    WrapperError,
    WrapperGetData,
    WrapperResponse,
    WrapperPostPutData,
    WrapperPostPutAccreditation,
    WrapperDeleteData,
    HttpGetResponse,
    HttpInternalErrorResponse
};
