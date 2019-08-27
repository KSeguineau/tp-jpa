package otg.ss.bank.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import otg.ss.bank.jpa.dao.Dao;
import otg.ss.bank.jpa.domain.Address;
import otg.ss.bank.jpa.domain.Agency;
import otg.ss.bank.jpa.domain.ChargedAccount;
import otg.ss.bank.jpa.domain.SimpleAccount;
import otg.ss.bank.jpa.domain.Transaction;
import otg.ss.bank.jpa.domain.TransactionId;

public class App {

	public static void main(String... args) {

		EntityManagerFactory emf = null;
		EntityManager em = null;
		try {
			emf = Persistence.createEntityManagerFactory("tp-jpa-bank");
			em = emf.createEntityManager();
			em.getTransaction().begin();

			Agency agency = new Agency();
			Address address = new Address("13", "rue baker street", "44000", "Nantes");
			agency.setAddress(address);

			ChargedAccount cAccount = new ChargedAccount();
			agency.addAccount(cAccount);

			SimpleAccount sAccount = new SimpleAccount();
			agency.addAccount(sAccount);

			TransactionId ti = new TransactionId(cAccount.getId(), agency.getId());
			Transaction transaction = new Transaction();
			transaction.setId(ti);
			transaction.setAccount(sAccount);
			transaction.setAgency(agency);
			em.persist(agency);

			em.getTransaction().commit();

			Dao<Agency, Long> agencyDao = new Dao(Agency.class);
			System.out.println(agencyDao.findByid(1L));
			System.out.println(agencyDao.findAll());
		} finally {
			if (em != null)
				em.close();
			if (emf != null)
				emf.close();

		}
	}
}
