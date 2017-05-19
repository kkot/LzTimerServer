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
        const copy = this.convert(period);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(period: Period): Observable<Period> {
        const copy = this.convert(period);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<Period> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            jsonResponse.beginTime = this.dateUtils
                .convertDateTimeFromServer(jsonResponse.beginTime);
            jsonResponse.endTime = this.dateUtils
                .convertDateTimeFromServer(jsonResponse.endTime);
            return jsonResponse;
        });
    }

    query(req?: any): Observable<Response> {
        const options = this.createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res))
        ;
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): Response {
        const jsonResponse = res.json();
        for (let i = 0; i < jsonResponse.length; i++) {
            jsonResponse[i].beginTime = this.dateUtils
                .convertDateTimeFromServer(jsonResponse[i].beginTime);
            jsonResponse[i].endTime = this.dateUtils
                .convertDateTimeFromServer(jsonResponse[i].endTime);
        }
        res.json().data = jsonResponse;
        return res;
    }

    private createRequestOption(req?: any): BaseRequestOptions {
        const options: BaseRequestOptions = new BaseRequestOptions();
        if (req) {
            const params: URLSearchParams = new URLSearchParams();
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

    private convert(period: Period): Period {
        const copy: Period = Object.assign({}, period);

        copy.beginTime = this.dateUtils.toDate(period.beginTime);

        copy.endTime = this.dateUtils.toDate(period.endTime);
        return copy;
    }
}
