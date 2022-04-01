package com.shopme.admin.users;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTest {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TestEntityManager entityManger;

	@Test
	void userCreationTest() {
		Role roleAdmin = new Role(1);
		User jai = new User("AAA@email.com", "Akon", "kon", "kon123");
		jai.addRole(roleAdmin);
		User savedUser = userRepository.save(jai);

		assertThat(savedUser.getUserId()).isGreaterThan(0);

	}

	@Test
	void userCreationWithTwoRolesTest() {
		Role roleAdmin = new Role(1);
		Role roleSalesPerson = new Role(2);

		User jai = new User("jai@email.com", "jai", "thir", "jai123");
		jai.addRole(roleAdmin);
		jai.addRole(roleSalesPerson);
		User savedUser = userRepository.save(jai);

		assertThat(savedUser.getUserId()).isGreaterThan(0);

	}

	@Test
	public void testFetchAll() {
		Iterable<User> users = userRepository.findAll();
		users.forEach(user -> System.out.println(user));
	}

	@Test
	public void getByUserid() {
		User user = userRepository.findById(4).get();
		System.out.println(user);
		assertThat(user.getUserId()).isGreaterThan(0);
	}

	@Test
	public void userUpdateTest() {
		User user = userRepository.findById(4).get();
		System.out.println(user);
		user.setEnabled(true);
		user.setEmail("newEmail@email.com");

		User updatedUser = userRepository.save(user);
		assertEquals("newEmail@email.com", updatedUser.getEmail());
	}

	@Test
	public void updateUserRoleTest() {
		User user = userRepository.findById(1).get();
		Role role = new Role(1);
		user.getRole().remove(role);
		Role roleSalesPerson = new Role(2);

		user.addRole(roleSalesPerson);
		userRepository.save(user);

	}

	@Test
	public void deleteUserById() {
		Integer userId = 10;
		userRepository.deleteById(userId);
	}

	@Test
	public void getUserByEmail() {
		String testEmailid = "jstin@Email";
		User user = userRepository.getUserByEmailId(testEmailid);
		System.out.println(user);
		assertThat(user).isNotNull();

	}

}
