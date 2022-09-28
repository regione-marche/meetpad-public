import { TestBed, async } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { AdminConsoleComponent } from './admin-console.component';

describe('AdminConsoleComponent', () => {
    beforeEach(async(() => {
        TestBed.configureTestingModule({
            imports: [RouterTestingModule],
            declarations: [AdminConsoleComponent]
        }).compileComponents();
    }));

    it('should create the app', () => {
        const fixture = TestBed.createComponent(AdminConsoleComponent);
        const app = fixture.debugElement.componentInstance;
        expect(app).toBeTruthy();
    });

    it(`should have as title 'adminConsole'`, () => {
        const fixture = TestBed.createComponent(AdminConsoleComponent);
        const app = fixture.debugElement.componentInstance;
        expect(app.title).toEqual('adminConsole');
    });

    it('should render title in a h1 tag', () => {
        const fixture = TestBed.createComponent(AdminConsoleComponent);
        fixture.detectChanges();
        const compiled = fixture.debugElement.nativeElement;
        expect(compiled.querySelector('h1').textContent).toContain(
            'Welcome to adminConsole!'
        );
    });
});
