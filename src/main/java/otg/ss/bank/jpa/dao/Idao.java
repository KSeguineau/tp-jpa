package otg.ss.bank.jpa.dao;

import java.util.List;

public interface Idao<T, ID> {

	public T findByid(ID id);

	public List<T> findAll();

	public void save(T entity);

	public void update(T entity);

	public void remove(T entity);

}
