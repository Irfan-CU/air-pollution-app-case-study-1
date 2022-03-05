import { Component, Input, OnInit, Output, ViewChild, ViewEncapsulation} from '@angular/core';
import { DashboardService } from '../../service/dashboard.service';
import { AirQualityModel } from '../model/main/airQuality/airQuality-model';
import { LocalStorageService } from 'src/app/service/local-storage.service';
import {FormGroup, FormControl} from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { trigger, state, style, transition, animate } from '@angular/animations';

import { RecentsSearches } from '../model/main/recentSearches/recent-model';
import { AuthService } from 'src/app/service/auth/auth.service';
import { LoginService } from 'src/app/service/login.service';
import { FavoriteService } from 'src/app/service/favorite/favorite.service';
import { AirQualityModelV2} from '../model/main/airQualityV2/airQualityV2-model';
import { Observable } from 'rxjs';
import { RecentsService } from 'src/app/service/recentSearches/recents.service';
import {MatAccordion} from '@angular/material/expansion';
import { RegisterComponent } from '../register/register.component';
import { MatSnackBar } from '@angular/material/snack-bar';
import { CommonService } from 'src/app/service/common.service';
import Swal from 'sweetalert2';
import {GeolocationService} from '@ng-web-apis/geolocation';



@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css'],
  // Need to remove view encapsulation so that the custom tooltip style defined in
  // `tooltip-custom-class-example.css` will not be scoped to this component's view.
  encapsulation: ViewEncapsulation.None,
  animations: [
    trigger('flipState', [
      state('active', style({
        transform: 'rotateY(179deg)'
      })),
      state('inactive', style({
        transform: 'rotateY(0)'
      })),
      transition('active => inactive', animate('500ms ease-out')),
      transition('inactive => active', animate('500ms ease-in'))
    ])
  ]
})
export class MainComponent implements OnInit {
  // citiesArray: any[] = [];

  @ViewChild(MatAccordion) accordion!: MatAccordion;


  constructor(private dashboardService:DashboardService,
    private lss: LocalStorageService,
    private auth:AuthService,
    private ls:LoginService,
    private fav:FavoriteService,
    private recentsService: RecentsService,
    private snackBar: MatSnackBar,
    private commonService: CommonService,
    public dialog:MatDialog) { }

  selectedCountry:string='Canada';

  selectedState:string='Quebec';

  selectedCity:string='Montreal';

  countries: string[]= [];

  states: string[] =[];

  cities: string[] = [];

  myImage:String="/src/app/animations/lights-planet-earth-europe.jpg"

  recentSearch:RecentsSearches=new RecentsSearches();//{countryName:"Canada",stateName:"Ontaro",cityName:"Toronto",mainPoulltant:"P2"};

  recents:RecentsSearches[]=[];

  favorites: string[] = [];

  pollution!:AirQualityModel;

  pollutionV2:AirQualityModelV2 = new AirQualityModelV2();

  loggedIn!:boolean;

  isregistered!:Boolean;

  registerMessage:String="User is registered successfully!";

  undoMessage:String="Undo";

  panelOpenState:boolean = false;

  //@Input()
  successfulRegister:boolean=false;

  flip: string[] = ['inactive'];


  ngOnInit(): void {
  this.getRecents();
  this.getCountries();
  this.commonService.data$.subscribe(
    res => {
      this.successfulRegister = res
      if(res==true) {
        Swal.fire('Welcome to MyAirQuality app', 'Registration successfull!', 'success')
      }
  })
  if (this.countries.length===0){
    this.getPollutionData();
  }
  

  }

  public getCountries():void {

    this.dashboardService.getCountriesList().subscribe(
      (response) => {
        this.countries = response;
        
      },
    );
  }

  public getStates():void {
    this.dashboardService.getStatesList(this.selectedCountry).subscribe(
      (response) => {
        this.states = response;

      },
    );
  }

  public getCities():void {
    console.log("Calling Cities")

    this.dashboardService.getCitiesList(this.selectedCountry,this.selectedState).subscribe(
      (response) => {
        this.cities = response;

      },
    );
  }


  getLogIn():boolean{
    return this.auth.loggedIn;

  }

  getRecents(){
    this.recentsService.obsOfRecents.subscribe((response)=>{
      this.recents=response;
      console.log(this.recents[0].aqi);
    })
  }

