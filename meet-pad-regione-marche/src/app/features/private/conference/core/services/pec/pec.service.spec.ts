import { TestBed } from '@angular/core/testing';

import { PecService } from './pec.service';

describe('PecService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: PecService = TestBed.get(PecService);
    expect(service).toBeTruthy();
  });
});
