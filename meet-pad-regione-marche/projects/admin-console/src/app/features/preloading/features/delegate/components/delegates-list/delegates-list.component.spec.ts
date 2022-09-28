import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DelegatesListComponent } from './delegates-list.component';

describe('DelegatesListComponent', () => {
  let component: DelegatesListComponent;
  let fixture: ComponentFixture<DelegatesListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DelegatesListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DelegatesListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
