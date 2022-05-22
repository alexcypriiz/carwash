package ru.aisa.carwash.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.aisa.carwash.model.Role;
import ru.aisa.carwash.model.Client;
import ru.aisa.carwash.repository.RoleRepository;
import ru.aisa.carwash.repository.ClientRepository;

import java.util.Collections;

@Service
public class ClientService implements UserDetailsService {
    final
    ClientRepository clientRepository;
    final
    RoleRepository roleRepository;
    final
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public ClientService(ClientRepository clientRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.clientRepository = clientRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Client client = clientRepository.findByEmail(email);

        if (client == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return client;
    }

    public boolean saveClient(Client client) {
        Client clientFromDB = clientRepository.findByEmail(client.getEmail());

        if (clientFromDB != null) {
            return false;
        }

        client.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));
        client.setPassword(bCryptPasswordEncoder.encode(client.getPassword()));
        clientRepository.save(client);
        return true;
    }

}
