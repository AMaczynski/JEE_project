package pl.edu.agh;

import pl.edu.agh.impl.MenuSoapService;

import javax.xml.ws.Endpoint;

public class MenuSoapServicePublisher {
    public static void main(String[] args) {
        Object implementor = new MenuSoapService();
        String address = "http://localhost:9000/Employee";
        Endpoint.publish(address, implementor);
    }
}
