import { HttpClientModule } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';

import { CityService } from './CityApi.service';

describe('CityApiService', () => {
  let service: CityService;

  beforeEach(() => {
    TestBed.configureTestingModule({imports: [HttpClientModule]});
    service = TestBed.inject(CityService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
