import { TestBed } from '@angular/core/testing';

import { JWTTokenService } from 'src/app/service/jwttoken.service';

describe('JWTTokenService', () => {
  let service: JWTTokenService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(JWTTokenService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
