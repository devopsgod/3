package by.vstu.service;

import by.vstu.exception.BusinessEntityNotFoundException;
import by.vstu.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientService implements ClientDetailsService {

    private final ClientRepository repository;

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        return repository.findByClientId(clientId)
                .orElseThrow(() -> new BusinessEntityNotFoundException("Client not found"));
    }
}
