import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService  } from 'ng-jhipster';

import { Period } from './period.model';
import { PeriodService } from './period.service';

@Component({
    selector: 'jhi-period-detail',
    templateUrl: './period-detail.component.html'
})
export class PeriodDetailComponent implements OnInit, OnDestroy {

    period: Period;
    private subscription: any;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private periodService: PeriodService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['period']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPeriods();
    }

    load(id) {
        this.periodService.find(id).subscribe((period) => {
            this.period = period;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPeriods() {
        this.eventSubscriber = this.eventManager.subscribe('periodListModification', (response) => this.load(this.period.id));
    }
}
