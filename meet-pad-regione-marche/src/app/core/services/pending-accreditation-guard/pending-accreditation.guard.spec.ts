import { TestBed, inject } from '@angular/core/testing';

import { PendingAccreditationGuard } from './pending-accreditation.guard';

describe('PendingAccreditationGuard', () => {
    beforeEach(() => {
        TestBed.configureTestingModule({
            providers: [PendingAccreditationGuard]
        });
    });

    it('should ...', inject(
        [PendingAccreditationGuard],
        (guard: PendingAccreditationGuard) => {
            expect(guard).toBeTruthy();
        }
    ));
});
