package hotel.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class CrudDAO<ID extends Serializable, EntityClass> {

	private Class<EntityClass> entityClass;

	@PersistenceContext
	private EntityManager entityManager;


	@SuppressWarnings("unchecked")
	public CrudDAO() {
		Type type = getClass().getGenericSuperclass();
		ParameterizedType parameterizedType = null;
		
		while (parameterizedType == null) {
			if (type instanceof ParameterizedType) {
				parameterizedType = (ParameterizedType) type;
			} else
				type = ((Class<?>) type).getGenericSuperclass();
		}
		
		entityClass = (Class<EntityClass>) parameterizedType.getActualTypeArguments()[1];
	}

	public EntityClass findOne(ID id) {
		return entityManager.find(entityClass, id);
	}

	public EntityClass save(EntityClass entity) {
		return entityManager.merge(entity);
	}

	public void remove(ID id) {
		entityManager.remove(id);
	}

	public List<EntityClass> findAll() {
		return entityManager.createQuery("SELECT tmp from " + entityClass.getName() + " tmp", entityClass)
				.getResultList();
	}

}
