import { TestBed, inject } from '@angular/core/testing';

import { UtilityService } from './utility.service';
import { HttpClient, HttpHandler } from '@angular/common/http';

describe('UtilityService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [UtilityService, HttpClient, HttpHandler]
    });
  });

  it('should be created', inject([UtilityService], (service: UtilityService) => {
    expect(service).toBeTruthy();
  }));
});
