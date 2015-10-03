package click.rmx.persistence.repository;

import click.rmx.persistence.model.IEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.util.List;

@Deprecated
public abstract class AbstractRepository<E extends IEntity, ID extends Serializable> implements JpaRepository<E, ID> {

	@PersistenceContext
	private EntityManager em;

    protected EntityManager entityManager() {
        return em;
    }

    @Override
    public <S extends E> S save(S entity) {

			if (entity.getId() == null) {
				em.persist(entity);
				em.flush();
			} else {
				entity = em.merge(entity);
			}

		return entity;
	}

    @Override
    public E findOne(ID id) {
        return null;
    }

    @Override
    public boolean exists(ID id) {
        return false;
    }

    @Override
	public void delete(E entity)   {
		if (entity != null)
            em.remove(em.contains(entity) ? entity : em.merge(entity));
            em.flush();

	}

    @Override
    public void delete(Iterable<? extends E> iterable) {

    }

    @Override
    public void deleteAll() {

    }


    protected class TypedQueryPair {
		public final String query;
		public final Class<E> Class;
		public TypedQueryPair(String query, Class<E> Class) {
			this.query = query;
			this.Class = Class;
		}
	}

    protected abstract String FIND_ALL();
    protected abstract Class<E> CLASS();

	
	@Override
	public List<E> findAll()  {
			TypedQuery<E> query = em.createNamedQuery(FIND_ALL(), CLASS());
//			Query query = em.create
			List<E> entities = query.getResultList();
		return entities;
	}



}
