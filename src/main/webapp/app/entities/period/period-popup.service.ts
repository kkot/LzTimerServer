import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { Period } from './period.model';
import { PeriodService } from './period.service';
@Injectable()
export class PeriodPopupService {
    private isOpen = false;
    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private periodService: PeriodService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.periodService.find(id).subscribe((period) => {
                period.beginTime = this.datePipe
                    .transform(period.beginTime, 'yyyy-MM-ddThh:mm');
                period.endTime = this.datePipe
                    .transform(period.endTime, 'yyyy-MM-ddThh:mm');
                this.periodModalRef(component, period);
            });
        } else {
            return this.periodModalRef(component, new Period());
        }
    }

    periodModalRef(component: Component, period: Period): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.period = period;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        });
        return modalRef;
    }
}
