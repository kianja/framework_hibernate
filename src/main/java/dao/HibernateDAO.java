package dao;

import java.util.*;
import java.lang.annotation.*;
import java.lang.reflect.*;
import java.sql.*;
import model.*;
import annotation.*;
import connexion.Connexion;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
public class HibernateDAO <T extends BaseModel> implements InterfaceDAO<T>
{
    private Class type = null;
	public HibernateDAO(T type)
	{
		this.type = type.getClass();
	}
    private String nomTable(Class cl)
	{
		Annotation[] annotations = cl.getAnnotations();
		for (Annotation annotation : annotations) {
			if (annotation instanceof DBTable) {
				DBTable monAnnotation = (DBTable) annotation;
				return monAnnotation.table();
			}
		}
		return null;
	}
    public SessionFactory sessionFactory=null;
    
    @Override
    public List<T> findAll() throws SQLException {
        List<T> result = new ArrayList<>();
        sessionFactory=new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        Session session=sessionFactory.getCurrentSession();
        Transaction tx = null;
        try {
                tx = session.beginTransaction();
                 result = session.createQuery("FROM '"+ nomTable(this.type)+"'").list();
                
        }
        
        finally {
            sessionFactory.close();
        }
        return result;

    }

    @Override
    public void findById(T condition) throws SQLException{
        sessionFactory=new Configuration().configure().buildSessionFactory();
        Session session=sessionFactory.getCurrentSession();
        Transaction tx = null;
        try {
                tx = session.beginTransaction();
                  session.createQuery("FROM Employee ");
                
        }
         
        finally {
            sessionFactory.close();
        }
       
    }
    @Override
    public void insert(T condition) throws SQLException{
        String file="/hibernate.cfg.xml";
         sessionFactory=new Configuration().configure(file).buildSessionFactory();
        Session session=sessionFactory.openSession();
        Transaction tx = null;
        try{
            tx=session.beginTransaction();
            session.save(condition);
            session.getTransaction().commit();
        }
        
        finally{
            sessionFactory.close();
        }

    }
    @Override
    public void update(T condition) throws SQLException{
        
    }
    @Override
    public void delete(T condition) throws SQLException{
        
    }
}