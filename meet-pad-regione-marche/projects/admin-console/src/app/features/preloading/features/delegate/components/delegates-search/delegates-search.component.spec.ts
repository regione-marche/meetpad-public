import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DelegatesSearchComponent } from './delegates-search.component';

describe('DelegatesSearchComponent', () => {
  let component: DelegatesSearchComponent;
  let fixture: ComponentFixture<DelegatesSearchComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DelegatesSearchComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DelegatesSearchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
