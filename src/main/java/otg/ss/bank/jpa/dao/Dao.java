package otg.ss.bank.jpa.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

public class Dao<T, ID> implements Idao<T, ID> {

	private EntityManagerFactory emf = null;
	private EntityManager em = null;
	private Class<T> tClass;

	public Dao(Class<T> tClasse) {
		this.tClass = tClasse;
	}

	public void init() {
		if (emf == null || !emf.isOpen()) {
			emf = Persistence.createEntityManagerFactory("tp-jpa-bank");
		}
		if (em == null || !em.isOpen()) {
			em = emf.createEntityManager();
		}
	}

	public void remove(T entity) {
		this.init();
		em.getTransaction().begin();
		em.remove(entity);
		em.getTransaction().commit();
		this.close();
	}

	public void update(T entity) {
		this.init();
		em.getTransaction().begin();
		em.merge(entity);
		em.getTransaction().commit();
		this.close();
	}

	public void close() {
		if (emf != null)
			emf.close();
		if (em != null)
			em.close();
	}

	@Override
	public T findByid(ID id) {
		this.init();
		T result = (T) em.find(tClass, id);
		this.close();

		return result;
	}

	@Override
	public List<T> findAll() {
		this.init();

		StringBuilder sb = new StringBuilder();
		sb.append("FROM ").append(tClass.getSimpleName());
		TypedQuery<T> query = em.createQuery(sb.toString(), tClass);
		List<T> lEntity = query.getResultList();
		this.close();

		return lEntity;
	}

	@Override
	public void save(T entity) {
		this.init();
		em.getTransaction().begin();
		em.persist(entity);
		em.getTransaction().commit();
		this.close();
	}
}
