import { TestBed } from '@angular/core/testing';

import { SupportContactService } from './support-contact.service';

describe('SupportContactService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: SupportContactService = TestBed.get(SupportContactService);
    expect(service).toBeTruthy();
  });
});
