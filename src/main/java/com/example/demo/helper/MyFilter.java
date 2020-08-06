package com.example.demo.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;
import java.util.UUID;

/**
 * @program: demo
 * @description: 过滤器
 * @author: py
 * @create: 2019-12-11 09:58
 **/

@Component
public class MyFilter implements Filter {
    private Logger log = LoggerFactory.getLogger(MyFilter.class);

    private static final String UNIQUE_ID = "traceId";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("filter初始化");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        boolean bInsertMDC = insertMDC();
        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            if (bInsertMDC) {
                MDC.remove(UNIQUE_ID);
            }
        }
    }

    @Override
    public void destroy() {
        System.out.println("filter销毁");
    }

    private boolean insertMDC() {
        UUID uuid = UUID.randomUUID();
        String uniqueId = uuid.toString().replace("-", "");
        MDC.put(UNIQUE_ID, uniqueId);
        return true;
    }

}
