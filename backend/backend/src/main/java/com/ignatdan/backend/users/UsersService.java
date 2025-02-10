package com.ignatdan.backend.users;

import com.ignatdan.backend.projects.Projects;
import com.ignatdan.backend.projects.ProjectsRepository;
import com.ignatdan.backend.tasks.Tasks;
import com.ignatdan.backend.tasks.TasksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsersService {
    private final UsersRepository usersRepository;
    private final ProjectsRepository projectsRepository;

    @Autowired
    public UsersService(UsersRepository usersRepository, ProjectsRepository projectsRepository) {
        this.usersRepository = usersRepository;
        this.projectsRepository = projectsRepository;
    }

    public List<Projects> getUserProjects(Integer id) {
        Optional<Users> userOptional = usersRepository.findById(id);

        Users user = userOptional.get();

        List<Projects> projects = projectsRepository.findByUser_UserId(user.getUserId());

        return projects;
    }

    public ResponseEntity<Object> getUserByAuth(UserAuthRequest authRequest) {
        Optional<Users> userOptional = usersRepository.findByUsername(authRequest.getUsername());

        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        Users user = userOptional.get();
        if (!user.getHashPassword().equals(authRequest.getHashPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }

        return ResponseEntity.ok(user);
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
