import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { AuthService } from 'src/app/service/auth/auth.service';
import { CommonService } from 'src/app/service/common.service';

import { LoginComponent } from '../../login/login.component';
import { RegisterComponent } from '../../register/register.component';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  @Output() public sidenavToggle = new EventEmitter();
  loggedIn:boolean= false;
  imageUrl:string="";

  constructor(
      public dialog:MatDialog,
      private authService:AuthService,
      private commonService:CommonService
  ) { }

  ngOnInit(): void {
    this.commonService.profileImage$.subscribe((image:any) => this.imageUrl = image)
  }

  public onToggleSidenav = () => {
    this.sidenavToggle.emit();
  }

  openRegisterDialog(): void {
    console.log("inside register dialog");
    const dialogRef = this.dialog.open(RegisterComponent, {
      width: '250px'
    })
  }

  openLoginDialog(): void {
    console.log("inside login dialog");
    console.log("Thevalue of the loggedIn is "+this.loggedIn);
    const dialogRef = this.dialog.open(LoginComponent, {
      width: '250px'
    })
  }

  checklogged(): boolean{
    return this.loggedIn || this.authService.loggedIn;
  }

  onLogOut(){
    this.authService.logOut();
  }

  imageAdded(event:any) {
    this.imageUrl = event.value
  }
}
