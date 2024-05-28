import {Component} from '@angular/core';
import {
    MatCard,
    MatCardContent,
    MatCardHeader,
    MatCardImage,
    MatCardModule,
    MatCardTitle
} from "@angular/material/card";
import {TokenStorageService} from "../../services/token-storage.service";
import {NgForOf, NgIf, NgOptimizedImage} from "@angular/common";
import {UserService} from "../../services/user.service";
import {UserWithTicketDto} from "../../models/user-with-ticket-dto";
import {
    MatAccordion, MatExpansionModule,
    MatExpansionPanel,
    MatExpansionPanelDescription,
    MatExpansionPanelTitle
} from "@angular/material/expansion";
import {MatLabel} from "@angular/material/form-field";

@Component({
    selector: 'app-profile',
    standalone: true,
    imports: [
        MatCard,
        MatCardHeader,
        MatCardContent,
        MatCardTitle,
        MatCardImage,
        NgOptimizedImage,
        MatCardModule,
        MatExpansionPanel,
        MatExpansionPanelTitle,
        NgForOf,
        NgIf,
        MatExpansionPanelDescription,
        MatAccordion,
        MatExpansionModule,
        MatLabel
    ],
    templateUrl: './profile.component.html',
    styleUrl: './profile.component.css'
})
export class ProfileComponent {
    currentUser!: UserWithTicketDto;

    constructor(private token: TokenStorageService, private userService: UserService) {
    }

    ngOnInit(): void {
        let userId = this.token.getUser().id;
        this.userService.getUserWithTicket(userId)
            .subscribe({
                next: (data) => {
                    this.currentUser = data;
                },
                error: err => {
                    alert("Ошибка получения данных! " + err.error);
                }
            });
    }
}
