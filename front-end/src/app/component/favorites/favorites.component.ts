import { getCurrencySymbol } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { DashboardService } from 'src/app/service/dashboard.service';
import { JWTTokenService } from 'src/app/service/jwttoken.service';
import { LocalStorageService } from 'src/app/service/local-storage.service';
import { AirQualityModel } from '../model/main/airQuality/airQuality-model';
import { AirQualityListModel } from '../model/main/airQuality/airQualityList-model';
import { CurrentModel } from '../model/main/airQuality/Current-model';
import { DataModel } from '../model/main/airQuality/data-model';
import { LocationModel } from '../model/main/airQuality/location-model';
import { AirQualityModelV2 } from '../model/main/airQualityV2/airQualityV2-model';

@Component({
  selector: 'app-favorites',
  templateUrl: './favorites.component.html',
  styleUrls: ['./favorites.component.css']
})
export class FavoritesComponent implements OnInit {

  //testing favorite html component with some dummy fave array
  dummyFavoriteArray: String[] = ["Canada", "France", "Germany", "England", "Italy"]
  //----------
  favorites:AirQualityModel[]=[];

  toggledOn=true;
  heartColor="red";
  constructor( private dashboardService: DashboardService,private lss:LocalStorageService) {
  }

  ngOnInit(): void {
    
    this.getFavorites();
  
  }


  getFavorites() {
    const jwttoken = this.lss.get('jwttoken');
    console.log("in getFavorites" + jwttoken);
    if (jwttoken != null) {
      this.dashboardService.getFavorites(jwttoken).subscribe(
        data => {
          this.favorites = data['airQualityList'];
          // console.log(data['airQualityList']);
          
        },
        error => {
//           if (error.status === 401) {
//             this.lss.remove('jwttoken')
//           }
          this.lss.remove('jwttoken')
          console.log('failed to fetch favorites: ', error);
        }
      )
    }
  }

  public toggleFavorite(favorite: any):void{
    let temp = { country:favorite.data.country,
      state:favorite.data.state,
      city:favorite.data.city
    }
       
    this.toggledOn=!this.toggledOn;
    if(!this.toggledOn)
    {
      this.heartColor="white";
      this.dashboardService.removeFavoriteItem(this.lss.get('jwttoken'), temp).subscribe(
        (response) => {
          this.favorites = response;
          console.log(this.favorites);
        },
      );
    }
    if(this.toggledOn)
    {
      this.heartColor="red";
      this.dashboardService.addFavoriteItem(this.lss.get('jwttoken'), favorite).subscribe(
        (response) => {
          this.favorites = response;
          console.log(this.favorites);
          
        },
      );
    }
  }

  get heartColorValue(){
    return this.heartColor;
  }

  aqiValue(aqi:String):String{
    if (Number(aqi)<=50) return "green";
    if (Number(aqi)>50 && Number(aqi)<=100) return "yellow";
    else return "red";
  }


}
