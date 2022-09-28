import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AccreditationTableComponent } from './accreditation-table.component';

describe('AccreditationTableComponent', () => {
  let component: AccreditationTableComponent;
  let fixture: ComponentFixture<AccreditationTableComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AccreditationTableComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AccreditationTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
