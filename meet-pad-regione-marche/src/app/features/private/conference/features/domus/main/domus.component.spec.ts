import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DomusComponent } from './domus.component';

describe('DomusComponent', () => {
    let component: DomusComponent;
    let fixture: ComponentFixture<DomusComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [DomusComponent]
        }).compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(DomusComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
