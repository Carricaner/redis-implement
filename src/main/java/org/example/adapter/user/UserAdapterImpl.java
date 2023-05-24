package org.example.adapter.user;

import org.example.adapter.datasource.redis.model.RoleModel;
import org.example.adapter.datasource.redis.repository.RoleRepository;
import org.example.core.usecase.user.port.UserAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Set;

@Component
public class UserAdapterImpl implements UserAdapter {

    private final RedisTemplate<String, String> template;
    private final RoleRepository roleRepository;

    @Autowired
    public UserAdapterImpl(
            RedisTemplate<String, String> template, RoleRepository roleRepository) {
        this.template = template;
        this.roleRepository = roleRepository;
    }

    @Override
    public void createRole() {
//        RoleModel adminRole = new RoleModel();
//        adminRole.setName("admin");
//        RoleModel customerRole = new RoleModel();
//        customerRole.setName("customer");
//        roleRepository.save(adminRole);
//        roleRepository.save(customerRole);
        String key = "+++key+++";
        template.opsForZSet().add(key,
                String.valueOf(Instant.now().getEpochSecond()), Instant.now().getEpochSecond());
        Set<String> values = template.opsForZSet().rangeByScore(key, 1684906355, 1684906372);
        System.out.println(values.size() + "ooooooo");
    }
}
