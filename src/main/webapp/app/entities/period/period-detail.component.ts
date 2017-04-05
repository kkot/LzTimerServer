import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { Period } from './period.model';
import { PeriodService } from './period.service';

@Component({
    selector: 'jhi-period-detail',
    templateUrl: './period-detail.component.html'
})
export class PeriodDetailComponent implements OnInit, OnDestroy {

    period: Period;
    private subscription: any;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private periodService: PeriodService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['period']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.periodService.find(id).subscribe(period => {
            this.period = period;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
