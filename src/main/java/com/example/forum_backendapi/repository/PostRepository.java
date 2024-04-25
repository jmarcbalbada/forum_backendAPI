package com.example.forum_backendapi.repository;
import com.example.forum_backendapi.entity.Post;
import com.example.forum_backendapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    // Custom method to find all posts by a user
    List<Post> findByUser(User user);

    List<Post> findAllByParentPostId(Long parentId);
}
