import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { OneToManyFormComponent } from './one-to-many-form.component';

describe('OneToManyFormComponent', () => {
  let component: OneToManyFormComponent;
  let fixture: ComponentFixture<OneToManyFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ OneToManyFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(OneToManyFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
