import {BrowserModule} from "@angular/platform-browser";
import {NgModule} from "@angular/core";
import {FormsModule} from "@angular/forms";
import {HttpModule} from "@angular/http";
import {AppComponent} from "./app.component";
import {MenuComponent} from "./componant/menu/menu.component";
import {SearchComponent} from "./componant/search/search.component";
import {TempComponent} from "./componant/temp/temp.component";
import {VideoComponent} from "./componant/video/video.component";
import {ItemComponent} from "./componant/item/item.component";
import {RouterModule} from "@angular/router";
import {routes} from "./services/routes";
import {BaseComponent} from "./views/base/base.component";
import {HomeComponent} from "./views/home/home.component";
import { ErrorComponent } from './views/error/error.component';

@NgModule({
  declarations: [
    AppComponent,
    MenuComponent,
    SearchComponent,
    TempComponent,
    VideoComponent,
    ItemComponent,
    BaseComponent,
    HomeComponent,
    ErrorComponent,
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule, RouterModule.forRoot(routes)
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
