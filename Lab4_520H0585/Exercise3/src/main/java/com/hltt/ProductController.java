package com.hltt;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

@WebServlet(name = "ProductController", value = "/products")
public class ProductController extends HttpServlet {
    private List<Product> productList;
    private Gson gson;

    @Override
    public void init() throws ServletException {
        super.init();
        gson = new Gson();
        productList = new ArrayList<>();
        productList.add(new Product(1, "Samsung Galaxy Note 8", 499));
        productList.add(new Product(2, "Laptop ASUS RTX 3080", 2000));
        productList.add(new Product(3, "iPhone 15", 750));
        productList.add(new Product(4, "Realme C35", 500));
        productList.add(new Product(5, "Samsung Smart TV 45Inch", 1000));
        productList.add(new Product(6, "Bluetooth Speaker", 300));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idValue = req.getParameter("id");

        if(idValue == null || idValue.equals("")){
            sendResponse(resp, Package.success("Read products successfully", productList));
        }
        else{
            try{
                int id = Integer.parseInt(idValue);
                if (findProduct(id) == null){
                    sendResponse(resp, Package.error(2, "Cannot find any product with id = " + id));
                }
                else sendResponse(resp, Package.success("Find product with id = " + id + " successfully", findProduct(id)));
            }catch (Exception e){
                sendResponse(resp, Package.error(1, "ID is invalid"));
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idValue = req.getParameter("id");
        String name = req.getParameter("name");
        String priceValue = req.getParameter("price");

        if(idValue == null || name == null || priceValue == null){
            sendResponse(resp, Package.error(3, "Please provide information about the products"));
        }
        else if(idValue.equals("")){
            sendResponse(resp, Package.error(4, "Please provide the ID of the product"));
        }
        else if(name.equals("")){
            sendResponse(resp, Package.error(4, "Please provide the name of the product"));
        }
        else if(priceValue.equals("")){
            sendResponse(resp, Package.error(4, "Please provide the price of the product"));
        }
        else{
            try{
                int id = Integer.parseInt(idValue);
                int price = Integer.parseInt(priceValue);

                if(findProduct(id) == null){
                    productList.add(new Product(id, name, price));
                    sendResponse(resp, Package.success("Add products successfully"));
                }
                else sendResponse(resp, Package.error(6, "Already have a product with id = " + id));
            }
            catch (Exception e){
                sendResponse(resp, Package.error(5, "ID or price is invalid"));
            }
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idValue = req.getParameter("id");
        String name = req.getParameter("name");
        String priceValue = req.getParameter("price");

        if(idValue == null || name == null || priceValue == null){
            sendResponse(resp, Package.error(3, "Please provide information about the products"));
        }
        else if(idValue.equals("")){
            sendResponse(resp, Package.error(4, "Please provide the ID of the product"));
        }
        else if(name.equals("")){
            sendResponse(resp, Package.error(4, "Please provide the name of the product"));
        }
        else if(priceValue.equals("")){
            sendResponse(resp, Package.error(4, "Please provide the price of the product"));
        }
        else{
            try{
                int id = Integer.parseInt(idValue);
                int price = Integer.parseInt(priceValue);

                if(findProduct(id) == null){
                    sendResponse(resp, Package.error(2, "Cannot find any product with id = " + id));
                }
                else{
                    if(findProduct(id).getName().equals(name) && findProduct(id).getPrice() == price){
                        sendResponse(resp, Package.error(7, "Please provide different information to update this product"));
                    }
                    else{
                        productList.get(productList.indexOf(findProduct(id))).setName(name);
                        productList.get(productList.indexOf(findProduct(id))).setPrice(price);

                        sendResponse(resp, Package.success("Update successfully"));
                    }
                }
            }
            catch (Exception e){
                sendResponse(resp, Package.error(5, "ID or price is invalid"));
            }
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idValue = req.getParameter("id");

        if(idValue == null || idValue.equals("")){
            sendResponse(resp, Package.error(8, "Please input the product you want to delete"));
        }
        else{
            try{
                int id = Integer.parseInt(idValue);
                if (findProduct(id) == null){
                    sendResponse(resp, Package.error(2, "Cannot find any product with id = " + id));
                }
                else{
                    productList.remove(findProduct(id));
                    sendResponse(resp, Package.success("Delete product with id = " + id + " successfully"));
                } 
            }catch (Exception e){
                sendResponse(resp, Package.error(1, "ID is invalid"));
            }
        }
    }
    
    private void sendResponse(HttpServletResponse resp, Package data) throws ServletException, IOException{
        resp.setHeader("Content-Type", "application/json");
        resp.getWriter().println(gson.toJson(data));
    }

    private Product findProduct(int id){
        return productList.stream().filter(product -> product.getId() == id).findAny().orElse(null);
    }
}
