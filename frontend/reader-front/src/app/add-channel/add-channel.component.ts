import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AddChannelDataService } from '../service/data/add-channel-data.service';

@Component({
  selector: 'app-add-channel',
  templateUrl: './add-channel.component.html',
  styleUrls: ['./add-channel.component.css']
})
export class AddChannelComponent implements OnInit {

  channelLink = '';
  uri = '/addChannel';

  constructor(
    private route: ActivatedRoute,
    private service: AddChannelDataService
  ) { }

  ngOnInit(): void {
  }

  sendChannelLink() {
    this.service.executeAddChannel(this.channelLink, this.uri).subscribe();
  }

}
