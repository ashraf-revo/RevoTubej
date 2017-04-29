import {Routes} from "@angular/router";
import {BaseComponent} from "../views/base/base.component";
import {HomeComponent} from "../views/home/home.component";
import {ErrorComponent} from "../views/error/error.component";
export const routes: Routes = [
  {
    path: '', component: BaseComponent, children: [
    {path: '', component: HomeComponent},
    {path: 'home', component: HomeComponent},
    {path: '**', component: ErrorComponent}
  ]
  }
];
