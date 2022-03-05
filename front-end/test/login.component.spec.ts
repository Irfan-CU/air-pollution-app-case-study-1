import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { MatDialogModule, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { LoginComponent } from 'src/app/component/login/login.component';
import { DashboardService } from 'src/app/service/dashboard.service';
import { LoginService } from 'src/app/service/login.service';
import { RegisterService } from 'src/app/service/register.service';

fdescribe('LoginComponent', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;
  let loginService : LoginService;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LoginComponent ],
      imports: [ HttpClientModule, ReactiveFormsModule, MatDialogModule,],
      providers: [RegisterService, DashboardService,  {provide: MatDialogRef, useValue: {}}, {MAT_DIALOG_DATA,useValue:{}}, Router]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

   // test to check ngOnInit method existence
  it('ngOnInit() should exists', () => {
    expect(component.ngOnInit).toBeTruthy();
  });

  // test to check onCloseRegisterDialog method existence
  it('onCloseRegisterDialog() should exists', () => {
    expect(component.onCloseLoginDialog).toBeTruthy();
  });

  // test to check onSubmit() method existence
  it('onSubmit() should exist ', () => {
    component.onSubmit();
    expect(component.onSubmit).toBeTruthy();
  });

  it('testing email field validity', () => {
    const id = component.loginForm.controls.id;
    id.setValue('someone@email.com');
    expect(id.valid).toBeTruthy();
  });

  it('testing password field validity', () => {
    const password = component.loginForm.controls.password;
    password.setValue('P@ssw0rd8');
    expect(password.valid).toBeTruthy();
  });

     // test to check onSubmit is calling RegisterService or not
    it('onSubmit() should call loginuser to login a user',()=> {
      const user: any = {
        id: 'someesdsdsd1y73ykdnadbkmail@email.com',
        password: 'P@ssw0rd8'
      };

      const id = component.loginForm.controls.id;
      id.setValue(user.id);
      const password = component.loginForm.controls.password;
      password.setValue(user.password);
      component.onSubmit();
      expect(loginService.loginUser).toHaveBeenCalled;
    });
});
