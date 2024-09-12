package com.vili.demo.core.request;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.vili.demo.core.exception.OperationNotSupportedException;
import com.vili.demo.core.util.CustomCaster;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;

import lombok.Getter;
import org.springframework.data.jpa.domain.Specification;

import com.vili.demo.core.domain.IEntity;

@Getter
public class SpecificationBuilder {

    private final Map<String, List<Filter>> filterMapping = new HashMap<>();

    private SpecificationBuilder(Collection<Filter> filters) {
        setFilterMapping(filters);
    }

    public void setFilterMapping(Collection<Filter> filters) {
        for (Filter filter : filters.stream().toList()) {
            String name = filter.fieldName();

            if (filterMapping.containsKey(name)) {
                List<Filter> list = new ArrayList<>(filterMapping.get(name));
                list.add(filter);
                filterMapping.replace(name, list);
            }
            else
                filterMapping.put(name, List.of(filter));
        }
    }

    public static SpecificationBuilder with(Collection<Filter> filters) {
        return new SpecificationBuilder(filters);
    }

    public <T extends IEntity> Specification<T> build() {
        if (getFilterMapping().isEmpty())
            return null;

        Specification<T> specification = Specification.where(null);

        for (Entry<String, List<Filter>> entry : getFilterMapping().entrySet()) {
            List<Filter> filters = entry.getValue();

            if (filters.size() > 1)
                specification = or(specification, filters);
            else
                specification = specification.and(createSpecification(filters.get(0)));
        }

        return specification;
    }

    private <T extends IEntity> Specification<T> or(Specification<T> specification, List<Filter> filters) {
        Specification<T> orSpec = createSpecification(filters.get(0));

        for (Filter filter : filters.subList(1, filters.size())) {
            orSpec = orSpec.or(createSpecification(filter));
        }

        return specification.and(orSpec);
    }

