import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ProtocolTabComponent } from './protocol.component';

describe('AccreditationTabComponent', () => {
    let component: ProtocolTabComponent;
    let fixture: ComponentFixture<ProtocolTabComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [ProtocolTabComponent]
        }).compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(ProtocolTabComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
