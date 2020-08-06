package com.example.demo.service;

import javax.servlet.http.HttpServletResponse;

public interface TransferDetailService {
    void deal();

    void transferDetailService(HttpServletResponse response);

    void dealTotal();
}
