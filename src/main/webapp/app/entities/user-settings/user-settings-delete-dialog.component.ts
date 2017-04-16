import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { UserSettings } from './user-settings.model';
import { UserSettingsPopupService } from './user-settings-popup.service';
import { UserSettingsService } from './user-settings.service';

@Component({
    selector: 'jhi-user-settings-delete-dialog',
    templateUrl: './user-settings-delete-dialog.component.html'
})
export class UserSettingsDeleteDialogComponent {

    userSettings: UserSettings;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private userSettingsService: UserSettingsService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['userSettings']);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.userSettingsService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'userSettingsListModification',
                content: 'Deleted an userSettings'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-user-settings-delete-popup',
    template: ''
})
export class UserSettingsDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private userSettingsPopupService: UserSettingsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.userSettingsPopupService
                .open(UserSettingsDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
