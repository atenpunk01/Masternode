package co.th.aten.network.producer;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author Mai
 */
public class EntityManagerProducer {
//	@Produces
//	@ExtensionManaged
//	@ConversationScoped
//	@PersistenceUnit(unitName = "epi-default")
//	EntityManagerFactory emf;

	@Produces
	@DBDefault
	@PersistenceContext(unitName = "default")
	EntityManager em;

}
