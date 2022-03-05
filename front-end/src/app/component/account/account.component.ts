import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { DomSanitizer } from '@angular/platform-browser';

import { AccountService } from 'src/app/service/account.service';
import { LocalStorageService } from 'src/app/service/local-storage.service';
import { CommonService } from 'src/app/service/common.service';

@Component({
  selector: 'app-account',
  templateUrl: './account.component.html',
  styleUrls: ['./account.component.css']
})
export class AccountComponent implements OnInit {
  accountPasswordForm:FormGroup
  accountImageForm:FormGroup

  password:FormControl
  image:FormControl

  file:File | undefined

  constructor(
      private as: AccountService,
      private lss: LocalStorageService,
      private sanitizer: DomSanitizer,
      private commonService: CommonService
  ) {
    this.password = new FormControl('', [Validators.required, Validators.pattern('(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[$@$!%*?&])[A-Za-z\d$@$!%*?&].{8,}')]);
    this.image = new FormControl('')

    this.accountPasswordForm = new FormGroup({
      password: this.password
    })

    this.accountImageForm = new FormGroup({
      image: this.image
    })
  }

  ngOnInit(): void {
  }

  onImageSubmit() {
    console.log("submitting image: ", this.file)
    this.as.upload(this.file!, this.lss.get('jwttoken')!).subscribe(data => {
        console.log("image uploaded successfully", data)
        let objectUrl = URL.createObjectURL(data);
        this.commonService.updateImage(this.sanitizer.bypassSecurityTrustUrl(objectUrl))
      },
      error => {
        console.log("could not upload image: ", error);
      }
    );
  }

  onPasswordSubmit() {
  }

  selectFile(event: any) {
    this.file = event.target.files[0];
//     if (this.file!.type.match('image.*')) {
//       var size = event.target.files[0].size;
//       if (size > 1000000) {
//         alert("size must not exceed 1 MB")
//         this.accountImageForm.reset()
//       } else {
//         this.file = event.target.files[0];
//       }
//     }
  }
}
