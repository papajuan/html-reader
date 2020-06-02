import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class AddChannelDataService {

  host = 'http://localhost:8888'
  channel = {};

  constructor(
    private http: HttpClient
  ) { }

  executeAddChannel(channelLink, uri) {
    let params = new HttpParams().set("link", channelLink);
    this.channel = this.http.get(this.host + uri, {params: params});
  }
}
