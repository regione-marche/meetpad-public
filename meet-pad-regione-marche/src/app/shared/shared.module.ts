import { NgModule } from '@angular/core';
import { CommonModule } from '@common';
import { AuthDirective } from '@app/shared/directives';
import { ContactComponent } from './components/contact/contact.component';
import { OpendataComponent } from './components/opendata/opendata.component';

@NgModule({
    imports: [CommonModule],
    declarations: [AuthDirective, ContactComponent, OpendataComponent],
    exports: [CommonModule, AuthDirective, ContactComponent, OpendataComponent]
})
export class SharedModule {}
