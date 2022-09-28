import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MediaMainComponent } from './media-main.component';

describe('MediaMainComponent', () => {
    let component: MediaMainComponent;
    let fixture: ComponentFixture<MediaMainComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [MediaMainComponent]
        }).compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(MediaMainComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
