import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ConferenceModalBeforeCreateComponent } from './conference-modal-before-create.component';

describe('ConferenceModalBeforeCreateComponent', () => {
    let component: ConferenceModalBeforeCreateComponent;
    let fixture: ComponentFixture<ConferenceModalBeforeCreateComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [ConferenceModalBeforeCreateComponent]
        }).compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(ConferenceModalBeforeCreateComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
