import { Component, OnInit, OnDestroy } from '@angular/core';
import { Response } from '@angular/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, JhiLanguageService, AlertService } from 'ng-jhipster';

import { UserSettings } from './user-settings.model';
import { UserSettingsService } from './user-settings.service';
import { ITEMS_PER_PAGE, Principal } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-user-settings',
    templateUrl: './user-settings.component.html'
})
export class UserSettingsComponent implements OnInit, OnDestroy {
userSettings: UserSettings[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private userSettingsService: UserSettingsService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.userSettingsService.query().subscribe(
            (res: Response) => {
                this.userSettings = res.json();
            },
            (res: Response) => this.onError(res.json())
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInUserSettings();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: UserSettings) {
        return item.id;
    }
    registerChangeInUserSettings() {
        this.eventSubscriber = this.eventManager.subscribe('userSettingsListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
