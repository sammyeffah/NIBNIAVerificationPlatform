// package com.nib.gh.nia.config;

// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.boot.CommandLineRunner;
// import org.springframework.stereotype.Component;
// import java.time.LocalDate;

// @Component
// public class ExpirationCheck implements CommandLineRunner {

//     @Value("${app.expiration-date}")
//     private String expirationDate;

//     @Override
//     public void run(String... args) throws Exception {
//         LocalDate currentDate = LocalDate.now();
//         LocalDate expiryDate = LocalDate.parse(expirationDate);

//         if (currentDate.isAfter(expiryDate)) {
//             System.out.println("The application has expired and will now exit.");
//             System.exit(0);
//         }
//     }
// }

