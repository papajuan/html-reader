import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AvailableTagsComponent } from './available-tags.component';

describe('AvailableTagsComponent', () => {
  let component: AvailableTagsComponent;
  let fixture: ComponentFixture<AvailableTagsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AvailableTagsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AvailableTagsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
