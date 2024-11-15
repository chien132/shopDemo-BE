package chien.demo.shopdemo.mapper;

import java.util.List;
import org.mapstruct.Context;
import org.mapstruct.Named;
import chien.demo.shopdemo.mapper.helper.CycleAvoidingMappingContext;
import chien.demo.shopdemo.model.AbstractAuditEntity;

/**
 * Abstract mapper.
 *
 * @param <D> Dto type
 * @param <E> Entity type
 */
public interface AbstractMapper<E extends AbstractAuditEntity, D> {

    E toEntityWithContext(D dto, @Context CycleAvoidingMappingContext context);

    @Named("defaultToEntity")
    default E toEntity(D dto) {
        return toEntityWithContext(dto, new CycleAvoidingMappingContext());
    }

    D toDtoWithContext(E entity, @Context CycleAvoidingMappingContext context);

    @Named("defaultToDto")
    default D toDto(E entity) {
        return toDtoWithContext(entity, new CycleAvoidingMappingContext());
    }

    List<E> toEntityListWithContext(List<D> dtoList, @Context CycleAvoidingMappingContext context);

    @Named("defaultToEntityList")
    default List<E> toEntityList(List<D> dtoList) {
        return toEntityListWithContext(dtoList, new CycleAvoidingMappingContext());
    }

    List<D> toDtoListWithContext(List<E> entityList, @Context CycleAvoidingMappingContext context);

    @Named("defaultToDtoList")
    default List<D> toDtoList(List<E> entityList) {
        return toDtoListWithContext(entityList, new CycleAvoidingMappingContext());
    }
}
