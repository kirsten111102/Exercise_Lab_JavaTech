package com.hltt;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.sql.SQLException;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class Program
{
    public static void main( String[] args ) throws HibernateException, SQLException
    {
        int optionmenu = 0;

        do{
            Scanner sc = new Scanner(System.in);

            System.out.println("1. Open Phone UI\r\n" + //
                    "2. Open Manufacture UI\n" + //
                    "3. Exit");
            System.out.println();
            System.out.println("Your choice: ");
            optionmenu = sc.nextInt();
            System.out.print("\033[H\033[2J");
            System.out.flush();

            if(optionmenu == 1){
                PhoneUI();
            }
            if(optionmenu == 2){
                ManufactureUI();
            }
            if(optionmenu == 3){
                break;
            }
            if(optionmenu < 1 && optionmenu > 3) System.out.println("Please enter the number appear in menu");
        }while (optionmenu != 3);
    }

    private static void PhoneUI() throws HibernateException, SQLException{
        PhoneDAO phoneDAO = new PhoneDAO();

        int option = 0;
        do {
            Scanner sc = new Scanner(System.in);

            System.out.println("1. Read all products\r\n" + //
                    "2. Read a product by input id\r\n" + //
                    "3. Add a new product\r\n" + //
                    "4. Update a product\r\n" + //
                    "5. Delete a product by id\r\n" + //
                    "6. Delete a product by product\r\n" + //
                    "7. Find product(s) with highest selling price\r\n" + //
                    "8. Get all products with country and price sorted\r\n" + //
                    "9. Check products cost over 50 million\r\n" + //
                    "10. Get all Pink phones cost over 15 million\r\n" + //
                    "11. Exit");
            System.out.println();
            System.out.println("Your choice: ");
            option = sc.nextInt();
            System.out.print("\033[H\033[2J");
            System.out.flush();

            if(option == 1) {
                if(phoneDAO.getAll() != null && !phoneDAO.getAll().isEmpty()){
                    for(Phone p : phoneDAO.getAll()) System.out.println(p);
                }
                else System.out.println("Can't load phone list");
            }
            if(option == 2){
                System.out.println("Enter product id: ");
                String id = sc.next();

                if(phoneDAO.get(id) == null){
                    System.out.println("Can't find product with id: " + id);
                }
                else System.out.println(phoneDAO.get(id));
            }
            if(option == 3){
                ManufactureDAO manufactureDAO = new ManufactureDAO();
                System.out.println("Enter product id you want to add: ");
                String id = sc.next();
                sc.nextLine();
                System.out.print("Enter product name: \n");
                String name = sc.nextLine();
                System.out.println("Enter product price: ");
                int price = sc.nextInt();
                System.out.println("Enter product color: ");
                String color = sc.next();
                System.out.println("Enter product origin: ");
                String country = sc.next();
                System.out.println("Enter the product's quantity: ");
                int quantity = sc.nextInt();
                System.out.println("Enter the manufacture id: ");
                String manufacture_id = sc.next();

                if(phoneDAO.add(new Phone(id, name, price, color, country, quantity, manufactureDAO.get(manufacture_id)))){
                    System.out.println("Add successfully");
                }
                else System.out.println("Failed to do this tasks.");
                manufactureDAO.close();
            }
            if(option == 4){
                ManufactureDAO manufactureDAO = new ManufactureDAO();
                System.out.println("Enter product id you want to update: ");
                String id = sc.next();
                sc.nextLine();
                System.out.print("Enter product name: \n");
                String name = sc.nextLine();
                System.out.println("Enter product price: ");
                int price = sc.nextInt();
                System.out.println("Enter product color: ");
                String color = sc.next();
                System.out.println("Enter product origin: ");
                String country = sc.next();
                System.out.println("Enter the product's quantity: ");
                int quantity = sc.nextInt();
                System.out.println("Enter the manufacture id: ");
                String manufacture_id = sc.next();

                if(phoneDAO.update(new Phone(id, name, price, color, country, quantity, manufactureDAO.get(manufacture_id)))) System.out.println("Update successful");
                else System.out.println("Update failed");

                manufactureDAO.close();
            }
            if(option == 5){
                System.out.println("Enter product id you want to delete: ");
                String delete_id = sc.next();
                if(phoneDAO.remove(delete_id)){
                    System.out.println("Deleted product with id: " + delete_id + " success");
                }
                else System.out.println("Can't find product with id: " + delete_id);
            }
            if(option == 6){
                System.out.println("Enter product id you want to delete: ");
                String delete_id = sc.next();
                Phone deleted_product = phoneDAO.get(delete_id);
                if(deleted_product != null){
                    System.out.println("Deleted product: " + deleted_product.getName() + " success");
                    phoneDAO.remove(deleted_product);
                }
                else System.out.println("Can't find product with id: " + delete_id);
            }
            if(option == 7){
                if(phoneDAO.highestSellingPrice() != null && !phoneDAO.highestSellingPrice().isEmpty()){
                    for(Phone p : phoneDAO.highestSellingPrice()) System.out.println(p);
                }
                else System.out.println("No phones available");
            }
            if(option == 8){
                if(phoneDAO.getAllSortedByCountryandPrice() != null && !phoneDAO.getAllSortedByCountryandPrice().isEmpty()){
                    for(Phone p : phoneDAO.getAllSortedByCountryandPrice()) System.out.println(p);
                }
                else System.out.println("Can't load Phone list");
            }
            if(option == 9){
                if(phoneDAO.getAbove50Million() != null && !phoneDAO.getAbove50Million().isEmpty()){
                    for(Phone p : phoneDAO.getAbove50Million()) System.out.println(p);
                }
                else System.out.println("There is no phone costs over 50 million");
            }
            if(option == 10){
                if(phoneDAO.getPinkandAbove15Million() != null && !phoneDAO.getPinkandAbove15Million().isEmpty()){
                    for(Phone p : phoneDAO.getPinkandAbove15Million()) System.out.println(p);
                }
                else System.out.println("Can't find any Pink phone costs over 15 million");
            }
            if(option == 11){
                break;
            }
            if(option < 1 && option > 11) System.out.println("Please enter the number appear in menu");
        } while (option != 11);

        phoneDAO.close();
    }

    private static void ManufactureUI() throws HibernateException, SQLException{
        ManufactureDAO manufactureDAO = new ManufactureDAO();

        int option = 0;
        do {
            Scanner sc = new Scanner(System.in);

            System.out.println("1. Read all manufactures\r\n" + //
                    "2. Read a manufacture by input id\r\n" + //
                    "3. Add a new manufacture\r\n" + //
                    "4. Update a manufacture\r\n" + //
                    "5. Delete a manufacture by id\r\n" + //
                    "6. Delete a manufacture by manufacture\r\n" + //
                    "7. Check if all the manufactures have more than 100 employees\r\n" + //
                    "8. Total employees of all manufactures\r\n" + //
                    "9. Get the last manufacture from USA\r\n" + //
                    "10. Exit");
            System.out.println();
            System.out.println("Your choice: ");
            option = sc.nextInt();
            System.out.print("\033[H\033[2J");
            System.out.flush();

            if(option == 1) {
                if(manufactureDAO.getAll() != null){
                    for(Manufacture p : manufactureDAO.getAll()) System.out.println(p);
                }
                else System.out.println("Can't load manufacture list");
            }
            if(option == 2){
                System.out.println("Enter manufacture id: ");
                String id = sc.next();

                if(manufactureDAO.get(id) == null){
                    System.out.println("Can't find manufacture with id: " + id);
                }
                else System.out.println(manufactureDAO.get(id));
            }
            if(option == 3){
                System.out.println("Enter manufacture id you want to add: ");
                String id = sc.next();

                System.out.println("Enter manufacture name: ");
                String name = sc.next();
                System.out.println("Enter manufacture numbers of employees: ");
                int employee = sc.nextInt();
                System.out.println("Enter manufacture origin: ");
                String origin = sc.next();

                if(manufactureDAO.add(new Manufacture(id, name, origin, employee))) System.out.println("Add successfully");
                else System.out.println("Failed to do this tasks.");
            }
            if(option == 4){
                System.out.println("Enter manufacture id you want to update: ");
                String id = sc.next();

                System.out.println("Enter manufacture name: ");
                String name = sc.next();
                System.out.println("Enter manufacture numbers of employees: ");
                int employee = sc.nextInt();
                System.out.println("Enter manufacture origin: ");
                String origin = sc.next();

                if(manufactureDAO.update(new Manufacture(id, name, origin, employee))) System.out.println("Update successful");
                else System.out.println("Update failed");
            }
            if(option == 5){
                System.out.println("Enter manufacture id you want to delete: ");
                String delete_id = sc.next();
                if(manufactureDAO.remove(delete_id)){
                    System.out.println("Deleted manufacture with id: " + delete_id + " success");
                }
                else System.out.println("Can't find manufacture with id: " + delete_id);
            }
            if(option == 6){
                System.out.println("Enter manufacture id you want to delete: ");
                String delete_id = sc.next();
                Manufacture deleted_manufacture = manufactureDAO.get(delete_id);
                if(manufactureDAO.remove(deleted_manufacture)){
                    System.out.println("Deleted manufacture: " + deleted_manufacture.getName() + " success");
                }
                else System.out.println("Can't find manufacture with id: " + delete_id);
            }
            if(option == 7){
                if(manufactureDAO.getManufactureHasLessThan100Employees() != null && !manufactureDAO.getManufactureHasLessThan100Employees().isEmpty()){
                    System.out.println("Not all the manufactures have more than 100 employees, these are manufactures have less then or exact 100 employees:");
                    for(Manufacture p : manufactureDAO.getManufactureHasLessThan100Employees()) System.out.println(p);
                }
                else System.out.println("All the manufactures have more than 100 employees");
            }
            if(option == 8){
                System.out.println("Total employees of all manufactures: " + manufactureDAO.totalEmployees());
            }
            if(option == 9){
                if(manufactureDAO.finalManufacturefromUSA() != null)
                    System.out.println(manufactureDAO.finalManufacturefromUSA());
                else System.out.println("Can't find any manufacture from USA");
            }
            if(option == 10){
                break;
            }
            if(option < 1 && option > 10) System.out.println("Please enter the number appear in menu");
        } while (option != 10);

        manufactureDAO.close();
    }
}