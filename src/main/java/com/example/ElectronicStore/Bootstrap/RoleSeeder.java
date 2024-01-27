package com.example.ElectronicStore.Bootstrap;

import com.example.ElectronicStore.Entity.Role;
import com.example.ElectronicStore.Entity.RoleEnum;
import com.example.ElectronicStore.Repository.RoleRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Optional;

@Component
public class RoleSeeder implements ApplicationListener<ContextRefreshedEvent> {

    private final RoleRepository roleRepository;

    public RoleSeeder(RoleRepository roleRepository){
        this.roleRepository = roleRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        this.loadRoles();
    }

    private void loadRoles(){
        RoleEnum[]  roleNames = new RoleEnum[] {RoleEnum.ADMIN,RoleEnum.USER};
        Arrays.stream(roleNames).forEach((roleName)->{
            Optional<Role> optionalRole = roleRepository.findByName(roleName);
            optionalRole.ifPresentOrElse(System.out::println, () -> {
                Role roleToCreate = new Role();
                roleToCreate.setName(roleName);
                roleRepository.save(roleToCreate);
            });
        });
    }
}
