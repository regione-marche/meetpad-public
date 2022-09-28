import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PecComponent } from './pec.component';

describe('PecComponent', () => {
  let component: PecComponent;
  let fixture: ComponentFixture<PecComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PecComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PecComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
