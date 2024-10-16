package com.saurabh.ecommerce.product_service.service;

import com.saurabh.ecommerce.product_service.dto.ProductRequest;
import com.saurabh.ecommerce.product_service.exception.UserNotFoundException;
import com.saurabh.ecommerce.product_service.models.Product;
import com.saurabh.ecommerce.product_service.payload.DeleteResponse;
import com.saurabh.ecommerce.product_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TestService {

    private final WebClient webClient;
    private final ProductRepository productRepository;


    public ResponseEntity<List<Product>> fetchProducts() throws UserNotFoundException {

        List<Product> products;

        try {
            products = productRepository.findAll();
            log.info("Get All Product is SuccesFulluy");
        } catch (Exception ex) {
            throw new UserNotFoundException("No User Exist in Database " + ex.getMessage());
        }

        return ResponseEntity.ok().body(products);
    }

    public ResponseEntity<Product> getProductByID(long id) throws UserNotFoundException {
        System.out.println("ProductService.getProductById");
        System.out.println("id = " + id);
        Product product = this.productRepository.getProductById(id);

        if (product != null) {
            return ResponseEntity.ok().body(product);
        }

        Product productFromApi = webClient
                .get()
                .uri("/products/" + id)
                .retrieve()
                .bodyToMono(Product.class)
                .block(); // Blocks for a response. Remove for asynchronous handling.
        if (productFromApi == null) throw new UserNotFoundException("User Not found " + id);
        this.saveProduct(productFromApi);
        return ResponseEntity.ok().body(productFromApi);
    }


    public Mono<Product> createProduct(Product product) {
        return webClient.post()
                .uri("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(product))
                .retrieve()
                .bodyToMono(Product.class);
    }


    public ResponseEntity<Product> updateProduct(long id, Product product) throws UserNotFoundException {
        System.out.println("Update product " + id + " " + product.toString());
        Product tempProduct = productRepository.getProductById(id);
        if (tempProduct != null) {
            productRepository.save(tempProduct);
            return ResponseEntity.ok().body(product);
        } else {

            throw new UserNotFoundException("User Not Found with id " + id);
        }

    }
//        String apiUrl = "https://fakestoreapi.com/products/" + id;  // Update URL with product ID
//
//        return webClient
//                .put()
//                .uri(apiUrl)
//                .body(Mono.just(product), Product.class)
//                .retrieve()
//                .bodyToMono(String.class)
//                .map(response -> ResponseEntity.ok(response))
//                .onErrorResume(e -> Mono.just(ResponseEntity.status(500).body("Error occurred: " + e.getMessage())));


    public ResponseEntity<DeleteResponse> deleteProduct(long id) throws UserNotFoundException {

        if (checkProductIsExist(id)) {
            try {
                productRepository.delete(productRepository.getProductById(id));
                return ResponseEntity.ok().body(new DeleteResponse(Boolean.TRUE, "Succfully Delete ", HttpStatus.ACCEPTED));
            } catch (Exception ex) {
                throw new UserNotFoundException("Failed to Delete the product" + id);
            }
        } else {
            throw new UserNotFoundException("Not able to Delete User" + id);
        }
    }

    public void saveProduct(Product product) {
        productRepository.save(product);
    }
//    public ResponseEntity<Product> updateProductById(Long id,Product updateProduct){
//
//        Product tempProduct = productRepository.getProductById(id);
//        if(tempProduct!=null) {
//            tempProduct = updateProduct;
//            productRepository.save(tempProduct);
//            return ResponseEntity.ok()
//        }else {
//            System.out.println("Product does not exist");
//        }
//    }

    public void createProduct(ProductRequest productRequest) {
        System.out.println("Start saving project");
        Product product = Product.builder()
                .title(productRequest.getTitle())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .image(productRequest.getImage())
                .category(productRequest.getCategory())
                .build();

        saveProduct(product);
        System.out.println("sava project");

    }

    public boolean checkProductIsExist(long id) {
        Product tempProduct = productRepository.getProductById(id);
        if (tempProduct == null) {
            return false;
        }
        return true;
    }


}
