import { TestBed } from '@angular/core/testing';

import { ConferenceStoreService } from './conference-store.service';

describe('ConferenceStoreService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: ConferenceStoreService = TestBed.get(ConferenceStoreService);
    expect(service).toBeTruthy();
  });
});
