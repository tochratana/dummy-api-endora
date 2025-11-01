package space.endora.dummy.features.user.service;



import space.endora.dummy.features.user.dto.UserCreateRequest;
import space.endora.dummy.features.user.dto.UserResponse;
import space.endora.dummy.features.user.dto.UserUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    UserResponse createUser(UserCreateRequest request);
    Page<UserResponse> getAllUsers(Pageable pageable);
    UserResponse getUserById(Long id);
    UserResponse updateUser(Long id, UserUpdateRequest request);
    void deleteUser(Long id);
    void initializeDefaultUsers();
    void resetToDefaultUsers();
}