import { Component, Input, OnInit } from '@angular/core';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent {
  title = 'front-end';
  successfulRegister:Boolean=false;
  isUserRegistered(event:any)
  {
    console.log("inside app.ts"+event.target.value);
    this.successfulRegister=event.target.value;
  }

}
