package Data;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * UserOrder entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see Data.UserOrder
 * @author MyEclipse Persistence Tools
 */

public class UserOrderDAO extends HibernateDaoSupport {
	private static final Logger log = LoggerFactory
			.getLogger(UserOrderDAO.class);

	// property constants

	protected void initDao() {
		// do nothing
	}

	public void save(UserOrder transientInstance) {
		log.debug("saving UserOrder instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(UserOrder persistentInstance) {
		log.debug("deleting UserOrder instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public UserOrder findById(Data.UserOrderId id) {
		log.debug("getting UserOrder instance with id: " + id);
		try {
			UserOrder instance = (UserOrder) getHibernateTemplate().get(
					"Data.UserOrder", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(UserOrder instance) {
		log.debug("finding UserOrder instance by example");
		try {
			List results = getHibernateTemplate().findByExample(instance);
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding UserOrder instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from UserOrder as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}
	
	public List findByUser(User user) {
		log.debug("finding specific User's Reservation instances");
		try {
			List<UserOrder> alluserOrders = findAll();
			List<Order> thisusercurrentOrders = new ArrayList<Order>();
			for(UserOrder item:alluserOrders)
			{
				if(item.getId().getUser().getUserId().equals(user.getUserId()))
				{
					thisusercurrentOrders.add(item.getId().getOrder());
				}
			}
			return thisusercurrentOrders;
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	

	

	public List findAll() {
		log.debug("finding all UserOrder instances");
		try {
			String queryString = "from UserOrder";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public UserOrder merge(UserOrder detachedInstance) {
		log.debug("merging UserOrder instance");
		try {
			UserOrder result = (UserOrder) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(UserOrder instance) {
		log.debug("attaching dirty UserOrder instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(UserOrder instance) {
		log.debug("attaching clean UserOrder instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static UserOrderDAO getFromApplicationContext(ApplicationContext ctx) {
		return (UserOrderDAO) ctx.getBean("UserOrderDAO");
	}
}