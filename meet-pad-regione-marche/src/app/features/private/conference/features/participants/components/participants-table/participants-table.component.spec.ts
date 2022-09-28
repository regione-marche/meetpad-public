import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { ParticipantsTableComponent } from '..';


describe('ParticipantsTableComponent', () => {
  let component: ParticipantsTableComponent;
  let fixture: ComponentFixture<ParticipantsTableComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ParticipantsTableComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ParticipantsTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
