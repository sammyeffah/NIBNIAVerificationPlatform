package com.nib.gh.nia.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import java.time.LocalDate;

@Component
public class ExpirationMonitor {

    @Value("${app.expiration-date}")
    private String expirationDate;

    private final ApplicationContext context;

    public ExpirationMonitor(ApplicationContext context) {
        this.context = context;
    }

    @Scheduled(fixedRate = 86400000) // Check once a day (86400000 milliseconds)
    public void checkExpiration() {
        LocalDate currentDate = LocalDate.now();
        LocalDate expiryDate = LocalDate.parse(expirationDate);

        if (currentDate.isAfter(expiryDate)) {
            System.out.println("The application has expired and will now exit.");
            ((ConfigurableApplicationContext) context).close();
        }
    }
}
