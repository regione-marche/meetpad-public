import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PaleoComponent } from './paleo.component';

describe('PaleoComponent', () => {
  let component: PaleoComponent;
  let fixture: ComponentFixture<PaleoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PaleoComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PaleoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
