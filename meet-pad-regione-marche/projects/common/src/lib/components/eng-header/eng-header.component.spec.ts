import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EngHeaderComponent } from './eng-header.component';

describe('EngHeaderComponent', () => {
  let component: EngHeaderComponent;
  let fixture: ComponentFixture<EngHeaderComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EngHeaderComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EngHeaderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
