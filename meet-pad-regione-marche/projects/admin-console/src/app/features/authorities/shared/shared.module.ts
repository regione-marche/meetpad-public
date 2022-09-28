import { NgModule } from '@angular/core';
import { CommonModule } from '@common';
import { AuthoritySearchComponent } from './components/authority-search/authority-search.component';
import { AuthoritiesListComponent } from './components/authorities-list/authorities-list.component';

@NgModule({
    imports: [CommonModule],
    declarations: [AuthoritySearchComponent, AuthoritiesListComponent],
    exports: [AuthoritySearchComponent, AuthoritiesListComponent]
})
export class SharedAuthoritiesModule {}
