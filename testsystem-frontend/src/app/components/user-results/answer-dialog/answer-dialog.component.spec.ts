import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AnswerDialogComponent } from './answer-dialog.component';

describe('AnswerDialogComponent', () => {
  let component: AnswerDialogComponent;
  let fixture: ComponentFixture<AnswerDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AnswerDialogComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AnswerDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
