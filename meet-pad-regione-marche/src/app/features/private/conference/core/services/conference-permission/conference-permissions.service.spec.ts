import { TestBed } from '@angular/core/testing';

import { ConferencePermissionsService } from './conference-permissions.service';

describe('ConferencePermissionsService', () => {
    beforeEach(() => TestBed.configureTestingModule({}));

    it('should be created', () => {
        const service: ConferencePermissionsService = TestBed.get(
            ConferencePermissionsService
        );
        expect(service).toBeTruthy();
    });
});
