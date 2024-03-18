package com.tcs.bookstore.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tcs.bookstore.exception.NoProductFound;
import com.tcs.bookstore.exception.NoProductFoundException;
import com.tcs.bookstore.model.Product;
import com.tcs.bookstore.repository.ProductRepo;

@Service
public class ProductServiceImpl implements ProductService{
	
	@Autowired
	private ProductRepo prodDao;
	
	@Override
	public Product addProduct(Product product) {
		Product prod = null;
		try {
			prod = prodDao.save(product);
		}catch(Exception e) {
			throw new NoProductFoundException("Unable to add Products");
		}
		return prod;
	}
	
	@Override
	public List<Product> getAllProducts() throws NoProductFound {
		List<Product> list = prodDao.findAll();
		
		if (list.size() > 0) {
			return list;
		} 
		throw new NoProductFound("No Products Found");
	}

	@Override
	public List<Product> getProductsByCategory(String cate) throws NoProductFound{
		List<Product> list = prodDao.getAllProductsInACategory(cate);
		if (list.size() > 0) {
			return list;
		}
		else 
		{
			throw new NoProductFound("No Products Found in category "+cate);
		}
	
	}
	
	@Override
	public List<Product> getAllProductsByName(String name) throws NoProductFound{
		List<Product> list = prodDao.getAllProductsByName(name);
		if (list.size() > 0) {
			return list;
		} else {
			throw new NoProductFound("Product with name : "+ name +" Not Found in catalog");
		}
	}

	@Override
	public String deleteProduct(int id) throws NoProductFound{
		Optional<Product> opt = prodDao.findById(id);
		
		if (opt.isPresent()) {
			Product prod = opt.get();
			prodDao.delete(prod);
			return "Product with id : "+ id+" deleted from catalog";
		}
		else {
			throw new NoProductFound("Product with id : "+ id+" Not Found in catalog");
		}
	}

	@Override
	public String updateProductQuantity(int id, int quantity) {
		Optional<Product> opt = prodDao.findById(id);
		if (opt.isPresent()) {
			prodDao.updateProductQuantity(id, quantity);
			return "Quantity Updated Successfully";
		}
		else {
			throw new NoProductFound("Product with id : "+ id+" Not Found in catalog");
		}
	}

}
