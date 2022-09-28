/* tslint:disable:no-inferrable-types */

import { BaseFile, FileType } from '@common';
import { Folder } from './folder.model';
import { AdditionalFile } from './additional-file.model';
import { IndictionFile } from './indiction-file.model';
import { InterectionFile } from './interection-file.model';
import { PreliminaryFile } from './preliminary-file.model';
import { SharedFile } from './shared-file.model';
import { SignatureFile } from './signature-file.model';

export class Documentation {
    additional: Folder<AdditionalFile>[] = [];
    interection: Folder<InterectionFile>[] = [];
    shared: Folder<SharedFile>[] = [];
    indiction: IndictionFile[] = [];
    preliminary: Folder<PreliminaryFile>[] = [];
    signature: Folder<SignatureFile>[] = [];

    constructor(documentation: Partial<Documentation>) {
        if (documentation) {
            for (const k in documentation.additional) {
                if (documentation.additional[k]) {
                    this.additional.push(
                        new Folder(AdditionalFile, documentation.additional[k])
                    );
                }
            }
            for (const k in documentation.interection) {
                if (documentation.interection[k]) {
                    this.interection.push(
                        new Folder(
                            InterectionFile,
                            documentation.interection[k]
                        )
                    );
                }
            }
            for (const k in documentation.shared) {
                if (documentation.shared[k]) {
                    this.shared.push(
                        new Folder(SharedFile, documentation.shared[k])
                    );
                }
            }
            for (const k in documentation.indiction) {
                if (documentation.indiction[k]) {
                    this.indiction.push(
                        new IndictionFile(documentation.indiction[k])
                    );
                }
            }
            for (const k in documentation.preliminary) {
                if (documentation.preliminary[k]) {
                    this.preliminary.push(
                        new Folder(
                            PreliminaryFile,
                            documentation.preliminary[k]
                        )
                    );
                }
            }
            for (const k in documentation.signature) {
                if (documentation.signature[k]) {
                    this.signature.push(
                        new Folder(
                            SignatureFile,
                            documentation.signature[k]
                        )
                    );
                }
            }
        }
    }

    push(file: any): void {
        let folders: Folder<BaseFile>[] = [];
        let fileModel = null;
        switch (file.type) {
            case FileType.ADDITIONAL:
                folders = this.additional;
                fileModel = AdditionalFile;
                break;
            case FileType.INTERETION:
                folders = this.interection;
                fileModel = InterectionFile;
                break;
            case FileType.PRELIMINARY:
                folders = this.preliminary;
                fileModel = PreliminaryFile;
                break;
            case FileType.SHARED:
                folders = this.shared;
                fileModel = SharedFile;
                break;
            case FileType.SIGNATURE:
                folders = this.signature;
                fileModel = SignatureFile;
                break;
        }

        let folder = this._findDefaultFolderByFolders(folders);
        if (!folder) {
            folder = new Folder(fileModel, {
                name: 'default'
            });
            folders.push(folder);
        }
        folder.push(file);
    }

    delete(file: any): void {

        if(file.type === FileType.INDICTION ){
            const allIndiction = this.indiction;
            const folderIndex = this._findIndictionIndexByFileId(file.id, allIndiction);
            if (folderIndex !== null) {
                allIndiction.splice(folderIndex, 1);
            }

        }else{
            const folders: Folder<BaseFile>[] = this._getDocumentationByFile(file);
            const folderIndex = this._findFolderIndexByFileId(file.id, folders);

            if (folderIndex !== null) {
                folders[folderIndex].delete(file);
            }

            if (!folders[folderIndex].files.length) {
                folders.splice(folderIndex, 1);
            }
        }
    }

    update(file: BaseFile): void {
        const folders: Folder<BaseFile>[] = this._getDocumentationByFile(file);
        const folder = this._findFolderByFileId(file.id, folders);

        if (folder) {
            folder.update(file);
        }
    }

    private _getDocumentationByFile(file: BaseFile): Folder<BaseFile>[] {
        switch (file.type) {
            case FileType.ADDITIONAL:
                return this.additional;
            case FileType.INTERETION:
                return this.interection;
            case FileType.PRELIMINARY:
                return this.preliminary;
            case FileType.SHARED:
                return this.shared;
            case FileType.SIGNATURE:
                return this.signature;
        }
    }

    private _findDefaultFolderByFolders(
        folders: Folder<BaseFile>[]
    ): Folder<BaseFile> {
        return folders.find((f: Folder<BaseFile>) => !f.name);
    }

    private _findFolderByFileId(
        fileId: string,
        folders: Folder<BaseFile>[]
    ): Folder<BaseFile> {
        for (let i = 0; i < folders.length; i++) {
            if (folders[i].find(fileId)) {
                return folders[i];
            }
        }
        return null;
    }

    private _findFolderIndexByFileId(
        fileId: string,
        folders: Folder<BaseFile>[]
    ): number {
        for (let i = 0; i < folders.length; i++) {
            if (folders[i].find(fileId)) {
                return i;
            }
        }
        return null;
    }
  
    private _findIndictionIndexByFileId(
        fileId: string,
        folders: IndictionFile[]
    ): number {
        for (let i = 0; i < folders.length; i++) {
            if (folders[i].id ===fileId) {
                return i;
            }
        }
        return null;
    }
}
