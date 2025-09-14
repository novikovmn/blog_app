package by.novikov.mn.blog_app.dto;

import by.novikov.mn.blog_app.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Builder
public class UserDto {

    private Long id;
    // email
    @Email
    @NotBlank (message = "Username must not be empty")
    private String username;
    @NotBlank(message = "Firstname must not be empty")
    private String firstname;
    @NotBlank(message = "Lastname must not be empty")
    private String lastname;
    @Pattern(regexp = "(?i)^[a-z0-9]{3,6}$", message = "Password must contain only latin letters and digits from 3 to 6 inclusively")
    private String password;
    private String imagePath;
    private MultipartFile imageFile;
    private Role role;
    private List<PostDto> postDtos;
    private List<CommentDto> commentDtos;

}
