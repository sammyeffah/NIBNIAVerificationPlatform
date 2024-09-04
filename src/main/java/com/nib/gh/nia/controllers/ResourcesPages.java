package com.nib.gh.nia.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ResourcesPages {

   @GetMapping("/nia/verification")
   public String getVerificationData(Model model, Authentication authentication) {
      String username = authentication.getName();
      model.addAttribute("username", username);
      return "nia/verification";
   }


   @GetMapping("/access-denied")
   public String getAccessDenied() {
     
      return "access-denied";
   }
   @GetMapping("/admin")
   public String getAdmin(Model model, Authentication authentication) {
      String username = authentication.getName();
      model.addAttribute("username", username);
      return "admin";
   }

}
