import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService  } from 'ng-jhipster';

import { UserSettings } from './user-settings.model';
import { UserSettingsService } from './user-settings.service';

@Component({
    selector: 'jhi-user-settings-detail',
    templateUrl: './user-settings-detail.component.html'
})
export class UserSettingsDetailComponent implements OnInit, OnDestroy {

    userSettings: UserSettings;
    private subscription: any;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private userSettingsService: UserSettingsService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['userSettings']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
        this.registerChangeInUserSettings();
    }

    load (id) {
        this.userSettingsService.find(id).subscribe(userSettings => {
            this.userSettings = userSettings;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInUserSettings() {
        this.eventSubscriber = this.eventManager.subscribe('userSettingsListModification', response => this.load(this.userSettings.id));
    }

}
