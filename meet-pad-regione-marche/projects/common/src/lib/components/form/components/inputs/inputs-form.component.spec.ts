import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { InputsFormComponent } from './inputs-form.component';

describe('InputsFormFormComponent', () => {
    let component: InputsFormComponent;
    let fixture: ComponentFixture<InputsFormComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [InputsFormComponent]
        }).compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(InputsFormComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
