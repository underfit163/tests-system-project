import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminResultsComponent } from './admin-results.component';

describe('AdminResultsComponent', () => {
  let component: AdminResultsComponent;
  let fixture: ComponentFixture<AdminResultsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdminResultsComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AdminResultsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
