import { TestBed } from '@angular/core/testing';

import { DelegateService } from './delegate.service';

describe('DelegateService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: DelegateService = TestBed.get(DelegateService);
    expect(service).toBeTruthy();
  });
});
