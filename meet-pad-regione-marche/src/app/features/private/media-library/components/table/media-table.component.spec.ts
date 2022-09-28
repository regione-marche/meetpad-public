import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MediaTableComponent } from './media-table.component';

describe('ResultTableComponent', () => {
    let component: MediaTableComponent;
    let fixture: ComponentFixture<MediaTableComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [MediaTableComponent]
        }).compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(MediaTableComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
