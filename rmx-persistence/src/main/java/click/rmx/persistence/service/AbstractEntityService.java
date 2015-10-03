package click.rmx.persistence.service;


import click.rmx.debug.RMXException;
import click.rmx.persistence.model.IEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractEntityService<E extends IEntity> implements EntityService<E> {

	private List<E> entities = new ArrayList<>();
	private Map<String,String> seriousErrors = new HashMap<>();
	protected String errorLog = "";


	protected abstract JpaRepository<E, Long> repository();

	@Override
	@Transactional
	public List<E> findAllEntities() {
		try {
			List<E> newList = (List<E>) repository().findAll();
			if ( newList != null )
				this.entities = newList;
		} catch (Exception e) {
			this.addError(RMXException.unexpected(e));
		}
		return this.entities;
	}

	@Override
	public String getErrors() {
		String log = "ERRORS: " + this.errorLog;
		for (String error : seriousErrors.values()) {
			log += "\n" + error;
		}
		this.errorLog = "";
		return log;//.replace("\n", "<br/>");
	}

	@Override
	@Transactional
	public E save(E entity) {
		try {
			this.getEntities().add(entity);
			return repository().save(entity);
		} catch (Exception e) {
			this.addError(RMXException.unexpected(e));
		}
		return entity;
	}


	
	@Override
	@Transactional
	public boolean remove(E entity) {
		try { 
			if (entities.contains(entity))
				entities.remove(entity);
			repository().delete(entity);
			return true;
		} catch (Exception e) {
			this.addError(RMXException.unexpected(e));
		} 
		return false;
	}
	
	@Override
	@Transactional
	public List<E> removeAll() {
		for (E entity : entities) {
			try {
				this.repository().delete(entity);
			} catch (Exception e) {
				this.addError(RMXException.unexpected(e));
			}
		}
		entities = null;
		return this.findAllEntities();
	}
	
	public RMXException addError(RMXException e) {
		if (e.isSerious())
			this.seriousErrors.put(e.getCause().getLocalizedMessage(),e.html());
		else 
			this.errorLog += "<br/>" + e.html();
		return e;
	}
	
	@Override
	@Transactional
	public List<E> synchronize() {
		try {
//			List<E> qList = repository().loadAll();
			for (E entity : entities) {
				try {
					repository().save(entity);//.synchronize(entity);
				} catch (Exception e) {
					addError(RMXException.unexpected(e,"Could not sync " + entity));
				}
			}
//			getEntities().clear();
//			for (E entity : qList) {
//				entities.add(entity);
//			}
//			return entities;
		} catch (Exception e) {
			this.addError(RMXException.unexpected(e));
		}
		return this.findAllEntities();
		
	}

	/**
	 * @return the entities
	 */
	public List<E> getEntities() {
		return entities;
	}



}
