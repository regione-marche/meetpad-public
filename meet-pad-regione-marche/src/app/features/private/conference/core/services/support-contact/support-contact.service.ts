import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { ApiService, LoggerService } from '@eng-ds/ng-core';

import {
    WrapperPostPutData,
    BaseService,
    WrapperDeleteData,
    WrapperGetData
} from '@common';
import { SupportContact } from '../../models/definition/support-contact.model';

@Injectable({
    providedIn: 'root'
})
export class SupportContactService extends BaseService {
    constructor(
        protected api: ApiService,
        private loggerService: LoggerService
    ) {
        super(api);
    }

    getContacts(conferenceId: string): Observable<SupportContact[]> {
        return this.api
            .request<WrapperGetData<SupportContact>>(
                'getContacts',
                null,
                null,
                {
                    conferenceId
                }
            )
            .pipe(map(response => response.list));
    }

    createContact(
        conferenceId: string,
        contact: SupportContact
    ): Observable<WrapperPostPutData> {
        return this.api.request('createContact', contact, null, {
            conferenceId
        });
    }

    editContact(contact: SupportContact): Observable<WrapperPostPutData> {
        return this.api.request('editContact', contact, null, {
            contactId: contact.id
        });
    }

    deleteContact(contactId: string): Observable<WrapperDeleteData> {
        return this.api.request('deleteContact', null, null, {
            contactId
        });
    }
}
