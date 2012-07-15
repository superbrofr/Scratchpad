import java.util.ArrayList;
import java.util.Iterator;

/**
 * Manage the stock in a business.
 * The stock is described by zero or more Products.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class StockManager
{
    // A list of the products.
    private ArrayList<Product> stock;

    /**
     * Initialise the stock manager.
     */
    public StockManager()
    {
        stock = new ArrayList<Product>();
    }

    /**
     * Add a product to the list. Does not add if ID is already
     * in use.
     * @param item The item to be added.
     */
    public void addProduct(Product item)
    {
        if(findProduct(item.getID()) == null){
            stock.add(item);
        }
        else{
            System.out.println("Product ID already exists!");
        }
    }
    
    /**
     * Receive a delivery of a particular product.
     * Increase the quantity of the product by the given amount.
     * @param id The ID of the product.
     * @param amount The amount to increase the quantity by.
     */
    public void delivery(int id, int amount)
    {
        Product item = findProduct(id);
        if(item == null){
            System.out.println("No such product!");
        }
        else{
            item.increaseQuantity(amount);
        }
    }
    
    /**
     * Try to find a product in the stock with the given id.
     * @return The identified product, or null if there is none
     *         with a matching ID.
     */
    public Product findProduct(int id)
    {
        Iterator<Product> it = stock.iterator();
        while(it.hasNext()){
            Product p = it.next();
            if(p.getID() == id){
                return p;
            }
        }
        return null;
    }
    
    /**
     * Locate a product with the given ID, and return how
     * many of this item are in stock. If the ID does not
     * match any product, return zero.
     * @param id The ID of the product.
     * @return The quantity of the given product in stock.
     */
    public int numberInStock(int id)
    {
        Product item = findProduct(id);
        if(item == null){
            System.out.println("No such product!");
            return 0;
        }
        else{
            return item.getQuantity();
        }
    }

    /**
     * Print details of all the products.
     */
    public void printProductDetails()
    {
        Iterator<Product> it = stock.iterator();
        while(it.hasNext()){
            Product p = it.next();
            System.out.println(p.toString());
        }
    }
    
    public Product findProduct(String name)
    {
        Iterator<Product> it = stock.iterator();
        while(it.hasNext()){
            Product p = it.next();
            if(p.getName().equals(name)){
                return p;
            }
        }
        return null;
    }
}
