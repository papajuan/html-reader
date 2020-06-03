import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AddChannelDataService } from '../service/data/add-channel-data.service';
import { HttpErrorResponse } from '@angular/common/http';
import { rejects } from 'assert';
import { AvailableTagsDataService } from '../service/data/available-tags-data.service';

@Component({
  selector: 'app-add-channel',
  templateUrl: './add-channel.component.html',
  styleUrls: ['./add-channel.component.css']
})
export class AddChannelComponent implements OnInit {

  channelLink = '';
  availableTags = [];

  constructor(
    private route: ActivatedRoute,
    private addChannelService: AddChannelDataService,
    private availableTagsService: AvailableTagsDataService
  ) { }

  ngOnInit(): void {
  }

  sendChannelLink() {
    let id = this.addChannelService.executeAddChannel(this.channelLink);
    this.setAvailabelTags(id);
  }

  setAvailabelTags(id) {
    console.log(id);
    this.availableTags = this.availableTagsService.executeListAvailableTags(id);
    console.log(this.availableTags);
  }  
}
