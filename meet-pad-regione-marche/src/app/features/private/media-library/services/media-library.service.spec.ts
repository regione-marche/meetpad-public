import { TestBed } from '@angular/core/testing';

import { MediaLibraryService } from './media-library.service';

describe('MediaLibraryService', () => {
    beforeEach(() => TestBed.configureTestingModule({}));

    it('should be created', () => {
        const service: MediaLibraryService = TestBed.get(MediaLibraryService);
        expect(service).toBeTruthy();
    });
});
