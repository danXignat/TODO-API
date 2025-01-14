package unitbv.crud_api.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsersService {
    private final UsersRepository usersRepository;

    @Autowired
    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public List<Users> getAllUsers() {
        return usersRepository.findAll();
    }

    public ResponseEntity<Object> getUserById(Integer userId) {
        Optional<Users> userOptional = usersRepository.findById(userId);

        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        return ResponseEntity.ok(userOptional.get());
    }

    public ResponseEntity<Object> createUser(Users user) {
        usersRepository.save(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    public ResponseEntity<Object> updateUser(Integer userId, Users updatedUser) {
        Optional<Users> userOptional = usersRepository.findById(userId);

        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        Users existingUser = userOptional.get();
        existingUser.setUsername(updatedUser.getUsername());
        existingUser.setHashPassword(updatedUser.getHashPassword());
        existingUser.setFirstName(updatedUser.getFirstName());
        existingUser.setLastName(updatedUser.getLastName());

        usersRepository.save(existingUser);
        return ResponseEntity.ok(existingUser);
    }

    public ResponseEntity<Object> deleteUser(Integer userId) {
        Optional<Users> userOptional = usersRepository.findById(userId);

        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        usersRepository.deleteById(userId);
        return ResponseEntity.ok().body("User deleted successfully");
    }
}
