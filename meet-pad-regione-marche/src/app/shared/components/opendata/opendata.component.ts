import { Component, OnInit } from '@angular/core';

import {
    LoaderService,
    SectionLoading,
    AuthService,
    HeaderService,
    FileService
} from '@common';

import { MenuService } from '@app/core';
import {OpendataService} from "@app/shared/components/opendata/opendata.service";
import {OpendataModel} from "@app/shared/components/opendata/opendata-model";
import { catchError } from 'rxjs/operators';
import { of } from 'rxjs';


@Component({
  selector: 'app-opendata',
  templateUrl: './opendata.component.html',
  styleUrls:  ['./opendata.component.scss']
})
export class OpendataComponent implements OnInit {
    OpenData: any = [];
  constructor(
                private loaderService: LoaderService,
                private authService: AuthService,
                private headerService: HeaderService,
                public menuService: MenuService,
                public OpendataService: OpendataService,
                private fileService: FileService,
             )
  {
      this.loaderService.hideLoading(SectionLoading.ALL_CONTENT);
  }

    ngOnInit(): void {
        if (this.authService.hasValidToken()) {
            this.menuService.loadPrivateMenu();
            this.loadOpendata();

        } else {
            this.headerService.showLogin();
            this.headerService.showMenu();
            this.menuService.loadPublicMenu();
            this.loadOpendata();
        }

    }

    loadOpendata() {
        return this.OpendataService.getOpendata().subscribe((data: any) => {

            this.OpenData = data.list;
            console.log(this.OpenData);
        });
    }

    
    
    downloadFile(openData: OpendataModel){
        console.info(openData)
        this.fileService.downloadByApiName('downloadReport',{code: openData.CODICE}).pipe(catchError( err => {
            console.info(err);
            return of(null);
        })).subscribe( res => {

        });
    }

}
