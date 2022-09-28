import { TestBed, inject } from '@angular/core/testing';

import { HeaderGuard } from './header.guard';

describe('HeaderGuard', () => {
    beforeEach(() => {
        TestBed.configureTestingModule({
            providers: [HeaderGuard]
        });
    });

    it('should ...', inject([HeaderGuard], (guard: HeaderGuard) => {
        expect(guard).toBeTruthy();
    }));
});
