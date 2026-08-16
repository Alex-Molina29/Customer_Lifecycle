package com.bancobogota.customersKata.customers.infrastructure.specification;

import com.bancobogota.customersKata.customers.domain.Customer;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class CustomerSpecification {

    private CustomerSpecification() {
    }

    public static Specification<Customer> withFilters(
            String documentType,
            String documentNumber,
            String name,
            String email,
            String phoneNumber,
            String search
    ) {
        return (root, query, cb) -> {
            if(StringUtils.hasText(search)){
                String pattern = "%" + search.toLowerCase() + "%";
                return cb.or(
                        cb.like(cb.lower(root.get("documentNumber")), pattern),
                        cb.like(cb.lower(root.get("name")), pattern),
                        cb.like(cb.lower(root.get("email")), pattern),
                        cb.like(cb.lower(root.get("phoneNumber")), pattern),
                        cb.like(cb.lower(root.get("documentType")), pattern)
                );
            }


            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.hasText(documentType)) {
                predicates.add(cb.equal(
                        cb.lower(root.get("documentType")),
                        documentType.toLowerCase()));
            }
            if (StringUtils.hasText(documentNumber)) {
                predicates.add(cb.like(
                        cb.lower(root.get("documentNumber")),
                        "%" + documentNumber.toLowerCase() + "%"));
            }
            if (StringUtils.hasText(name)) {
                predicates.add(cb.like(
                        cb.lower(root.get("name")),
                        "%" + name.toLowerCase() + "%"));
            }
            if (StringUtils.hasText(email)) {
                predicates.add(cb.like(
                        cb.lower(root.get("email")),
                        "%" + email.toLowerCase() + "%"));
            }
            if (StringUtils.hasText(phoneNumber)) {
                predicates.add(cb.like(
                        cb.lower(root.get("phoneNumber")),
                        "%" + phoneNumber.toLowerCase() + "%"));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}