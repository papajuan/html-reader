import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AddChannelComponent } from './add-channel/add-channel.component';
import { AvailableTagsComponent } from './available-tags/available-tags.component';

@NgModule({
  declarations: [
    AppComponent,
    AddChannelComponent,
    AvailableTagsComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
