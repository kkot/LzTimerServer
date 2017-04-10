import { Component, OnInit, OnDestroy } from '@angular/core';
import { Response } from '@angular/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, JhiLanguageService, AlertService } from 'ng-jhipster';

import { Period } from './period.model';
import { PeriodService } from './period.service';
import { ITEMS_PER_PAGE, Principal } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-period',
    templateUrl: './period.component.html'
})
export class PeriodComponent implements OnInit, OnDestroy {
periods: Period[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private periodService: PeriodService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
        this.jhiLanguageService.setLocations(['period']);
    }

    loadAll() {
        this.periodService.query().subscribe(
            (res: Response) => {
                this.periods = res.json();
            },
            (res: Response) => this.onError(res.json())
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInPeriods();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId (index: number, item: Period) {
        return item.id;
    }



    registerChangeInPeriods() {
        this.eventSubscriber = this.eventManager.subscribe('periodListModification', (response) => this.loadAll());
    }


    private onError (error) {
        this.alertService.error(error.message, null, null);
    }
}
