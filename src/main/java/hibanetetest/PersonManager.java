package hibanetetest;



import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import hibanetetest.Person;
import hibanetetest.HibernateUtil;

@SuppressWarnings("unused")
public class PersonManager {
	public static void main(String[] args) {
		new PersonManager().createAndStoreEvent();
		HibernateUtil.getSessionFactory().close();
	}

	private void createAndStoreEvent() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		// Person オブジェクトの永続化
		Person p1 = new Person();
		p1.setId(1);
		p1.setName("hoge");
		session.persist(p1);

		Person p2 = new Person();
		p2.setId(2);
		p2.setName("uga");
		session.persist(p2);

		// データの検索
		// 全件取得
		List<Person> persons = session.createCriteria(Person.class).list();
		for(Person p : persons) {
			System.out.println(p.getId() + ":" + p.getName());
		}

		// 条件指定して取得
		Criteria criteria = session.createCriteria(Person.class).add(Restrictions.eq("id", 2));
		Person p = (Person)criteria.uniqueResult();
		System.out.println(p.getId() + ":" + p.getName());

		session.getTransaction().commit();
	}
}