package com.nib.gh.nia.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;

import com.nib.gh.nia.model.ResponseData;
import com.nib.gh.nia.model.UserDTO;
import com.nib.gh.nia.model.Users;
import com.nib.gh.nia.model.VerificationData;
import com.nib.gh.nia.services.UserService;
import com.nib.gh.nia.utility.DoRequest;
import java.util.List;

@RestController
@RequestMapping("/nib/api/nia")
public class ResourcesClasses {

    Logger logger = LogManager.getLogger(ResourcesClasses.class);

    @Autowired
    private UserService userService;
    @Value("${VERIFICATION.URL}")
    private String verifyURL;

    @GetMapping("/verify-data/all")
    public List<VerificationData> getAllVerifyData() {
        List<VerificationData> verificationDatas = null;

        try {
            String responseReceived = new DoRequest().sendGet(verifyURL.replace("#ACTION#", "all"));
            // logger.info("RESPONSE:: " + responseReceived);
            if (responseReceived != null) {
                JSONObject respJSON = new JSONObject(responseReceived);
                if (respJSON.optString("code").equals("00")) {
                    // logger.info("ACTUAL DATA:: " + respJSON.optJSONArray("data"));
                    verificationDatas = convertJSONArrayToList(respJSON.optJSONArray("data"));
                    // logger.info("LIST:: " + verificationDatas);
                    // System.out.println("ARRAY LIST:: " + verificationDatas);

                } else {
                    return null;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception, " + e.getMessage());
        }
        return verificationDatas;
    }

    @GetMapping("/verify-data")
    public String getVerifyDataByGuid(@RequestParam("shortGuid") String shortGuid, @RequestParam("reason") String reason) {
        String verificationDatas = null;
        try {
            String responseReceived = new DoRequest()
                    .sendGet(verifyURL.replace("#ACTION#", "by_guid&guid=" + shortGuid + "&reason=" + reason));
            logger.info("DATA:: " + responseReceived);
            if (responseReceived != null) {
                JSONObject respJSON = new JSONObject(responseReceived);
                if (respJSON.optString("code").equals("00")) {
                    verificationDatas = respJSON.optJSONArray("data").length() > 0
                            ? respJSON.optJSONArray("data").optJSONObject(0).toString()
                            : "";
                    logger.info("ACTUAL DATA:: " + verificationDatas);
                } else {
                    return null;
                }
            }

        } catch (Exception e) {
            logger.error("Exception, " + e.getMessage());
        }
        return verificationDatas;
    }

    // public static void main(String[] args) {
    // PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    // System.out.println(passwordEncoder.encode("Passw0rdS@m|"));
    // }

    public List<VerificationData> convertJSONArrayToList(JSONArray jsonArray) {
        List<VerificationData> verificationDataList = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            VerificationData verificationData = new VerificationData();

            verificationData.setId(jsonObject.getInt("id"));
            verificationData.setNationalId(jsonObject.getString("nationalId"));
            verificationData.setRequestStatus(jsonObject.getBoolean("requestStatus"));
            verificationData.setTransactionId(jsonObject.getString("transactionId"));
            verificationData.setRequestTime(jsonObject.getString("requestTime"));
            verificationData.setResponseTime(jsonObject.optString("responseTime", null));
            verificationData.setName(jsonObject.optString("name", null));
            verificationData.setPhoneNumber(jsonObject.optString("phoneNumber", null));
            verificationData.setDigitalAddress(jsonObject.optString("digitalAddress", ""));
            verificationData.setOtherDigitalAddress(jsonObject.optString("otherDigitalAddress", ""));
            verificationData.setCenter(jsonObject.optString("center", ""));
            verificationData.setDateOfBirth(jsonObject.optString("dateOfBirth", ""));
            verificationData.setCardValidFrom(jsonObject.optString("cardValidFrom", ""));
            verificationData.setCardValidTo(jsonObject.optString("cardValidTo", ""));
            verificationData.setCardId(jsonObject.optString("cardId", ""));
            verificationData.setReason(jsonObject.optString("reason", ""));

            verificationDataList.add(verificationData);
        }

        return verificationDataList;
    }

    @GetMapping("/get-users")
    public List<Users> getUsers() {

        return userService.findAll();
    }

    @PostMapping("/add-user")
    public String addUsers(@RequestBody UserDTO user) {
        logger.info("REQUEST:: " + user);
        String respMsg = userService.saveUser(user);
        logger.info("RESPONSE:: " + respMsg);
        if (respMsg.split("#")[0].equals("00"))
            return "{\r\n" + //
                    "    \"status\": \"00\",\r\n" + //
                    "    \"message\": \"Added successfully\"\r\n" + //
                    "}";
        else
            return "{\r\n" + //
                    "    \"status\": \"00\",\r\n" + //
                    "    \"message\": \"Failed\"\r\n" + //
                    "}";
    }

    @PostMapping("/update-user")
    public ResponseData editUsers(@RequestBody UserDTO user) {
        logger.info("REQUEST:: " + user);
        String respMsg = userService.updateUser(user);
        logger.info("RESPONSE:: " + respMsg);
        if (respMsg.split("#")[0].equals("00"))
            return new ResponseData("00", "Updated successfully");
        else
            return new ResponseData("06", "Failed");

    }

    @GetMapping("/delete-user/{user_id}")
    public ResponseData deleteUsers(@PathVariable("user_id") long id) {
        logger.info("REQUESTED ID:: " + id);

        if (userService.deleteById(id))
            return new ResponseData("00", "Added successfully");
        else
            return new ResponseData("06", "Failed");

    }

    @GetMapping("/enable-user/{user_id}")
    public ResponseData enableUsers(@PathVariable("user_id") long id) {
        logger.info("REQUESTED ID:: " + id);

        if (userService.enableById(id))
            return new ResponseData("00", "Added successfully");
        else
            return new ResponseData("06", "Failed");

    }

    @GetMapping("/disable-user/{user_id}")
    public ResponseData disableUsers(@PathVariable("user_id") long id) {
        logger.info("REQUESTED ID:: " + id);

        if (userService.disableById(id))
            return new ResponseData("00", "Added successfully");
        else
            return new ResponseData("06", "Failed");

    }

}
