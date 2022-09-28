import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EngFileDropComponent } from './eng-file-drop.component';

describe('EngFileDropComponent', () => {
  let component: EngFileDropComponent;
  let fixture: ComponentFixture<EngFileDropComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EngFileDropComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EngFileDropComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
