package com.example.forum_backendapi.controller;

import com.example.forum_backendapi.dto.PostRequestDTO;
import com.example.forum_backendapi.entity.Post;
import com.example.forum_backendapi.entity.User;
import com.example.forum_backendapi.repository.PostRepository;
import com.example.forum_backendapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Long id) {
        Optional<Post> post = postRepository.findById(id);
        return post.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    // return boolean success
    @PostMapping
    public ResponseEntity<Map<String, Boolean>> createPost(@RequestBody PostRequestDTO postRequest) {
        // Retrieve the user from the database based on the userId
        User user = userRepository.findById(postRequest.getUserId()).orElse(null);

        if (user == null) {
            // Return failure if user not found
            Map<String, Boolean> response = new HashMap<>();
            response.put("success", false);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        // Create and save the new post
        Post post = new Post();
        post.setUser(user);
        post.setPostText(postRequest.getPostText());
        postRepository.save(post);

        // Return success
        Map<String, Boolean> response = new HashMap<>();
        response.put("success", true);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable Long id, @RequestBody Post post) {
        if (!postRepository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        post.setId(id);
        Post updatedPost = postRepository.save(post);
        return new ResponseEntity<>(updatedPost, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public boolean deletePost(@PathVariable Long id) {
        if (!postRepository.existsById(id)) {
            return false;
        }
        postRepository.deleteById(id);
        return true;
    }

}
