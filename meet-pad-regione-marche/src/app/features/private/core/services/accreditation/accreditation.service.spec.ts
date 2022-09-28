import { TestBed } from '@angular/core/testing';

import { AccreditationService } from './accreditation.service';

describe('AccreditationService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: AccreditationService = TestBed.get(AccreditationService);
    expect(service).toBeTruthy();
  });
});
