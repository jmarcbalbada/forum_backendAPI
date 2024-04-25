package com.example.forum_backendapi.controller;
import com.example.forum_backendapi.dto.ReplyRequestDTO;
import com.example.forum_backendapi.entity.Post;
import com.example.forum_backendapi.entity.Reply;
import com.example.forum_backendapi.entity.User;
import com.example.forum_backendapi.repository.PostRepository;
import com.example.forum_backendapi.repository.ReplyRepository;
import com.example.forum_backendapi.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/posts/{postId}/replies")
public class ReplyController {

    @Autowired
    private ReplyRepository replyRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @GetMapping
    public ResponseEntity<List<Reply>> getAllRepliesForPost(@PathVariable Long postId) {
        Optional<Post> postOptional = postRepository.findById(postId);
        if (!postOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        List<Reply> replies = replyRepository.findByPostId(postId);
        return ResponseEntity.ok(replies);
    }

    @GetMapping("/{replyId}")
    public ResponseEntity<Reply> getReplyById(@PathVariable Long postId, @PathVariable Long replyId) {
        Optional<Post> postOptional = postRepository.findById(postId);
        if (!postOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Optional<Reply> replyOptional = replyRepository.findById(replyId);
        return replyOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public boolean createReply(@PathVariable Long postId, @RequestBody ReplyRequestDTO replyRequest) {
        Optional<Post> postOptional = postRepository.findById(postId);
        if (!postOptional.isPresent()) {
            return false;
        }

        Post post = postOptional.get();
        User user = userRepository.findById(replyRequest.getUserId())
                .orElse(null);

        if (user == null) {
            return false;
        }

        Reply reply = new Reply();
        reply.setPost(post);
        reply.setUser(user);
        reply.setReplyText(replyRequest.getReplyText());

        try {
            replyRepository.save(reply);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @PutMapping("/{replyId}")
    public ResponseEntity<Reply> updateReply(@PathVariable Long postId, @PathVariable Long replyId, @RequestBody ReplyRequestDTO replyRequest) {
        Optional<Post> postOptional = postRepository.findById(postId);
        if (!postOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Optional<Reply> existingReplyOptional = replyRepository.findById(replyId);
        if (!existingReplyOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Reply existingReply = existingReplyOptional.get();
        existingReply.setReplyText(replyRequest.getReplyText());

        Reply updatedReply = replyRepository.save(existingReply);

        return ResponseEntity.ok(updatedReply);
    }

    @DeleteMapping("/{replyId}")
    public boolean deleteReply(@PathVariable Long postId, @PathVariable Long replyId) {
        Optional<Post> postOptional = postRepository.findById(postId);
        if (!postOptional.isPresent()) {
            return false;
        }

        Optional<Reply> replyOptional = replyRepository.findById(replyId);
        if (!replyOptional.isPresent()) {
            return false;
        }

        try {
            replyRepository.deleteById(replyId);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


//    @DeleteMapping("/{replyId}")
//    public ResponseEntity<Void> deleteReply(@PathVariable Long postId, @PathVariable Long replyId) {
//        Optional<Post> postOptional = postRepository.findById(postId);
//        if (!postOptional.isPresent()) {
//            return ResponseEntity.notFound().build();
//        }
//
//        Optional<Reply> replyOptional = replyRepository.findById(replyId);
//        if (!replyOptional.isPresent()) {
//            return ResponseEntity.notFound().build();
//        }
//
//        replyRepository.deleteById(replyId);
//        return ResponseEntity.noContent().build();
//    }
}