    @SuppressWarnings("unchecked")
    private <T extends IEntity> Specification<T> createSpecification(Filter filter) {
        return switch (filter.operator()) {
            case EQUALS -> (root, query, cb) -> {
                String[] path = filter.fieldName().split("\\.");
                Object filterValues = CustomCaster.castTo(filter.fieldType(), filter.value());
                int len = path.length;

                if (len > 1) {
                    Join<Object, Object> join = root.join(path[0]);

                    for (int i = 1; i < len - 1; i++) {
                        join = join.join(path[i]);
                    }

                    if (Collection.class.isAssignableFrom(filter.fieldType())) {
                        Object filterValue = ((Collection<Object>) filterValues).stream().toList().get(0);
                        return cb.equal(join.join(path[len - 1]), filterValue);
                    } else
                        return cb.equal(join.get(path[len - 1]), filterValues);
                }

                if (Collection.class.isAssignableFrom(filter.fieldType())) {
                    Object filterValue = ((Collection<Object>) filterValues).stream().toList().get(0);
                    return cb.equal(root.join(path[len - 1]), filterValue);
                } else
                    return cb.equal(root.get(path[len - 1]), filterValues);
            };
            case NOT_EQUALS -> (root, query, cb) -> {
                String[] path = filter.fieldName().split("\\.");
                Object filterValues = CustomCaster.castTo(filter.fieldType(), filter.value());
                int len = path.length;

                if (len > 1) {
                    Join<Object, Object> join = root.join(path[0]);

                    for (int i = 1; i < len - 1; i++) {
                        join = join.join(path[i]);
                    }

                    if (Collection.class.isAssignableFrom(filter.fieldType())) {
                        Object filterValue = ((Collection<Object>) filterValues).stream().toList().get(0);
                        return cb.notEqual(join.join(path[len - 1]), filterValue);
                    } else
                        return cb.notEqual(join.get(path[len - 1]), filterValues);
                }

                if (Collection.class.isAssignableFrom(filter.fieldType())) {
                    Object filterValue = ((Collection<Object>) filterValues).stream().toList().get(0);
                    return cb.notEqual(root.join(path[len - 1]), filterValue);
                } else
                    return cb.notEqual(root.get(path[len - 1]), filterValues);
            };
            case GREATER_THAN -> (root, query, cb) -> {
                String[] path = filter.fieldName().split("\\.");
                Object filterValues = CustomCaster.castTo(filter.fieldType(), filter.value());
                int len = path.length;

                if (len > 1) {
                    Join<Object, Object> join = root.join(path[0]);

                    for (int i = 1; i < len - 1; i++) {
                        join = join.join(path[i]);
                    }

                    if (Collection.class.isAssignableFrom(filter.fieldType())) {
                        Object filterValue = ((Collection<Object>) filterValues).stream().toList().get(0);

                        if (filterValue.getClass().isAssignableFrom(Date.class))
                            return cb.greaterThan(join.join(path[len - 1]), (Date) filterValue);
                        else
                            return cb.gt(join.join(path[len - 1]), (Number) filterValue);
                    } else {
                        if (filter.fieldType().isAssignableFrom(Date.class))
                            return cb.greaterThan(join.get(path[len - 1]), (Date) filterValues);
                        else
                            return cb.gt(join.get(path[len - 1]), (Number) filterValues);
                    }
                }

                if (Collection.class.isAssignableFrom(filter.fieldType())) {
                    Object filterValue = ((Collection<Object>) filterValues).stream().toList().get(0);

                    if (filterValue.getClass().isAssignableFrom(Date.class))
                        return cb.greaterThan(root.join(path[len - 1]), (Date) filterValue);
                    else
                        return cb.gt(root.join(path[len - 1]), (Number) filterValue);
                } else {
                    if (filter.fieldType().isAssignableFrom(Date.class))
                        return cb.greaterThan(root.get(path[len - 1]), (Date) filterValues);
                    else
                        return cb.gt(root.get(path[len - 1]), (Number) filterValues);
                }
            };
            case LESS_THAN -> (root, query, cb) -> {
                String[] path = filter.fieldName().split("\\.");
                Object filterValues = CustomCaster.castTo(filter.fieldType(), filter.value());
                int len = path.length;

                if (len > 1) {
                    Join<Object, Object> join = root.join(path[0]);

                    for (int i = 1; i < len - 1; i++) {
                        join = join.join(path[i]);
                    }

                    if (Collection.class.isAssignableFrom(filter.fieldType())) {
                        Object filterValue = ((Collection<Object>) filterValues).stream().toList().get(0);

                        if (filterValue.getClass().isAssignableFrom(Date.class))
                            return cb.lessThan(join.join(path[len - 1]), (Date) filterValue);
                        else
                            return cb.lt(join.join(path[len - 1]), (Number) filterValue);
                    } else {
                        if (filter.fieldType().isAssignableFrom(Date.class))
                            return cb.lessThan(join.get(path[len - 1]), (Date) filterValues);
                        else
                            return cb.lt(join.get(path[len - 1]), (Number) filterValues);
                    }
                }

                if (Collection.class.isAssignableFrom(filter.fieldType())) {
                    Object filterValue = ((Collection<Object>) filterValues).stream().toList().get(0);

                    if (filterValue.getClass().isAssignableFrom(Date.class))
                        return cb.lessThan(root.join(path[len - 1]), (Date) filterValue);
                    else
                        return cb.lt(root.join(path[len - 1]), (Number) filterValue);
                } else {
                    if (filter.fieldType().isAssignableFrom(Date.class))
                        return cb.lessThan(root.get(path[len - 1]), (Date) filterValues);
                    else
                        return cb.lt(root.get(path[len - 1]), (Number) filterValues);
                }
            };
            case GREATER_OR_EQUAL -> (root, query, cb) -> {
                String[] path = filter.fieldName().split("\\.");
                Object filterValues = CustomCaster.castTo(filter.fieldType(), filter.value());
                int len = path.length;

                if (len > 1) {
                    Join<Object, Object> join = root.join(path[0]);

                    for (int i = 1; i < len - 1; i++) {
                        join = join.join(path[i]);
                    }

                    if (Collection.class.isAssignableFrom(filter.fieldType())) {
                        Object filterValue = ((Collection<Object>) filterValues).stream().toList().get(0);

                        if (filterValue.getClass().isAssignableFrom(Date.class))
                            return cb.greaterThanOrEqualTo(join.join(path[len - 1]), (Date) filterValue);
                        else
                            return cb.ge(join.join(path[len - 1]), (Number) filterValue);
                    } else {
                        if (filter.fieldType().isAssignableFrom(Date.class))
                            return cb.greaterThanOrEqualTo(join.get(path[len - 1]), (Date) filterValues);
                        else
                            return cb.ge(join.get(path[len - 1]), (Number) filterValues);
                    }
                }

                if (Collection.class.isAssignableFrom(filter.fieldType())) {
                    Object filterValue = ((Collection<Object>) filterValues).stream().toList().get(0);

                    if (filterValue.getClass().isAssignableFrom(Date.class))
                        return cb.greaterThanOrEqualTo(root.join(path[len - 1]), (Date) filterValue);
                    else
                        return cb.ge(root.join(path[len - 1]), (Number) filterValue);
                } else {
                    if (filter.fieldType().isAssignableFrom(Date.class))
                        return cb.greaterThanOrEqualTo(root.get(path[len - 1]), (Date) filterValues);
                    else
                        return cb.ge(root.get(path[len - 1]), (Number) filterValues);
                }
            };
            case LESS_OR_EQUAL -> (root, query, cb) -> {
                String[] path = filter.fieldName().split("\\.");
                Object filterValues = CustomCaster.castTo(filter.fieldType(), filter.value());
                int len = path.length;

                if (len > 1) {
                    Join<Object, Object> join = root.join(path[0]);

                    for (int i = 1; i < len - 1; i++) {
                        join = join.join(path[i]);
                    }

                    if (Collection.class.isAssignableFrom(filter.fieldType())) {
                        Object filterValue = ((Collection<Object>) filterValues).stream().toList().get(0);

                        if (filterValue.getClass().isAssignableFrom(Date.class))
                            return cb.lessThanOrEqualTo(join.join(path[len - 1]), (Date) filterValue);
                        else
                            return cb.le(join.join(path[len - 1]), (Number) filterValue);
                    } else {
                        if (filter.fieldType().isAssignableFrom(Date.class))
                            return cb.lessThanOrEqualTo(join.get(path[len - 1]), (Date) filterValues);
                        else
                            return cb.le(join.get(path[len - 1]), (Number) filterValues);
                    }
                }

                if (Collection.class.isAssignableFrom(filter.fieldType())) {
                    Object filterValue = ((Collection<Object>) filterValues).stream().toList().get(0);

                    if (filterValue.getClass().isAssignableFrom(Date.class))
                        return cb.lessThanOrEqualTo(root.join(path[len - 1]), (Date) filterValue);
                    else
                        return cb.le(root.join(path[len - 1]), (Number) filterValue);
                } else {
                    if (filter.fieldType().isAssignableFrom(Date.class))
                        return cb.lessThanOrEqualTo(root.get(path[len - 1]), (Date) filterValues);
                    else
                        return cb.le(root.get(path[len - 1]), (Number) filterValues);
                }
            };
            case BETWEEN -> (root, query, cb) -> {
                String[] path = filter.fieldName().split("\\.");
                List<Object> filterValues = CustomCaster.castTo(filter.fieldType(), filter.values());
                int len = path.length;

                if (len > 1) {
                    Join<Object, Object> join = root.join(path[0]);

                    for (int i = 1; i < len - 1; i++) {
                        join = join.join(path[i]);
                    }

                    if (Collection.class.isAssignableFrom(filter.fieldType())) {
                        Object filterValue1 = ((Collection<Object>) filterValues.get(0)).stream().toList().get(0);
                        Object filterValue2 = ((Collection<Object>) filterValues.get(1)).stream().toList().get(0);

                        if (filterValue1.getClass().isAssignableFrom(Date.class))
                            return cb.and(
                                    cb.lessThanOrEqualTo(join.join(path[len - 1]), (Date) filterValue1),
                                    cb.greaterThanOrEqualTo(join.join(path[len - 1]), (Date) filterValue2));
                        else
                            return cb.and(
                                    cb.le(join.join(path[len - 1]), (Number) filterValue1),
                                    cb.ge(join.join(path[len - 1]), (Number) filterValue2));
                    } else {
                        if (filter.fieldType().isAssignableFrom(Date.class))
                            return cb.and(
                                    cb.lessThanOrEqualTo(join.get(path[len - 1]), (Date) filterValues.get(1)),
                                    cb.greaterThanOrEqualTo(join.get(path[len - 1]), (Date) filterValues.get(0)));
                        else
                            return cb.and(
                                    cb.le(join.get(path[len - 1]), (Number) filterValues.get(1)),
                                    cb.ge(join.get(path[len - 1]), (Number) filterValues.get(0)));
                    }
                }

                if (Collection.class.isAssignableFrom(filter.fieldType())) {
                    Object filterValue1 = ((Collection<Object>) filterValues.get(0)).stream().toList().get(0);
                    Object filterValue2 = ((Collection<Object>) filterValues.get(1)).stream().toList().get(0);

                    if (filterValue1.getClass().isAssignableFrom(Date.class))
                        return cb.and(
                                cb.lessThanOrEqualTo(root.join(path[len - 1]), (Date) filterValue1),
                                cb.greaterThanOrEqualTo(root.join(path[len - 1]), (Date) filterValue2));
                    else
                        return cb.and(
                                cb.le(root.join(path[len - 1]), (Number) filterValue1),
                                cb.ge(root.join(path[len - 1]), (Number) filterValue2));

                } else {
                    if (filter.fieldType().isAssignableFrom(Date.class))
                        return cb.and(
                                cb.lessThanOrEqualTo(root.get(path[len - 1]), (Date) filterValues.get(1)),
                                cb.greaterThanOrEqualTo(root.get(path[len - 1]), (Date) filterValues.get(0)));
                    else
                        return cb.and(
                                cb.le(root.get(path[len - 1]), (Number) filterValues.get(1)),
                                cb.ge(root.get(path[len - 1]), (Number) filterValues.get(0)));
                }
            };
            case LIKE -> (root, query, cb) -> {
                String[] path = filter.fieldName().split("\\.");
                Object filterValues = CustomCaster.castTo(filter.fieldType(), filter.value());
                int len = path.length;

                if (len > 1) {
                    Join<Object, Object> join = root.join(path[0]);

                    for (int i = 1; i < len - 1; i++) {
                        join = join.join(path[i]);
                    }

                    if (Collection.class.isAssignableFrom(filter.fieldType())) {
                        Collection<Object> filterCollection = (Collection<Object>) filterValues;
                        Predicate where = cb.disjunction();
                        for (Object filterValue : filterCollection) {
                            where = cb.or(where, cb.like(join.join(path[len - 1]), "%" + filterValue + "%"));
                        }
                        return where;
                    } else
                        return cb.like(join.get(path[len - 1]), "%" + filterValues + "%");
                }

                if (Collection.class.isAssignableFrom(filter.fieldType())) {
                    Collection<Object> filterCollection = (Collection<Object>) filterValues;
                    Predicate where = cb.disjunction();
                    for (Object filterValue : filterCollection) {
                        where = cb.or(where, cb.like(root.join(path[len - 1]), "%" + filterValue + "%"));
                    }
                    return where;
                } else
                    return cb.like(root.get(path[len - 1]), "%" + filterValues + "%");
            };
            case ENDS_WITH -> (root, query, cb) -> {
                String[] path = filter.fieldName().split("\\.");
                Object filterValues = CustomCaster.castTo(filter.fieldType(), filter.value());
                int len = path.length;

                if (len > 1) {
                    Join<Object, Object> join = root.join(path[0]);

                    for (int i = 1; i < len - 1; i++) {
                        join = join.join(path[i]);
                    }

                    if (Collection.class.isAssignableFrom(filter.fieldType())) {
                        Collection<Object> filterCollection = (Collection<Object>) filterValues;
                        Predicate where = cb.disjunction();
                        for (Object filterValue : filterCollection) {
                            where = cb.or(where, cb.like(join.join(path[len - 1]), "%" + filterValue));
                        }
                        return where;
                    } else
                        return cb.like(join.get(path[len - 1]), "%" + filterValues);
                }

                if (Collection.class.isAssignableFrom(filter.fieldType())) {
                    Collection<Object> filterCollection = (Collection<Object>) filterValues;
                    Predicate where = cb.disjunction();
                    for (Object filterValue : filterCollection) {
                        where = cb.or(where, cb.like(root.join(path[len - 1]), "%" + filterValue));
                    }
                    return where;
                } else
                    return cb.like(root.get(path[len - 1]), "%" + filterValues);
            };
            case STARTS_WITH -> (root, query, cb) -> {
                String[] path = filter.fieldName().split("\\.");
                Object filterValues = CustomCaster.castTo(filter.fieldType(), filter.value());
                int len = path.length;

                if (len > 1) {
                    Join<Object, Object> join = root.join(path[0]);

                    for (int i = 1; i < len - 1; i++) {
                        join = join.join(path[i]);
                    }

                    if (Collection.class.isAssignableFrom(filter.fieldType())) {
                        Collection<Object> filterCollection = (Collection<Object>) filterValues;
                        Predicate where = cb.disjunction();
                        for (Object filterValue : filterCollection) {
                            where = cb.or(where, cb.like(join.join(path[len - 1]), filterValue + "%"));
                        }
                        return where;
                    } else
                        return cb.like(join.get(path[len - 1]), filterValues + "%");
                }

                if (Collection.class.isAssignableFrom(filter.fieldType())) {
                    Collection<Object> filterCollection = (Collection<Object>) filterValues;
                    Predicate where = cb.disjunction();
                    for (Object filterValue : filterCollection) {
                        where = cb.or(where, cb.like(root.join(path[len - 1]), filterValue + "%"));
                    }
                    return where;
                } else
                    return cb.like(root.get(path[len - 1]), filterValues + "%");
            };
            case IN -> (root, query, cb) -> {
                String[] path = filter.fieldName().split("\\.");
                List<Object> filterValues = CustomCaster.castTo(filter.fieldType(), filter.values());
                int len = path.length;

                if (len > 1) {
                    Join<Object, Object> join = root.join(path[0]);

                    for (int i = 1; i < len - 1; i++) {
                        join = join.join(path[i]);
                    }

                    if (Collection.class.isAssignableFrom(filter.fieldType())) {
                        Predicate where = cb.disjunction();
                        for (Object filterValue : filterValues) {
                            where = cb.or(where, cb.equal(join.join(path[len - 1]), filterValue));
                        }
                        return where;
                    } else
                        return cb.in(join.get(path[len - 1])).value(filterValues);
                }

                if (Collection.class.isAssignableFrom(filter.fieldType())) {
                    Predicate where = cb.disjunction();
                    for (Object filterValue : filterValues) {
                        where = cb.or(where, cb.equal(root.join(path[len - 1]), filterValue));
                    }
                    return where;
                } else
                    return cb.in(root.get(path[len - 1])).value(filterValues);
            };
            default -> throw new OperationNotSupportedException(filter.operator());
        };
    }
}