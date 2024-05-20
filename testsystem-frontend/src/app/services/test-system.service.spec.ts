import { TestBed } from '@angular/core/testing';

import { TestSystemService } from './test-system.service';

describe('TestSystemService', () => {
  let service: TestSystemService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TestSystemService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
