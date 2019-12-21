package model;

public class Product {
	private String id;
	private String name;
	private int amount;
	private int price;
	private Category category;
		
	public Product() {
		super();
	}
	public Product(String id, String name, int amount, int price, Category category) {
		super();
		this.id = id;
		this.name = name;
		this.amount = amount;
		this.price = price;
		this.category = category;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}	
			
}
