package com.jstiven.demoRestaurante.service;

import com.jstiven.demoRestaurante.entity.UserEntity;
import com.jstiven.demoRestaurante.repository.UserEntityRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;



@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserEntityRepository userEntityRepository;

    public UserDetailsServiceImpl(UserEntityRepository userEntityRepository){
        this.userEntityRepository=userEntityRepository;
    }

    // Carga el usuario para que Spring gestione su autenticacion y autorizacion
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userEntityRepository.findUserEntityByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("El usuario "+ username +"no fue encontrado" ));

        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();

        // Lista de roles
        userEntity.getRoles()
                .forEach(role -> authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRol().name()))));

        // Lista de autorizaciones
        userEntity.getRoles().stream()
                .flatMap(role->role.getPermission().stream())
                .forEach(permission-> authorityList.add(new SimpleGrantedAuthority(permission.getName().name())));

        return new User(userEntity.getUsername(),
                userEntity.getPassword(),
                userEntity.isEnabled(),
                userEntity.isAccountNoExpired(),
                userEntity.isCredentialNoExpired(),
                userEntity.isAccountNoLocked(),
                authorityList);
    }
}
