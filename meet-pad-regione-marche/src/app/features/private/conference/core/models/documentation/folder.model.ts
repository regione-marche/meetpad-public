/* tslint:disable:no-inferrable-types */
import { BaseFile } from '@common';

export class Folder<T extends BaseFile> {
    name: string;
    files: T[] = [];
    private model: { new (file: Partial<T>): T };

    constructor(
        model: { new (file: Partial<T>): T },
        folder?: Partial<Folder<T>>
    ) {
        this.model = model;
        if (folder) {
            this.name = folder.name === 'default' ? null : folder.name;

            for (const k in folder.files) {
                if (folder.files[k]) {
                    this.files.push(new this.model(folder.files[k]));
                }
            }
        }
    }

    push(file: T): void {
        this.files.push(file);
    }

    find(fileId: string): BaseFile {
        return this.files.find((f: BaseFile) => f.id === fileId);
    }

    update(file: T): void {
        for (const k in this.files) {
            if (this.files[k] && this.files[k].id === file.id) {
                this.files[k] = new this.model(file);
            }
        }
    }

    delete(file: T): void {
        const i = this.files.findIndex((f: BaseFile) => f.id === file.id);
        this.files.splice(i, 1);
    }
}
