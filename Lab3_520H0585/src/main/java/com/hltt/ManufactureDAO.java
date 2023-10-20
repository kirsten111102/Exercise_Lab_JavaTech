package com.hltt;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.List;

public class ManufactureDAO {
    private org.hibernate.SessionFactory SessionFactory;
    private Session session;
    private Transaction transaction;

    public ManufactureDAO() throws HibernateException {
        SessionFactory = new Configuration().configure().
                addAnnotatedClass(Phone.class).
                addAnnotatedClass(Manufacture.class).
                buildSessionFactory();

        session = SessionFactory.getCurrentSession();

        transaction = session.beginTransaction();
    }
    public boolean add(Manufacture p){
        try{
            session.persist(p);
            return true;
        }
        catch(Exception e){
            return false;
        }
    }
    public Manufacture get(String id){
        try{
            Query qr = session.createQuery("from Manufacture m where m.id = :id", Manufacture.class);
            qr.setParameter("id", id);
            Manufacture manufacture = (Manufacture) qr.list().get(0);
            return manufacture;
        }
        catch (Exception e){
            return null;
        }
    }
    public List<Manufacture> getAll(){
        try{
            Query qr = session.createQuery("from Manufacture", Manufacture.class);
            List<Manufacture> manufactureList = qr.list();
            return manufactureList;
        }
        catch (Exception e){
            return null;
        }
    }
    public boolean remove(String id){
        int rowDeleted = session.createQuery("delete from Manufacture where id = :id")
                .setParameter("id", id)
                .executeUpdate();

        if (rowDeleted == 1) return true;
        else return false;
    }
    public boolean remove(Manufacture p){
        if(get(p.getId()) == null) return false;

        Manufacture deleted = session.get(Manufacture.class, p.getId());
        session.remove(deleted);
        return true;
    }
    public boolean update(Manufacture p){
        if(get(p.getId()) == null) return false;

        Manufacture updated = session.get(Manufacture.class, p.getId());

        updated.setName(p.getName());
        updated.setLocation(p.getLocation());
        updated.setEmployee(p.getEmployee());

        session.persist(updated);

        return true;
    }

    public List<Manufacture> getManufactureHasLessThan100Employees(){
        try{
            Query qr = session.createQuery("from Manufacture m where m.employee <= 100", Manufacture.class);
            List<Manufacture> manufactureList = qr.list();

            return manufactureList;
        }
        catch (Exception e){
            return null;
        }
    }

    public int totalEmployees(){
        try{
            Query qr = session.createQuery("select sum(employee) from Manufacture", Manufacture.class);
            int total = Integer.parseInt(qr.list().get(0).toString());

            return total;
        }
        catch (Exception e){
            return 0;
        }
    }

    public Manufacture finalManufacturefromUSA(){
        try{
            Query qr = session.createQuery("from Manufacture where location = 'USA'", Manufacture.class);
            List<Manufacture> manufactures = qr.list();
            if(manufactures.isEmpty()){
                return null;
            }
            else return (Manufacture) qr.list().get(manufactures.size() - 1);
        }
        catch (Exception e){
            return null;
        }
    }
    public void close() throws HibernateException {
        transaction.commit();
        session.close();
        SessionFactory.close();
    }
}
