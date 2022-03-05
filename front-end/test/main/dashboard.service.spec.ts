import { TestBed } from '@angular/core/testing';
import { HttpEvent, HttpEventType } from '@angular/common/http';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { environment } from 'src/environments/environment';


import { DashboardService } from 'src/app/service/dashboard.service';

describe('AuthService', () => {
  let service: DashboardService;
  let httpMock: HttpTestingController;
  let Base_URL_V1 = "http://localhost:8080/airquality/airQualityInfo/api/v1";
  beforeEach(() => {
    TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [DashboardService],

    });
    httpMock = TestBed.get(HttpTestingController);
    service = TestBed.inject(DashboardService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
  afterEach(()=>{
    httpMock.verify();
  })

  it('test the service for getPollutionData method',()=>{
    
    service.getPollutionData("Canada","Quebec","Montreal").subscribe(res=>{
        expect(res.status).toBe("success");
        expect(res.data.city).toBe("Montreal");
        
    })
    
    const request = httpMock.expectOne(`${Base_URL_V1}/city?country=Canada&state=Quebec&city=Montreal`);
    expect(request.request.method).toBe('GET');
    // expect(request.request.headers.get('Content-Type')).toEqual('application/json');


  });

  it('test the service for getCountries method',()=>{
  
    service.getCountriesList().subscribe(res=>{
        expect(res.length).toBeGreaterThan(0);
        expect(res.data.includes("Canada"));
        
    })
    
    const request = httpMock.expectOne(`${Base_URL_V1}/supportedCountries`);
    expect(request.request.method).toBe('GET');
    // expect(request.request.headers.get('Content-Type')).toEqual('application/json');

  });

  it('test the service for getStates method',()=>{
  
    service.getStatesList("Canada").subscribe(res=>{
        expect(res.length).toBeGreaterThan(0);
        expect(res.data.includes("Quebec"));
        
    })
    
    const request = httpMock.expectOne(`${Base_URL_V1}/supportedStates?country=Canada`);
    expect(request.request.method).toBe('GET');
    // expect(request.request.headers.get('Content-Type')).toEqual('application/json');

  });

  it('test the service for getCities method',()=>{
  
    service.getCitiesList("Canada","Quebec").subscribe(res=>{
        expect(res.length).toBeGreaterThan(0);
        expect(res.data.includes("Montreal"));
        
    })
    
    const request = httpMock.expectOne(`${Base_URL_V1}/supportedCities?country=Canada&state=Quebec`);
    expect(request.request.method).toBe('GET');
    // expect(request.request.headers.get('Content-Type')).toEqual('application/json');

  });


});
