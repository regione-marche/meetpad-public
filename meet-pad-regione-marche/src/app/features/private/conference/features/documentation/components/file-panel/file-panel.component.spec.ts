import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FilePanelComponent } from './file-panel.component';

describe('FilePanelComponent', () => {
  let component: FilePanelComponent;
  let fixture: ComponentFixture<FilePanelComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FilePanelComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FilePanelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
