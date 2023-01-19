package com.example.user_ingredient_service.validation.pageable;

import com.example.user_ingredient_service.annotation.pageable.ValidSortProperty;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;

public class SortPropertyValidator implements ConstraintValidator<ValidSortProperty, Pageable> {

    private List<String> allowedProperties;

    @Override
    public void initialize(ValidSortProperty constraintAnnotation) {
        allowedProperties = Arrays.asList(constraintAnnotation.allowedProperties());
    }

    @Override
    public boolean isValid(Pageable value, ConstraintValidatorContext context) {

        if (value==null) {
            return true;
        }

        if (allowedProperties == null || allowedProperties.isEmpty()) {
            return true;
        }

        if (value.isUnpaged()) {
            return true;
        }

        Sort sort = value.getSort();

        if (sort.isUnsorted()) {
            return true;
        }

        List<String> notAllowedProperties = sort.stream().
                map(Sort.Order::getProperty).
                filter(property -> !allowedProperties.contains(property)).
                toList();

        return notAllowedProperties.isEmpty();
    }
}
