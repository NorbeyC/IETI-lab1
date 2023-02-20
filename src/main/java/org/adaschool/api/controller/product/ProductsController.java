package org.adaschool.api.controller.product;

import org.adaschool.api.exception.ProductNotFoundException;
import org.adaschool.api.repository.product.Product;
import org.adaschool.api.service.product.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/products/")
public class ProductsController {

    private final ProductsService productsService;

    public ProductsController(@Autowired ProductsService productsService) {
        this.productsService = productsService;
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product producto) {
        //Method already implemented
        try {
            productsService.save(producto);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        //Method already implemented
        return ResponseEntity.ok(productsService.all());
    }

    @GetMapping("{id}")
    public ResponseEntity<?> findById(@PathVariable("id") String id) {
        //TODO implement this method
        try {
            return new ResponseEntity<Product>(productsService.findById(id).get(), HttpStatus.OK);
        } catch (Exception e) {
            throw new ProductNotFoundException(id);
        }
    }
    @PutMapping("{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable String id, @RequestBody Product producto) {
        //Method already implemented
        Optional<Product> products = productsService.findById(id);
        if(products.isPresent()){
            productsService.update(producto,id);
            productsService.save(products.get());
            return new ResponseEntity<>(products.get(), HttpStatus.OK);
        }else{
            throw new ProductNotFoundException(id);

        }
    }


    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String id) {
        //Method already implemented
        Optional<Product> products = productsService.findById(id);
        if(products.isPresent()){
            productsService.deleteById(id);
            productsService.save(products.get());
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            throw new ProductNotFoundException(id);

        }
    }
}
