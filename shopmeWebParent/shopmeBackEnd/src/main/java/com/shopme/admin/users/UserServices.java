package com.shopme.admin.users;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;

@Service
public class UserServices {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleRepository roleRepository;

	public List<User> listAllUsers() {
		return (List<User>) userRepository.findAll();
	}

	public List<Role> listAllRoles() {
		return (List<Role>) roleRepository.findAll();
	}

	public void saveUser(User user) {

		boolean isUpdatingUser = (user.getUserId() != null);

		if (isUpdatingUser) {
			User existingUser = userRepository.findById(user.getUserId()).get();
			if (user.getPassword().isEmpty()) { // updating user
				user.setPassword(existingUser.getPassword());
			} else {
				encodePassword(user); // updated pass will be encoded

			}
			encodePassword(user); // for only new us
		}

		userRepository.save(user);
	}

	public void encodePassword(User user) {
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
	}

	public Boolean isEmailUnique(Integer id, String email) {
		User user = userRepository.getUserByEmailId(email);

		if (user.getEmail() == null)
			return true; // email null means no user using that email

		boolean isCreateNewUser = (id == null); // id null means creating new user | no id generated

		if (isCreateNewUser) { // only in the case of new user creation
			if (user.getEmail() != null) {
				return false; // new user so we are expecting email not reused.
			} else if (user.getUserId() == id)
				return false; // as new user being created id cannot be having any user id that means
								// already id generated
		}

		return true; // applies to only edit user
	}

	public User get(Integer id) throws UserNotFoundException {
		try {
			return userRepository.findById(id).get();
		} catch (NoSuchElementException ex) {

			throw new UserNotFoundException("Could not find the user with id = " + id);
		}
	}

}
