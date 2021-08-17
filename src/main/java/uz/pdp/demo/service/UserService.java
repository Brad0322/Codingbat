package uz.pdp.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.demo.entity.User;
import uz.pdp.demo.payload.ApiResponse;
import uz.pdp.demo.payload.UserDto;
import uz.pdp.demo.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUser(Integer id) {
        Optional<User> optional = userRepository.findById(id);
        if (optional.isEmpty()) {
            return null;
        }
        return optional.get();
    }

    public ApiResponse addUsers(UserDto userDto) {
        boolean exists = userRepository.existsByEmail(userDto.getEmail());
        if (exists) {
            return new ApiResponse("Bunday email mavjud!", false);
        }
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        userRepository.save(user);
        return new ApiResponse("Foydalanuvchi muvaqqaiyatli ruhatdan o'tdi", true);
    }

    public ApiResponse forgotPassword(UserDto userDto) {
        Optional<User> optional = userRepository.findByEmail(userDto.getEmail());
        User user = optional.get();
        user.setPassword(userDto.getPassword());
        userRepository.save(user);
        return new ApiResponse("Parol tiklandi!", true);
    }

    public ApiResponse deleteUser(Integer id) {
        Optional<User> optional = userRepository.findById(id);
        if (optional.isEmpty()) {
            return new ApiResponse("Bunday ID lik foydalanuvhci mavjud emas", false);
        }
        userRepository.deleteById(id);
        return new ApiResponse("Foydalanuvchi uchirildi", false);
    }
}
