package com.example.demo.service;

import javax.servlet.http.HttpServletResponse;

public interface LoanDetailService {
    void deal();
    void exportExcel(HttpServletResponse response);

    void updateName();
}
