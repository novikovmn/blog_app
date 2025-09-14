package by.novikov.mn.blog_app.repository;

import by.novikov.mn.blog_app.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    Optional<Post> findById(Long id);


    // search posts by title or description fragment
    @Query("select p from Post p where " +
            "p.title like concat('%', :query, '%')" +
            "or p.description like concat('%', :query, '%')")
    List<Post> searchPosts(String query);

}