import { Injectable } from '@angular/core';
import { Http, Response, URLSearchParams, BaseRequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { Period } from './period.model';
import { DateUtils } from 'ng-jhipster';
@Injectable()
export class PeriodService {

    private resourceUrl = 'api/periods';

    constructor(private http: Http, private dateUtils: DateUtils) { }

    create(period: Period): Observable<Period> {
        let copy: Period = Object.assign({}, period);
        copy.startTime = this.dateUtils.toDate(period.startTime);
        copy.stopTime = this.dateUtils.toDate(period.stopTime);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(period: Period): Observable<Period> {
        let copy: Period = Object.assign({}, period);

        copy.startTime = this.dateUtils.toDate(period.startTime);

        copy.stopTime = this.dateUtils.toDate(period.stopTime);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<Period> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            let jsonResponse = res.json();
            jsonResponse.startTime = this.dateUtils
                .convertDateTimeFromServer(jsonResponse.startTime);
            jsonResponse.stopTime = this.dateUtils
                .convertDateTimeFromServer(jsonResponse.stopTime);
            return jsonResponse;
        });
    }

    query(req?: any): Observable<Response> {
        let options = this.createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: any) => this.convertResponse(res))
        ;
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }


    private convertResponse(res: any): any {
        let jsonResponse = res.json();
        for (let i = 0; i < jsonResponse.length; i++) {
            jsonResponse[i].startTime = this.dateUtils
                .convertDateTimeFromServer(jsonResponse[i].startTime);
            jsonResponse[i].stopTime = this.dateUtils
                .convertDateTimeFromServer(jsonResponse[i].stopTime);
        }
        res._body = jsonResponse;
        return res;
    }

    private createRequestOption(req?: any): BaseRequestOptions {
        let options: BaseRequestOptions = new BaseRequestOptions();
        if (req) {
            let params: URLSearchParams = new URLSearchParams();
            params.set('page', req.page);
            params.set('size', req.size);
            if (req.sort) {
                params.paramsMap.set('sort', req.sort);
            }
            params.set('query', req.query);

            options.search = params;
        }
        return options;
    }
}
