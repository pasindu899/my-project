package com.aiken.pos.admin.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.aiken.pos.admin.model.Event;

/**
 * Template for generic CRUD operations,
 * T - Object
 * ID - Type
 *
 * @author Asela Liyanage
 * @version 1.0
 * @since 2021-01-29
 */

@Repository
public interface GenericRepository<T, ID> {

	/**
	 * Save a given model
	 *
	 * @param model - must not be null
	 * @return the saved model id, will never be null
	 * @exception IllegalArgumentException - in case the given model is null
	 */
	ID insert(T model) throws IllegalArgumentException;

	/**
	 * Save all given entities
	 *
	 * @param models - must not be null nor must it contain null
	 * @return the the saved models id's
	 * @exception IllegalArgumentException - in case the given models or one of its
	 *                                     entities is null
	 */
	List<ID> insertAll(List<T> models) throws IllegalArgumentException;

	/**
	 * Update a given model
	 *
	 * @param model - must not be null
	 * @return the updated model id; will never be null
	 * @exception IllegalArgumentException - in case the given model is null
	 */
	ID update(T model) throws IllegalArgumentException;

	/**
	 * Update all given entities
	 *
	 * @param models - must not be null nor must it contain null
	 * @return the the saved models id's; will never be null
	 * @exception IllegalArgumentException - in case the given models or one of its
	 *                                     entities is null
	 */
	List<ID> updateAll(List<T> models) throws IllegalArgumentException;

	/**
	 * Deletes the model with the given id
	 *
	 * @param id - must not be null
	 * @return the deleted model id; will never be null
	 * @exception IllegalArgumentException - in case the given id is null
	 */
	ID deleteById(ID id) throws IllegalArgumentException;

	/**
	 * Deletes a given model.
	 *
	 * @param model - must not be null.
	 * @return the deleted model id; will never be null
	 * @exception IllegalArgumentException - in case the given entity is null
	 */
	ID delete(T entity) throws IllegalArgumentException;

	/**
	 * Deletes the given models
	 *
	 * @param models - must not be null. Must not contain null elements
	 * @return the the deleted models id's; will never be null
	 * @exception IllegalArgumentException - in case the given entities or one of
	 *                                     its entities is null
	 */
	List<ID> deleteAll(List<T> models) throws IllegalArgumentException;

	/**
	 * Deletes all models managed by the repository
	 * 
	 * @return the the deleted models id's; will never be null
	 */
	List<ID> deleteAll();

	/**
	 * Returns all instances of the type
	 * 
	 * @return all models
	 */
	List<T> findAll();

	/**
	 * Returns@Override
	 all instances of the type T with the given IDs
	 *
	 * @param ids - must not be null nor contain any null values.
	 * @return guaranteed to be not null. The size can be equal or less than the
	 *         number of given id's
	 * @exception IllegalArgumentException -in case the given id's or one of its
	 *                                     items is null
	 */
	List<T> findAllById(List<String> ids) throws IllegalArgumentException;

	/**
	 * Retrieves an model by its id
	 *
	 * @param id - must not be null
	 * @return the model with the given id or Optional#empty() if none found
	 * @exception IllegalArgumentException - if id is null
	 */
	Optional<T> findById(ID id) throws IllegalArgumentException;
	
	/**
	 * Retrieves an model by its id
	 *
	 * @param key - must not be null
	 * @return the model with the given id or Optional#empty() if none found
	 * @exception IllegalArgumentException - if id is null
	 */
	Optional<T> findByKey(String key) throws IllegalArgumentException;

	/**
	 * Returns whether an model with the given id exists
	 *
	 * @param id - must not be null
	 * @return true if an model with the given id exists, false otherwise
	 * @exception IllegalArgumentException - if id is null
	 */
	boolean existsById(ID id) throws IllegalArgumentException;

	/**
	 * Returns the number of models available
	 * 
	 * @return the number of models 
	 */
	ID count();
	
	boolean existsByParams(T params) throws IllegalArgumentException;
	/**
	 * Returns the boolean value true if the event available
	 * 
	 * @return boolean 
	 */
	
	List<T> findAllByDates(List<String> params) throws IllegalArgumentException;
	/**
	 * Returns the List of Event between two given dates
	 * 
	 * @return List <Event> 
	 */

	List<Event> findAllByKey(String param) throws IllegalArgumentException;

	String deleteByKey(String key) throws IllegalArgumentException;

    String deleteByParams(List<String> params) throws IllegalArgumentException;
}