  getPollutionData(){

    this.dashboardService.getPollutionData(this.selectedCountry,this.selectedState,this.selectedCity).subscribe(
      (response) => {


        if (this.selectedCountry==="USA"){
          this.pollutionV2 = response;
        }
        else{
          this.pollution = response;
        }
          this.addRecents();
      },
    );
  }

  addRecents(){
    let temp = new RecentsSearches;
          temp.countryName= this.selectedCountry;
          temp.stateName=this.selectedState;
          temp.cityName = this.selectedCity;
          if (this.selectedCountry==="USA"){
            temp.o3 = Number (this.pollutionV2.o3).toFixed(2);
            temp.aqi = this.pollutionV2.aqi;
            temp.pm10 = Number (this.pollutionV2.pm10).toFixed(2);
            temp.so2 = Number (this.pollutionV2.so2).toFixed(2);
            temp.no2 = Number (this.pollutionV2.no2).toFixed(2);
            temp.co= Number (this.pollutionV2.co).toFixed(2);            

          }
          else{
            temp.mainPoulltant = this.pollution.data.current.pollution.mainus;
            temp.aqi = (this.pollution.data.current.pollution.aqius);
            }

          this.recentsService.addRecents(temp)
  }

  removeRecents(recent:RecentsSearches){
    this.recentsService.removeRecent(recent);
  }



//   getFavorites() {
//     const jwttoken = this.lss.get('jwttoken');
//     console.log("in getFavorites" + jwttoken);
//     if (jwttoken != null) {
//       this.dashboardService.getFavorites(jwttoken).subscribe(
//         data => {
//           this.favorites = data;
//           console.log(data);
//         },
//         error => {
// //           if (error.status === 401) {
// //             this.lss.remove('jwttoken')
// //           }
//           this.lss.remove('jwttoken')
//           console.log('failed to fetch favorites: ', error);
//         }
//       )
//     }
//   }

  addtoFavorites(recent:RecentsSearches){

    let token = this.lss.get('jwttoken');
    console.log('addtoFavorites: ', token);

    this.fav.addfav(token!, {
      country: recent.countryName,
      state: recent.stateName,
      city: recent.cityName
    }).subscribe();

    this.removeRecents(recent);
  }



  ELEMENT_DATA: AirQualityModelV2[] = [
    // {aqi: 'adad', o3: 'Hydrogen', so2: "1.0079", no2: 'H',pm10:"asas",co:"aas"}
    {aqi: this.pollutionV2.aqi, o3: this.pollutionV2.o3, so2: this.pollutionV2.so2, no2: this.pollutionV2.no2 ,pm10:this.pollutionV2.pm10,co:this.pollutionV2.co}
  ];

  displayedColumns: string[] = ['aqi', 'o3', 'so2', 'no2', 'pm10', 'co'];
  dataSource = this.ELEMENT_DATA;

  step = 0;

  setStep(index: number) {
    this.step = index;
  }

  nextStep() {
    this.step++;
  }

  prevStep() {
    this.step--;
  }

  getSuccessfulRegister():boolean{
    console.log("inside successful register" + this.successfulRegister);
    return this.successfulRegister;
  }

  openRegisterDialog(): void {
    console.log("inside register dialog");
    const dialogRef = this.dialog.open(RegisterComponent, {
      width: '250px'
    })
  }

  aqiValue(aqi:String):String{
    if (Number(aqi)<=50) return "#9cd84e";
    if (Number(aqi)>50 && Number(aqi)<=100) return "#fdd64b";
    else return "#fe6a69";
  }
  aqiComment(aqi:String):String{
    if (Number(aqi)<=50) return "Good";
    if (Number(aqi)>50 && Number(aqi)<=100) return "Moderate";
    else return "Unhealthy";
  }

  greaterThan(aqi:String):boolean{
    if (Number(aqi)<=50) return false;
    if (Number(aqi)>50 && Number(aqi)<=100) return false;
    else return true;
  }

  color(aqi:String):String{
    if (Number(aqi)<=50) return "green";
    if (Number(aqi)>50 && Number(aqi)<=100) return "yellow";
    else return "red";
  }

  windowStatus(aqi:String):String{
    if (Number(aqi)<=50) return "Open your windows for fresh air.";
    else return "Close your windows to avoid  outside air.";
  }

  outDoorStatus(aqi:String):String{
    if (Number(aqi)<=50) return "Enjoy outdoor activites.";
    else return "Please take care and stay inside.";
  }

  nearestCity():String{
    return "Montreal"
  }

  toggleFlip(i:number) {
    this.flip[i] = (this.flip[i] == 'inactive') ? 'active' : 'inactive';
  }
}
