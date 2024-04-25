package com.example.forum_backendapi.controller;
import com.example.forum_backendapi.entity.User;
import com.example.forum_backendapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginRequest) {
        String firstName = loginRequest.get("firstName");
        String lastName = loginRequest.get("lastName");

        User user = userRepository.findByFirstNameAndLastName(firstName, lastName);
        if (user != null) {
            // Return JSON object of the user details if found
            return ResponseEntity.ok(true);
        } else {
            // Return false if user not found
            return ResponseEntity.ok(false);
        }
    }


//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestParam String firstName, @RequestParam String lastName) {
//        User user = userRepository.findByFirstNameAndLastName(firstName, lastName);
//        if (user != null) {
//            // Return JSON object of the user details if found
//            return ResponseEntity.ok(user);
//        } else {
//            // Return false if user not found
//            return ResponseEntity.ok(false);
//        }
//    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User newUser = userRepository.save(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        if (!userRepository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        user.setId(id);
        User updatedUser = userRepository.save(user);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (!userRepository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        userRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
