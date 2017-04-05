import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { MockBackend } from '@angular/http/testing';
import { Http, BaseRequestOptions } from '@angular/http';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils } from 'ng-jhipster';
import { JhiLanguageService } from 'ng-jhipster';
import { MockLanguageService } from '../../../helpers/mock-language.service';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { PeriodDetailComponent } from '../../../../../../main/webapp/app/entities/period/period-detail.component';
import { PeriodService } from '../../../../../../main/webapp/app/entities/period/period.service';
import { Period } from '../../../../../../main/webapp/app/entities/period/period.model';

describe('Component Tests', () => {

    describe('Period Management Detail Component', () => {
        let comp: PeriodDetailComponent;
        let fixture: ComponentFixture<PeriodDetailComponent>;
        let service: PeriodService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [PeriodDetailComponent],
                providers: [
                    MockBackend,
                    BaseRequestOptions,
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    {
                        provide: Http,
                        useFactory: (backendInstance: MockBackend, defaultOptions: BaseRequestOptions) => {
                            return new Http(backendInstance, defaultOptions);
                        },
                        deps: [MockBackend, BaseRequestOptions]
                    },
                    {
                        provide: JhiLanguageService,
                        useClass: MockLanguageService
                    },
                    PeriodService
                ]
            }).overrideComponent(PeriodDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PeriodDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PeriodService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Period(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.period).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
