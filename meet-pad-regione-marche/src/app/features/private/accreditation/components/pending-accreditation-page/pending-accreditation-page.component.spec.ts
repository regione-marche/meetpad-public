import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PendingAccreditationPageComponent } from './pending-accreditation-page.component';

describe('PendingAccreditationPageComponent', () => {
  let component: PendingAccreditationPageComponent;
  let fixture: ComponentFixture<PendingAccreditationPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PendingAccreditationPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PendingAccreditationPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
