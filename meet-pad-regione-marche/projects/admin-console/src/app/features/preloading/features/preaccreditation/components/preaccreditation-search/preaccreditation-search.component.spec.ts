import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PreaccreditationSearchComponent } from './preaccreditation-search.component';

describe('PreaccreditationSearchComponent', () => {
  let component: PreaccreditationSearchComponent;
  let fixture: ComponentFixture<PreaccreditationSearchComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PreaccreditationSearchComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PreaccreditationSearchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
