import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SignPanelComponent } from './sign-panel.component';

describe('SignPanelComponent', () => {
  let component: SignPanelComponent;
  let fixture: ComponentFixture<SignPanelComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SignPanelComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SignPanelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
