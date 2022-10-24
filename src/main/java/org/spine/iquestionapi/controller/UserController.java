package org.spine.iquestionapi.controller;

import org.spine.iquestionapi.model.User;
import org.spine.iquestionapi.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

// TODO: There are some changes we need to make to this controller
// 1. The method to register a user is now in the AuthController.
//      We might need to move that method here. But I'm not sure.
// 2. Updating the user's password is not implemented yet.
//      Because it needs to be hashed and all that.
// - Jesse

@RestController
@RequestMapping("/user")
@ResponseStatus(HttpStatus.OK)
public class UserController {

    @Autowired private UserRepo userRepo;

    // Get all users
    @GetMapping("/all")
    @ResponseBody
    public User[] getAllUsers(){
        return userRepo.findAll().toArray(new User[0]);
    }

    // Get a user by id
    @GetMapping("/{id}")
    @ResponseBody
    public User getUserById(@PathVariable(value="id") long id){
        return userRepo.findById(id).get();
    }

    // Update a user
    @PostMapping("/{id}")
    @ResponseBody
    public User updateUser(@PathVariable(value="id") long id, @RequestBody User user){
        User userToUpdate = userRepo.findById(id).get();
        // Update fields that are given
        if (user.getName() != null) userToUpdate.setName(user.getName());
        if (user.getRole() != null) userToUpdate.setRole(user.getRole());
        if (user.getOrganization() != null) userToUpdate.setOrganization(user.getOrganization());

        return userRepo.save(userToUpdate);
    }

    // Delete a user
    @DeleteMapping("/{id}")
    @ResponseBody
    public void deleteUser(@PathVariable(value="id") long id){
        userRepo.deleteById(id);
    }
}
