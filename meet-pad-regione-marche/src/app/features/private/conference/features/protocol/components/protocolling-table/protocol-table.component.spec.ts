import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ProtocolTableComponent } from './protocolling-table.component';

describe('ProtocolTableComponent', () => {
  let component:ProtocolTableComponent;
  let fixture: ComponentFixture<ProtocolTableComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ProtocolTableComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ProtocolTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});