package com.blank04.service;

import com.blank04.model.Request;
import com.blank04.repository.RequestRepository;
import org.springframework.stereotype.Service;

@Service
public class RequestService {

    private final RequestRepository requestRepository;

    public RequestService(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    public void save(String type, String request, String response) {
        requestRepository.save(new Request(null, type, request, response));
    }
}
