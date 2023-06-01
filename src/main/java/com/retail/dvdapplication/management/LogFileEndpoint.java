package com.retail.dvdapplication.management;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/*
* Uses spring actuator to create an endpoint that
* gives access to the output of the application's logfile
* in order to enable remote debugging
*/
@Component
@Endpoint(id = "logs")
public class LogFileEndpoint {

    private final String logfilePath = "./logs/dvdServer.log";

    @ReadOperation
    public String readLogFile() throws IOException {
        return new String(Files.readAllBytes(Paths.get(logfilePath)));
    }
}
