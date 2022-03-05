import { CurrentModel } from "./Current-model";
import { LocationModel } from "./location-model";

export class DataModel{
    
    city:String="";
    state:String="";
    country:String="";
    current!:CurrentModel;
    location!:LocationModel;

    constructor(){}

    
}