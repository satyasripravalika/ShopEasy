
package com.shop.shopeasy;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductRepository productRepository;

    // Constructor injection
    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // Get all products from database
    @GetMapping
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Add a new product to database
    @PostMapping
    public Product addProduct(@RequestBody Product product) {
        return productRepository.save(product);
    }

// Update an existing product
    @PutMapping("/{id}")
    public Product updateProduct(
            @PathVariable Long id,
            @RequestBody Product updatedProduct) {

        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                    new RuntimeException("Product not found"));

        product.setName(updatedProduct.getName());
        product.setPrice(updatedProduct.getPrice());

        return productRepository.save(product);
    }
    
    // Delete a product by ID
    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable Long id) {

        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Product not found");
        }

        productRepository.deleteById(id);

        return "Product deleted successfully";
    }
@GetMapping("/{id}")
public Product getProductById(@PathVariable Long id) {
    return productRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Product not found"));
}
@GetMapping("/search")
public List<Product> searchProducts(@RequestParam("name") String name) {
    return productRepository.findByNameContainingIgnoreCase(name);
}
// Get products with price less than the given amount
@GetMapping("/price")
public List<Product> getProductsByPrice(@RequestParam double maxPrice) {
    return productRepository.findByPriceLessThan(maxPrice);
}
}