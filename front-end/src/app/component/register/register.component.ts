import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { FormGroup, FormControl, Validators, FormBuilder } from '@angular/forms';

import { RegisterService } from '../../service/register.service';
import { DashboardService } from 'src/app/service/dashboard.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MainComponent } from '../main/main.component';
import { CompileClassMetadataFn } from '@angular/compiler';
import { CommonService } from 'src/app/service/common.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
})
export class RegisterComponent implements OnInit {
  registerForm:FormGroup;

  id:FormControl;

  password:FormControl;

  isFormValid=false;

  isInDialog=false;

  userexist:Boolean=false;

  successfulRegistered:boolean=false;
  isSuccessfulRegistered:Boolean=true;

  constructor(
      public registerDialogRef: MatDialogRef<RegisterComponent>,
      private rs:RegisterService,
      private dashboard: DashboardService,
      private commonService:CommonService,
      private formBuilder: FormBuilder
    ) {
    this.id = new FormControl('', [Validators.required, Validators.email]);
    // At least 8 characters in length, Lowercase letters, Uppercase letters, Numbers, Special characters
    this.password = new FormControl('', [Validators.required, Validators.pattern('(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[$@$!%*?&])[A-Za-z\d$@$!%*?&].{7,}')]);
      this.registerForm = this.formBuilder.group({
      id: this.id,
      password: this.password,
    });
  }

  onCloseRegisterDialog(): void {
    this.registerDialogRef.close();
  }

  async onSubmit() {
    this.isInDialog=true;
    if (this.registerForm.invalid) {
      console.log('in/valid form');
    } else {
      console.log("checking if user exist")
      this.dashboard.userExists(this.registerForm.get('id')?.value).subscribe(resp=>{console.log(" I am in com"+resp)})
      this.dashboard.userExists(this.registerForm.get('id')?.value).subscribe(
        data=>{
          this.userexist=data;
          if(!data) {
            console.log("user does not exist")
            this.isFormValid=true;
            this.rs.registerNewUser(this.registerForm.value).subscribe(
              (data) => {
                this.isSuccessfulRegistered=true;
                this.successfulRegistered=true;
                this.newData();
                console.log("this is just tested +");
                console.log('successful user registration');
              },
              (error) => {
                this.successfulRegistered=false;
                this.newData();
                this.isSuccessfulRegistered=false;
                console.log("this is just tested -");
                console.log('unsuccessful user registration');
              },
            );
            this.registerForm.reset();
            this.registerDialogRef.close();
          }
        }
      )
    }
  }

  getFormValidation(): boolean{
    return this.isFormValid;
  }
  getIsInDialog():boolean{
    return this.isInDialog
  }
  ngOnInit(): void {
    this.commonService.data$.subscribe(res => this.successfulRegistered = res)
  }
  get formEmail()
  {
    return this.registerForm.get('id');
  }
  get formPassword()
  {
    return this.registerForm.get('password')
  }
  //
  newData() {
    this.commonService.changeData(this.successfulRegistered);
  }
}

