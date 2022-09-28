import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PreaccreditationListComponent } from './preaccreditation-list.component';

describe('PreaccreditationListComponent', () => {
  let component: PreaccreditationListComponent;
  let fixture: ComponentFixture<PreaccreditationListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PreaccreditationListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PreaccreditationListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
