import { TestBed } from '@angular/core/testing';

import { UserPortalService } from './user-portal.service';

describe('UserPortalService', () => {
    beforeEach(() => TestBed.configureTestingModule({}));

    it('should be created', () => {
        const service: UserPortalService = TestBed.get(UserPortalService);
        expect(service).toBeTruthy();
    });
});
