package com.project01.quiz.config;

import com.project01.quiz.entity.RoleEntity;
import com.project01.quiz.entity.UserEntity;
import com.project01.quiz.repository.RoleRepository;
import com.project01.quiz.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Configuration
@Slf4j
public class ApplicationInit {



    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    ApplicationRunner init(UserRepository userRepository, RoleRepository roleRepository) {
        return args -> {
            List<RoleEntity> roleEntity=roleRepository.findAll();
            if(roleEntity.size()==0){
                List<RoleEntity> roles= new ArrayList<>();
                RoleEntity role1=RoleEntity.builder()
                        .code("ADMIN")
                        .name("admin").build();
                RoleEntity role2=RoleEntity.builder()
                        .code("USER")
                        .name("khach").build();
                roles.add(role1);
                roles.add(role2);
                roleRepository.saveAll(roles);
            }
            else{
                boolean user = userRepository.existsByUsername("admin");
                if(user==false){
                    List<RoleEntity> role = new ArrayList<>();
                    role.add(roleRepository.findById(1L).get());
                    UserEntity admin = UserEntity.builder()
                            .username("admin")
                            .password(passwordEncoder.encode("admin"))
                            .email("abc@gmail.com")
                            .birth(new Date())
                            .fullname("Tran Quoc Dat")
                            .roles(role)
                            .build();
                    userRepository.save(admin);
                }
                else{
                    log.info("Admin Exits");
                }
            }
        };
    }

}
