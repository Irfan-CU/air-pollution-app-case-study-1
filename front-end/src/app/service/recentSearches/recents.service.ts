import { Injectable } from '@angular/core';
import { Observable, of, Subject } from 'rxjs';
import { RecentsSearches } from 'src/app/component/model/main/recentSearches/recent-model';

@Injectable({
  providedIn: 'root'
})
export class RecentsService {

  constructor() {
  }

  
  recent!:RecentsSearches;
  recents:RecentsSearches[]=[];

  obsOfRecents=of(this.recents);

  addRecents(recentSearch:RecentsSearches){
    
    if (this.recents.length < 3) {
      this.recents.push(recentSearch);
    } else {
      this.recents.shift();
      this.recents.push(recentSearch);
      
    }
    
  }

  removeRecent (recent:RecentsSearches){
    this.recents.forEach( (item, index) => {
      if(item.cityName === recent.cityName) this.recents.splice(index,1);
    });
  }

  



}
