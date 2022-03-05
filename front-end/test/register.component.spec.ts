import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { MatDialogModule, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { of } from 'rxjs';
import { DashboardService } from 'src/app/service/dashboard.service';
import { RegisterService } from 'src/app/service/register.service';

import { RegisterComponent } from '../src/app/component/register/register.component';

describe('RegisterComponent', () => {
  let component: RegisterComponent;
  let fixture: ComponentFixture<RegisterComponent>;
  let registerService: RegisterService;
  let registerServiceSpy:any;
  let dashboard: DashboardService;
  let matRef : MatDialogRef<RegisterComponent>;
  beforeEach(async () => {
      TestBed.configureTestingModule({
      declarations: [RegisterComponent],
      imports: [ HttpClientModule, ReactiveFormsModule, MatDialogModule,],
      providers: [RegisterService, DashboardService,  {provide: MatDialogRef, useValue: {}}, {MAT_DIALOG_DATA,useValue:{}}]
    })
      .compileComponents();
      registerService = TestBed.get(RegisterService);
      dashboard = TestBed.get(DashboardService);
      registerServiceSpy = spyOn(registerService, 'registerNewUser').and.callThrough();
  

  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RegisterComponent);
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
    expect(component.onCloseRegisterDialog).toBeTruthy();
  });

  // test to check onSubmit() method existence
  it('onSubmit() should exist ', () => {
    component.onSubmit();
    expect(component.onSubmit).toBeTruthy();
  });

  it('testing email field validity', () => {
    const id = component.registerForm.controls.id;
    id.setValue('someone@email.com');
    expect(id.valid).toBeTruthy();
  });

  it('testing password field validity', () => {
    const password = component.registerForm.controls.password;
    password.setValue('P@ssw0rd8');
    expect(password.valid).toBeTruthy();
  });

     // test to check onSubmit is calling RegisterService or not
    it('onSubmit() should call registerService to add a user', async () => {
      const user: any = {
        id: 'someesdsdsd1y73ykdnadbkmail@email.com',
        password: 'P@ssw0rd8'
      };

      const id = component.registerForm.controls.id;
      id.setValue(user.id);
      const password = component.registerForm.controls.password;
      password.setValue(user.password);
      component.onSubmit();
      fixture.detectChanges();
      await fixture.whenStable();
      expect(registerService.registerNewUser).toBeTruthy();
    });

});
