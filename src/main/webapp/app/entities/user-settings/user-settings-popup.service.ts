import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { UserSettings } from './user-settings.model';
import { UserSettingsService } from './user-settings.service';
@Injectable()
export class UserSettingsPopupService {
    private isOpen = false;
    constructor (
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private userSettingsService: UserSettingsService

    ) {}

    open (component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.userSettingsService.find(id).subscribe(userSettings => {
                userSettings.updatedAt = this.datePipe
                    .transform(userSettings.updatedAt, 'yyyy-MM-ddThh:mm');
                this.userSettingsModalRef(component, userSettings);
            });
        } else {
            return this.userSettingsModalRef(component, new UserSettings());
        }
    }

    userSettingsModalRef(component: Component, userSettings: UserSettings): NgbModalRef {
        let modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.userSettings = userSettings;
        modalRef.result.then(result => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        });
        return modalRef;
    }
}
