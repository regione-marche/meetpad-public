import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DelegateMainComponent } from './main.component';

describe('DelegateMainComponent', () => {
  let component: DelegateMainComponent;
  let fixture: ComponentFixture<DelegateMainComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DelegateMainComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DelegateMainComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
