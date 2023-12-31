package com.library.database.entities;

/**
 * The {@code DBEntity} interface is a marker interface that represents an entity
 * in the database. Classes implementing this interface are expected to model
 * entities stored in the database.
 * <p>
 * This interface serves as a common base interface for all database entities
 * and is typically used to indicate that a class is an entity, allowing for
 * generic processing of entities in certain situations.
 * </p>
 * <p>
 * Implementing classes should provide appropriate implementations for
 * {@code equals}, {@code hashCode}, and any other relevant methods based on
 * the business logic of the entity.
 * </p>
 *
 * @see java.lang.Object#equals(Object)
 * @see java.lang.Object#hashCode()
 */
public interface DBEntity {
}
