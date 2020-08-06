package com.example.demo.service;

import com.google.common.collect.Maps;

import java.util.Map;

public interface CheckDataService {
    Map<String, Object> dealLoan();
    Map<String, Object> dealTransfer();
}
