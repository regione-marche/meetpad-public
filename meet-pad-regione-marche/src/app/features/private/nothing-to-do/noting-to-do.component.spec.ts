import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NothingToDoComponent } from './nothing-to-do.component';

describe('NothingToDoComponent', () => {
    let component: NothingToDoComponent;
    let fixture: ComponentFixture<NothingToDoComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [NothingToDoComponent]
        }).compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(NothingToDoComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
