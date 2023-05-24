package com.greenblat.etuep.service;

import com.greenblat.etuep.dto.DocumentInfoResponse;
import com.greenblat.etuep.dto.DocumentResponse;
import com.greenblat.etuep.exception.ResourceNotFoundException;
import com.greenblat.etuep.mapper.DocumentMapper;
import com.greenblat.etuep.model.Document;
import com.greenblat.etuep.model.User;
import com.greenblat.etuep.repository.DocumentRepository;
import com.greenblat.etuep.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final DocumentRepository documentRepository;
    private final DocumentMapper documentMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(user -> new org.springframework.security.core.userdetails.User(
                        user.getUsername(),
                        user.getPassword(),
                        Collections.emptyList()
                ))
                .orElseThrow(() -> new UsernameNotFoundException("Failed to retrieve user: " + username));
    }

    public List<DocumentResponse> getDocumentsByUser(UserDetails userDetails) {
        String username = userDetails.getUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("user with name [%s] not found", username)
                ));

        return user.getDocuments().stream()
                .map(document -> documentMapper.mapToDto(username, document))
                .collect(Collectors.toList());
    }

    public DocumentInfoResponse getDocument(Long id) {
        Document document = documentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("document not found"));
        return documentMapper.mapToInfoDto(document);
    }
}
