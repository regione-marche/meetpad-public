import { BaseModel } from '@eng-ds/ng-toolkit';

export class MediaLibrary extends BaseModel {
    id: any;
    name: string = '';
    conferenceId: string = '';
    conferenceSubject: string = '';
    registryId: string = '';
    path: string = '';
    md5: string = '';

    constructor(mediaLibrary?: Partial<MediaLibrary>) {
        super();
        if (mediaLibrary) {
            this.id = mediaLibrary.id;
            this.name = mediaLibrary.name;
            this.conferenceId = mediaLibrary.conferenceId;
            this.conferenceSubject = mediaLibrary.conferenceSubject;
            this.registryId = mediaLibrary.registryId;
            this.path = mediaLibrary.path;
            this.md5 = mediaLibrary.md5;
        }
    }

    update(mediaLibrary: MediaLibrary): void {
        this.id = mediaLibrary.id;
        this.name = mediaLibrary.name;
        this.conferenceId = mediaLibrary.conferenceId;
        this.conferenceSubject = mediaLibrary.conferenceSubject;
        this.registryId = mediaLibrary.registryId;
        this.path = mediaLibrary.path;
        this.md5 = mediaLibrary.md5;
    }

    // tslint:disable-next-line: member-ordering
    static fromDto(data: any) {
        return new MediaLibrary({
            id: data.id,
            name: data.name,
            conferenceId: data.conferenceId,
            conferenceSubject: data.conferenceSubject,
            registryId: data.registryId,
            path: data.path,
            md5: data.md5
        });
    }
}
