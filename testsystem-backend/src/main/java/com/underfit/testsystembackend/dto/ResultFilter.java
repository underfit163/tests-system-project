package com.underfit.testsystembackend.dto;

import com.underfit.testsystembackend.entity.Result;
import lombok.Builder;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

@Builder
public record ResultFilter(Long testId, Long userId, Integer scoreLte, Integer scoreGte, Integer countYearWorkLte,
                           Integer countYearWorkGte, Integer ageLte, Integer ageGte,
                           Long userWorkNumber, Boolean acceptResult) {
    public Specification<Result> toSpecification() {
        return Specification.where(testIdSpec())
                .and(userIdSpec())
                .and(scoreGteSpec())
                .and(scoreLteSpec())
                .and(userBirthdayGteSpec())
                .and(userBirthdayLteSpec())
                .and(userStartWorkGteSpec())
                .and(userStartWorkLteSpec())
                .and(userWorkNumberSpec())
                .and(acceptResultSpec());
    }

    private Specification<Result> testIdSpec() {
        return ((root, query, cb) -> testId != null
                ? cb.equal(root.get("test").get("id"), testId)
                : null);
    }

    private Specification<Result> userIdSpec() {
        return ((root, query, cb) -> userId != null
                ? cb.equal(root.get("user").get("id"), userId)
                : null);
    }

    private Specification<Result> scoreGteSpec() {
        return ((root, query, cb) -> scoreGte != null
                ? cb.greaterThanOrEqualTo(root.get("score"), scoreGte)
                : null);
    }

    private Specification<Result> scoreLteSpec() {
        return ((root, query, cb) -> scoreLte != null
                ? cb.lessThanOrEqualTo(root.get("score"), scoreLte)
                : null);
    }

    private Specification<Result> userBirthdayGteSpec() {
        return ((root, query, cb) -> ageGte != null
                ? cb.lessThanOrEqualTo(root.get("user").get("birthday"), LocalDate.now().minusYears(ageGte))
                : null);
    }

    private Specification<Result> userBirthdayLteSpec() {
        return ((root, query, cb) -> ageLte != null
                ? cb.greaterThanOrEqualTo(root.get("user").get("birthday"), LocalDate.now().minusYears(ageLte))
                : null);
    }

    private Specification<Result> userStartWorkGteSpec() {
        return ((root, query, cb) -> countYearWorkGte != null
                ? cb.lessThanOrEqualTo(root.get("user").get("startWork"), LocalDate.now().minusYears(countYearWorkGte))
                : null);
    }

    private Specification<Result> userStartWorkLteSpec() {
        return ((root, query, cb) -> countYearWorkLte != null
                ? cb.greaterThanOrEqualTo(root.get("user").get("startWork"), LocalDate.now().minusYears(countYearWorkLte))
                : null);
    }

    private Specification<Result> userWorkNumberSpec() {
        return ((root, query, cb) -> userWorkNumber != null
                ? cb.equal(root.get("user").get("workNumber"), userWorkNumber)
                : null);
    }

    private Specification<Result> acceptResultSpec() {
        return ((root, query, cb) -> acceptResult != null
                ? cb.equal(root.get("acceptResult"), acceptResult)
                : null);
    }
}