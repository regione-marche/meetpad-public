import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PendingListPageComponent } from './pending-list-page.component';

describe('PendingListPageComponent', () => {
    let component: PendingListPageComponent;
    let fixture: ComponentFixture<PendingListPageComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [PendingListPageComponent]
        }).compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(PendingListPageComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
