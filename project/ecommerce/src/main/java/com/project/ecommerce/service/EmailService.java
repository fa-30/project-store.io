package com.project.ecommerce.service;





public interface EmailService  {
    String sendMail(String to,String cc, String subject, String body);
    //String sendMail1(String to,String cc, String subject, Integer body);
}
