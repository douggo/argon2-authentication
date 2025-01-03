package com.douggo.login.usecases;

import com.douggo.login.application.gateway.UserGateway;
import com.douggo.login.application.usecases.ListAllUsersUseCase;
import com.douggo.login.domain.entity.User;
import com.douggo.login.infrastructure.gateway.Memory.UserGatewayMemory;
import com.douggo.login.infrastructure.gateway.mappers.UserMapper;
import com.douggo.login.infrastructure.persistence.user.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ListAllUsersUseCaseTest {

    private List<UserEntity> userRepository;
    private ListAllUsersUseCase listAllUsersUseCase;
    private final List<UUID> uuidList = new ArrayList<>();

    private void createDummyUsers(List<UserEntity> userRepository) {
        for (int i = 0; i < 10; i++) {
            UUID uuid = UUID.randomUUID();
            this.uuidList.add(uuid);
            userRepository.add(
                    new UserEntity(
                            uuid,
                            "John Doe Dummy",
                            "test" + i + "@mail.com.br",
                            LocalDate.of(2000,1, i + 1)
                    )
            );
        }
    }

    @BeforeEach
    public void setup() {
        this.userRepository = new ArrayList<>();
        UserMapper userMapper = new UserMapper();
        UserGateway userGateway = new UserGatewayMemory(userMapper, userRepository);
        this.listAllUsersUseCase = new ListAllUsersUseCase(userGateway);
    }

    @Test
    void shouldListAllUsers() {
        this.createDummyUsers(userRepository);
        AtomicInteger atomicInteger = new AtomicInteger();
        List<User> users = this.listAllUsersUseCase.execute();
        assertEquals(10, users.size());
        users.forEach(user -> {
            assertEquals(uuidList.get(atomicInteger.get()), user.getId());
            assertEquals("test" + atomicInteger + "@mail.com.br", user.getEmail());
            assertEquals(LocalDate.of(2000,1, atomicInteger.get() + 1), user.getDateOfBirth());
            atomicInteger.getAndIncrement();
        });
    }

    @Test
    void shouldBringEmptyListIfNoUsersAreCreated() {
        List<User> users = this.listAllUsersUseCase.execute();
        assertEquals(0, users.size());
    }


}
