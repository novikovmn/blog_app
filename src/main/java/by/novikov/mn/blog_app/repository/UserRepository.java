package by.novikov.mn.blog_app.repository;

import by.novikov.mn.blog_app.entity.Post;
import by.novikov.mn.blog_app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    // search users by firstname or lastname or username fragment
    @Query("select u from User u where " +
            "u.firstname like concat('%', :query, '%')" +
            "or u.lastname like concat('%', :query, '%')" +
            "or u.username like concat('%', :query, '%')")
    List<User> searchUsers(String query);
}
