package com.OnlineCart.Utils;

import jakarta.servlet.http.HttpServletRequest;

public class CommonUtil {

    public static Boolean sendMail()
    {
        return false;
    }

    public static String generateUrl(HttpServletRequest request)
    {
        return request.getRequestURL().toString();

    }
}
