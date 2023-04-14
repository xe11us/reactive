package controller;

import model.User;
import model.User.Currency;
import model.request.AddUserRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;
import repository.UserRepository;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping
    public Mono<User> register(@RequestBody AddUserRequest user) {
        try {
            Currency currency = Currency.valueOf(user.getCurrency());
            User newUser = new User().withName(user.getName()).withCurrency(currency);
            return userRepository.save(newUser);
        } catch (IllegalArgumentException e) {
            return Mono.error(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unknown currency"));
        }
    }
}
