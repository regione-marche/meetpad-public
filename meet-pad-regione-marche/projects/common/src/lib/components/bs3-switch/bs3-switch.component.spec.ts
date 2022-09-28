import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Bs3SwitchComponent } from './bs3-switch.component';

describe('Bs3SwitchComponent', () => {
  let component: Bs3SwitchComponent;
  let fixture: ComponentFixture<Bs3SwitchComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ Bs3SwitchComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(Bs3SwitchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
