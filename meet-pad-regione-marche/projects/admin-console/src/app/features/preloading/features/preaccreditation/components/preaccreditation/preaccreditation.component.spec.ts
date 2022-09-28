import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PreaccreditationComponent } from './preaccreditation.component';

describe('PreaccreditationComponent', () => {
  let component: PreaccreditationComponent;
  let fixture: ComponentFixture<PreaccreditationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PreaccreditationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PreaccreditationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
