import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PreaccreditationMainComponent } from './main.component';

describe('PreaccreditationMainComponent', () => {
  let component: PreaccreditationMainComponent;
  let fixture: ComponentFixture<PreaccreditationMainComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PreaccreditationMainComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PreaccreditationMainComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
