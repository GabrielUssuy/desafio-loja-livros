package com.ussuy.gabriel.desafiolojalivros.utils.annotation.uniquevalue;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.Assert;

import java.util.List;

public class UniqueValueValidator implements ConstraintValidator<UniqueValue, Object> {

    @PersistenceContext
    private EntityManager entityManager;

    private String domainAttribute;
    private Class<?> klass;

    @Override
    public void initialize(UniqueValue constraintAnnotation) {
        this.domainAttribute = constraintAnnotation.fieldName();
        this.klass = constraintAnnotation.domainClass();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        String sql = "SELECT 1 FROM "+klass.getName()+" WHERE "+domainAttribute+" = :value";
        Query query = entityManager.createQuery(sql);
        query.setParameter("value", value);
        List<?> list = query.getResultList();
        Assert.state(list.size() <= 1,
                "Unique value rule for class "+klass+" field "+domainAttribute+" was broken");
        return list.isEmpty();
    }
}
