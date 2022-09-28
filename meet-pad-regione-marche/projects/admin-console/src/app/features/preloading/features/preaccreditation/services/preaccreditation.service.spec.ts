import { TestBed } from '@angular/core/testing';

import { PreaccreditationService } from './preaccreditation.service';

describe('PreaccreditationService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: PreaccreditationService = TestBed.get(PreaccreditationService);
    expect(service).toBeTruthy();
  });
});
