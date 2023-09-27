package com.hltt;

import java.sql.*;
import java.util.*;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws SQLException
    {
        if(args.length > 1){
            System.out.println("Please enter an URL");
            return;
        }

        String url = args[0];
        ProductDAO dao = new ProductDAO(url);

        dao.createDatabase();

        if(dao.tableExists() == false) dao.createTable();
        else{
            Scanner sc = new Scanner(System.in);
            System.out.println("Do you want to keep this table? Y/N");
            String answer = sc.next();
            if(answer.equals("N")){
                dao.deleteTable();
                return;
            }
        }
        int option = 0;
        do {
            Scanner sc = new Scanner(System.in);
            
            System.out.println("1. Read all products\r\n" + //
                    "2. Read a product by input id\r\n" + //
                    "3. Add a new product\r\n" + //
                    "4. Update a product\r\n" + //
                    "5. Delete a product by id\r\n" + //
                    "6. Exit");
            System.out.println();
            System.out.println("Your choice: ");
            option = sc.nextInt();
            System.out.print("\033[H\033[2J"); 
            System.out.flush(); 

            if(option == 1) {
                for(Product p : dao.readAll()) System.out.println(p);

            }
            if(option == 2){
                System.out.println("Enter product id: ");
                int id = sc.nextInt();

                System.out.println(dao.read(id));
            }
            if(option == 3){
                System.out.println("Enter product name: ");
                String name = sc.next();
                System.out.println("Enter product price: ");
                double price = sc.nextDouble();
                System.out.println("Enter product color: ");
                String color = sc.next();
                System.out.println("Enter the amount of remaining products: ");
                int remaining = sc.nextInt();

                if(dao.add(new Product(name, price, color, remaining))!=-1) System.out.println("Add successfully");
                else System.out.println("Add product failed!");
            }
            if(option == 4){
                System.out.println("Enter product id you want to update: ");
                int id = sc.nextInt();

                System.out.println("Enter product name: ");
                String name = sc.next();
                System.out.println("Enter product price: ");
                double price = sc.nextDouble();
                System.out.println("Enter product color: ");
                String color = sc.next();
                System.out.println("Enter the amount of remaining products: ");
                int remaining = sc.nextInt();

                if(dao.update(new Product(id, name, price, color, remaining))) System.out.println("Update successful");
                else System.out.println("Update failed");
            }
            if(option == 5){
                System.out.println("Enter product id to delete: ");
                int delete_id = sc.nextInt();
                if(dao.delete(delete_id)) System.out.println("Delete successfully");
                else System.out.println("Delete failed, id cannot be found");
            }
            if(option == 6){
                break;
            }
            if(option < 1 && option > 6) System.out.println("Please enter the number appear in menu");
        } while (option != 6);

        dao.close();
    }
}
