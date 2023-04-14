package controller;

import model.Product;
import model.User;
import model.request.AddProductRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import repository.ProductRepository;
import repository.UserRepository;

@RestController
@RequestMapping("/catalog")
public class CatalogController {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final long USDRate = 82;
    private final long EURRate = 90;

    public CatalogController(ProductRepository productRepository, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @PostMapping
    public Mono<Product> addProduct(@RequestBody AddProductRequest product) {
        Product newProduct = new Product().withName(product.getName()).withPrice(product.getPrice());
        return productRepository.save(newProduct);
    }

    @GetMapping(value = "/{user_id}")
    public Flux<Product> getProducts(@PathVariable(value = "user_id") String userId) {
        Mono<User> user = userRepository.findById(userId);
        Flux<Product> products = productRepository.findAll();

        return products.flatMap(product ->
                user.map(u -> switch (u.getCurrency()) {
                    case RUB -> product;
                    case USD -> product.withPrice(product.getPrice() * USDRate);
                    case EUR -> product.withPrice(product.getPrice() * EURRate);
                }));
    }
}
