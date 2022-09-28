import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AccreditationTabComponent } from './accreditation-tab.component';

describe('AccreditationTabComponent', () => {
    let component: AccreditationTabComponent;
    let fixture: ComponentFixture<AccreditationTabComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [AccreditationTabComponent]
        }).compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(AccreditationTabComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
