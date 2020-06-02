import { TestBed } from '@angular/core/testing';

import { AddChannelDataService } from './add-channel-data.service';

describe('AddChannelDataService', () => {
  let service: AddChannelDataService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AddChannelDataService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
