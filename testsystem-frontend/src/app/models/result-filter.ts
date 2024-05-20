export interface ResultFilter {
    testId: number;
    userId: number;
    scoreLte: number;
    scoreGte: number;
    countYearWorkLte: number;
    countYearWorkGte: number;
    ageLte: number;
    ageGte: number;
    userWorkNumber: number;
    acceptResult: boolean;
}
