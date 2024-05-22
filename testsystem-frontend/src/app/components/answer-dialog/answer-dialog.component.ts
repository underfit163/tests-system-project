import {Component, Inject} from '@angular/core';
import {
  MAT_DIALOG_DATA,
  MatDialogModule,
  MatDialogRef
} from "@angular/material/dialog";
import {MatListModule} from "@angular/material/list";
import {AnswerDto} from "../../models/answer-dto";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatButton} from "@angular/material/button";
import {MatLine} from "@angular/material/core";
import {NgForOf} from "@angular/common";

@Component({
  selector: 'app-answer-dialog',
  standalone: true,
  imports: [
    MatDialogModule,
    MatListModule,
    MatFormFieldModule,
    MatButton,
    MatLine,
    NgForOf
  ],
  templateUrl: './answer-dialog.component.html',
  styleUrl: './answer-dialog.component.css'
})
export class AnswerDialogComponent {
  constructor(
    public dialogRef: MatDialogRef<AnswerDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { answers: AnswerDto[] }) {}

  onClose(): void {
    this.dialogRef.close();
  }
}
