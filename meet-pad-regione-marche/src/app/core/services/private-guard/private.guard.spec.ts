import { TestBed, async, inject } from '@angular/core/testing';

import { PrivateGuard } from './private.guard';

describe('PrivateGuard', () => {
    beforeEach(() => {
        TestBed.configureTestingModule({
            providers: [PrivateGuard]
        });
    });

    it('should ...', inject([PrivateGuard], (guard: PrivateGuard) => {
        expect(guard).toBeTruthy();
    }));
});
