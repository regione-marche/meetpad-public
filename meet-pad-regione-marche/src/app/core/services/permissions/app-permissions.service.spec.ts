import { TestBed } from '@angular/core/testing';

import { AppPermissionsService } from './app-permissions.service';

describe('AppPermissionsService', () => {
    beforeEach(() => TestBed.configureTestingModule({}));

    it('should be created', () => {
        const service: AppPermissionsService = TestBed.get(
            AppPermissionsService
        );
        expect(service).toBeTruthy();
    });
});
