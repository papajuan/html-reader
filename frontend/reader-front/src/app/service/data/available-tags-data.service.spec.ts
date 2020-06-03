import { TestBed } from '@angular/core/testing';

import { AvailableTagsDataService } from './available-tags-data.service';

describe('AvailableTagsDataService', () => {
  let service: AvailableTagsDataService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AvailableTagsDataService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
