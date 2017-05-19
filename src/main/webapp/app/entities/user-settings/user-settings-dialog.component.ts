import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { UserSettings } from './user-settings.model';
import { UserSettingsPopupService } from './user-settings-popup.service';
import { UserSettingsService } from './user-settings.service';
import { User, UserService } from '../../shared';

@Component({
    selector: 'jhi-user-settings-dialog',
    templateUrl: './user-settings-dialog.component.html'
})
export class UserSettingsDialogComponent implements OnInit {

    userSettings: UserSettings;
    authorities: any[];
    isSaving: boolean;

    users: User[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private userSettingsService: UserSettingsService,
        private userService: UserService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.userService.query().subscribe(
            (res: Response) => { this.users = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.userSettings.id !== undefined) {
            this.subscribeToSaveResponse(
                this.userSettingsService.update(this.userSettings));
        } else {
            this.subscribeToSaveResponse(
                this.userSettingsService.create(this.userSettings));
        }
    }

    private subscribeToSaveResponse(result: Observable<UserSettings>) {
        result.subscribe((res: UserSettings) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: UserSettings) {
        this.eventManager.broadcast({ name: 'userSettingsListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    trackUserById(index: number, item: User) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-user-settings-popup',
    template: ''
})
export class UserSettingsPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private userSettingsPopupService: UserSettingsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.userSettingsPopupService
                    .open(UserSettingsDialogComponent, params['id']);
            } else {
                this.modalRef = this.userSettingsPopupService
                    .open(UserSettingsDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
