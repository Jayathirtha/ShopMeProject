package com.shopme.admin.users;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.shopme.common.entity.Role;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE) // will use actual DB to test
@Rollback(false)
public class RoleRepositoryTests {

	@Autowired
	private RoleRepository roleRepository;

	@Test
	public void testCreateAdminRole() {
		Role admin = new Role("Admin", "Admin disscription");

		Role savedRole = roleRepository.save(admin);

		assertThat(savedRole.getRoleId()).isGreaterThan(0);
	}

	@Test
	public void testCreateRestRoles() {
		Role roleSalesperson = new Role("Salesperson",
				"manage product price, " + "customers, shipping, orders and sales report");

		Role roleEditor = new Role("Editor", "manage categories, brands, " + "products, articles and menus");

		Role roleShipper = new Role("Shipper", "view products, view orders " + "and update order status");

		Role roleAssistant = new Role("Assistant", "manage questions and reviews");

		roleRepository.saveAll(List.of(roleSalesperson, roleEditor, roleShipper, roleAssistant));
	}
}
