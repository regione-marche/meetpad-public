import { TestBed } from '@angular/core/testing';
import { ParticipantsService } from '..';

describe('ParticipantsService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: ParticipantsService = TestBed.get(ParticipantsService);
    expect(service).toBeTruthy();
  });
});
