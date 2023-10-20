package com.hltt;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.sql.*;
import java.util.*;

public class PhoneDAO {
    private SessionFactory SessionFactory;
    private Session session;
    private Transaction transaction;

    public PhoneDAO() throws HibernateException {
        SessionFactory = new Configuration().configure().
                addAnnotatedClass(Phone.class).
                addAnnotatedClass(Manufacture.class).
                buildSessionFactory();

        session = SessionFactory.getCurrentSession();

        transaction = session.beginTransaction();
    }

    public boolean add(Phone p){
        try{
            session.persist(p);
            return true;
        }
        catch(Exception e){
            return false;
        }
    }

    public Phone get(String id){
        try{
            Query qr = session.createQuery("from Phone p where p.id = :id", Phone.class);
            qr.setParameter("id", id);
            Phone phone = (Phone) qr.list().get(0);
            return phone;
        }
        catch (Exception e){
            return null;
        }
    }
    public List<Phone> getAll(){
        try{
            Query qr = session.createQuery("from Phone", Phone.class);
            List<Phone> phoneList = qr.list();
            return phoneList;
        }
        catch (Exception e){
            return null;
        }
    }
    public boolean remove(String id){
        int rowDeleted = session.createQuery("delete from Phone where id = :id")
                .setParameter("id", id)
                .executeUpdate();

        if (rowDeleted == 1) return true;
        else return false;
    }
    public boolean remove(Phone p){
        if(get(p.getId()) == null) return false;

        Phone deleted = session.get(Phone.class, p.getId());
        session.remove(deleted);
        return true;
    }
    public boolean update(Phone p){
        if(get(p.getId()) == null) return false;

        Phone updated = session.get(Phone.class, p.getId());

        updated.setColor(p.getColor());
        updated.setCountry(p.getCountry());
        updated.setName(p.getName());
        updated.setColor(p.getColor());
        updated.setPrice(p.getPrice());
        updated.setQuantity(p.getQuantity());
        updated.setManufacture(p.getManufacture());

        session.persist(updated);
        return true;
    }

    public List<Phone> highestSellingPrice(){
        try{
            Query qr = session.createQuery("from Phone where price = (select max(price) from Phone)", Phone.class);
            List<Phone> phoneList = qr.list();
            return phoneList;
        }
        catch (Exception e){
            return null;
        }
    }

    public List<Phone> getAllSortedByCountryandPrice(){
        try{
            Query qr = session.createQuery("from Phone p order by country, price desc", Phone.class);
            List<Phone> phoneList = qr.list();
            return phoneList;
        }
        catch (Exception e){
            return null;
        }
    }

    public List<Phone> getAbove50Million(){
        try{
            Query qr = session.createQuery("from Phone p where price > 50000000", Phone.class);
            List<Phone> phoneList = qr.list();
            return phoneList;
        }
        catch (Exception e){
            return null;
        }
    }

    public List<Phone> getPinkandAbove15Million(){
        try{
            Query qr = session.createQuery("from Phone p where color = 'Pink' and price > 15000000", Phone.class);
            List<Phone> phoneList = qr.list();
            return phoneList;
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
