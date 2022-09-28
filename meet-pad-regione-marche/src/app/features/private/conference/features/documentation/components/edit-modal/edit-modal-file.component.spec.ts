import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EditModalFileComponent } from './edit-modal-file.component';

describe('EditModalComponent', () => {
  let component: EditModalFileComponent;
  let fixture: ComponentFixture<EditModalFileComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EditModalFileComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EditModalFileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
