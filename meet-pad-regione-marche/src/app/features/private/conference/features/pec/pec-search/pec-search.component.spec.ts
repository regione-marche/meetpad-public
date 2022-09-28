import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PecSearchComponent } from './pec-search.component';

describe('PecSearchComponent', () => {
  let component: PecSearchComponent;
  let fixture: ComponentFixture<PecSearchComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PecSearchComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PecSearchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
