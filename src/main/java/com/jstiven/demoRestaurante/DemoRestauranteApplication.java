package com.jstiven.demoRestaurante;

import com.jstiven.demoRestaurante.entity.*;
import com.jstiven.demoRestaurante.repository.UserEntityRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.Permission;
import java.util.List;
import java.util.Set;

@SpringBootApplication
public class DemoRestauranteApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoRestauranteApplication.class, args);
	}

	@Bean
	CommandLineRunner init(UserEntityRepository userEntityRepository, PasswordEncoder passwordEncoder){
		return args -> {

			// CREACION DE PERMISOS

			// Permiso para crear
			PermissionEntity createPermission = PermissionEntity
					.builder()
					.name(PermissionEnum.CREATE)
					.build();

			// Permiso para leer
			PermissionEntity readPermission = PermissionEntity
					.builder()
					.name(PermissionEnum.READ)
					.build();

			// Permiso para editar
			PermissionEntity updatePermission = PermissionEntity
					.builder()
					.name(PermissionEnum.UPDATE)
					.build();

			// Permiso para eliminar
			PermissionEntity deletePermission = PermissionEntity
					.builder()
					.name(PermissionEnum.DELETE)
					.build();


			// CREACION DE ROLES

			RoleEntity administratorRol = RoleEntity
					.builder()
					.rol(RoleEnum.ADMINISTRATOR)
					.permission(Set.of(createPermission,readPermission,updatePermission,deletePermission))
					.build();

			RoleEntity waiterRol = RoleEntity
					.builder()
					.rol(RoleEnum.WAITER)
					.permission(Set.of(readPermission))
					.build();

			// CREACION DE USUARIOS

			UserEntity administratorUser = UserEntity
					.builder()
					.username("ADMIN")
					.password(passwordEncoder.encode("2167"))
					.roles(Set.of(administratorRol))
					.accountNoExpired(true)
					.accountNoLocked(true)
					.credentialNoExpired(true)
					.isEnabled(true)
					.build();

			UserEntity waiterUser = UserEntity
					.builder()
					.username("WAITER")
					.password(passwordEncoder.encode("1234"))
					.roles(Set.of(waiterRol))
					.accountNoExpired(true)
					.accountNoLocked(true)
					.credentialNoExpired(true)
					.isEnabled(true)
					.build();

			userEntityRepository.saveAll(List.of(administratorUser, waiterUser));


		};
	}

}
