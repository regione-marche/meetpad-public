import { Observable, Subject, of } from 'rxjs';
import { map } from 'rxjs/operators';

import { ApiService } from '@eng-ds/ng-core';

import { User } from '../../interfaces/user.interface';
import { ApplicationRole } from '../../enums/application-role.enum';

import { BaseService } from '../../class/base-service.class';

export class UserService extends BaseService {
    public unauthenticated$: Subject<void> = new Subject();
    public systemMaintenance$: Subject<void> = new Subject();

    constructor(protected api: ApiService) {
        super(api);
    }

    getInfo(cache: boolean = true): Observable<User | boolean> {
        return this.get<User>(
            this.api.request('getUserInfo'),
            'userInfo',
            !cache
        );
    }

    getRole(): Observable<ApplicationRole> {
        return this.getInfo().pipe(
            map((info: User) => {
                return (
                    info &&
                    info.profile &&
                    (info.profile.key as ApplicationRole)
                );
            })
        );
    }

    isAdmin(): Observable<boolean> {
        return this.getRole().pipe(
            map(value => value === ApplicationRole.ADMINISTRATOR)
        );
    }

    isSignatoryUser(): Observable<boolean> {
        
        return this.getInfo().pipe(
            map((info: User) => {
                return (
                    info.flagSignatory === true
                );
            })
        );
    }
}
