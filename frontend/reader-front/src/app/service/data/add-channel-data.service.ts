import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { environment } from 'src/environments/environment';  


@Injectable({
  providedIn: 'root'
})

export class AddChannelDataService {

  host = environment.apiUrl;
  uri = "/addChannel";
  response;

  constructor(
    private http: HttpClient
  ) { }

  executeAddChannel(channelLink) {
    let params = new HttpParams().set("link", channelLink);
    this.http.get(this.host + this.uri, {params: params}).subscribe(response => this.response = response);
    return this.response;
  }
}
