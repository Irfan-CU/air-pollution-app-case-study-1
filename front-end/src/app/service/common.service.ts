import { Injectable } from "@angular/core";
import { BehaviorSubject, Observable } from "rxjs";

@Injectable({
    providedIn: 'root'
  })
export class CommonService {
  private data = new BehaviorSubject(false);
  data$ = this.data.asObservable();

  private profileImage = new BehaviorSubject("");
  profileImage$ = this.profileImage.asObservable();

  changeData(data: boolean){
    this.data.next(data)
  }

  updateImage(imageUrl: any) {
    this.profileImage.next(imageUrl)
  }
}
