import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SignTableComponent } from './sign-table.component';

describe('SignTableComponent', () => {
  let component: SignTableComponent;
  let fixture: ComponentFixture<SignTableComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SignTableComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SignTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
