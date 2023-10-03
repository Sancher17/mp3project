package com.alex.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.sql.Blob;


@Service
public class LobService {

    @PersistenceContext
    private EntityManager entityManager;

    public Blob createBlob(InputStream content, long size) {
        Session session = entityManager.unwrap(Session.class);
        return session.getLobHelper().createBlob(content, size);
    }
}
