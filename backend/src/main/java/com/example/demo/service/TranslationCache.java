package com.example.demo.service;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TranslationCache {

    private final Map<String, String> cache = new ConcurrentHashMap<>();

    public String get(String text) {
        return cache.get(text);
    }

    public void put(String original, String translated) {
        cache.put(original, translated);
    }
}