package com.cs.CyberSecurityProject;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.*;

@RestController
@RequestMapping("/check")
@CrossOrigin(origins = "*")
public class UrlCheckController {

    private static final String VT_API_KEY = "96172db7a6e6f29db8592de91d0637802ede94a48f6d57f0985b2a6deda45710";
    private static final String VT_API_URL = "https://www.virustotal.com/api/v3/urls";

    private static final String GOOGLE_API_KEY = "AIzaSyACd5OYoXlL-fM6Zr6LUw0HPCjkXWIz63k";
    private static final String GOOGLE_API_URL = "https://safebrowsing.googleapis.com/v4/threatMatches:find?key=" + GOOGLE_API_KEY;

    @PostMapping
    public Map<String, String> checkUrl(@RequestParam String url) {
        Map<String, String> results = new HashMap<>();
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper mapper = new ObjectMapper();

        // ---------------- VIRUSTOTAL CHECK ----------------
        try {
            HttpHeaders vtHeaders = new HttpHeaders();
            vtHeaders.set("x-apikey", VT_API_KEY);
            vtHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            HttpEntity<String> vtRequest = new HttpEntity<>("url=" + url, vtHeaders);

            // Step 1: Submit URL
            ResponseEntity<String> vtResponse = restTemplate.postForEntity(VT_API_URL, vtRequest, String.class);
            JsonNode vtRoot = mapper.readTree(vtResponse.getBody());
            String analysisId = vtRoot.path("data").path("id").asText();

            // Step 2: Get analysis report
            String reportUrl = "https://www.virustotal.com/api/v3/analyses/" + analysisId;
            HttpEntity<Void> vtEntity = new HttpEntity<>(vtHeaders);
            ResponseEntity<String> reportResponse = restTemplate.exchange(reportUrl, HttpMethod.GET, vtEntity, String.class);

            JsonNode reportRoot = mapper.readTree(reportResponse.getBody());
            JsonNode stats = reportRoot.path("data").path("attributes").path("stats");

            int harmless = stats.path("harmless").asInt();
            int malicious = stats.path("malicious").asInt();

            results.put("virusTotal", "✅ Harmless: " + harmless + " | ❌ Malicious: " + malicious);
        } catch (Exception e) {
            results.put("virusTotal", "⚠️ Error in VirusTotal: " + e.getMessage());
        }

        // ---------------- GOOGLE SAFE BROWSING CHECK ----------------
        try {
            HttpHeaders gHeaders = new HttpHeaders();
            gHeaders.setContentType(MediaType.APPLICATION_JSON);

            String requestBody = "{"
                    + "\"client\": {\"clientId\": \"cybersecurity-tool\", \"clientVersion\": \"1.0\"},"
                    + "\"threatInfo\": {"
                    + "\"threatTypes\": [\"MALWARE\", \"SOCIAL_ENGINEERING\", \"POTENTIALLY_HARMFUL_APPLICATION\", \"UNWANTED_SOFTWARE\"],"
                    + "\"platformTypes\": [\"ANY_PLATFORM\"],"
                    + "\"threatEntryTypes\": [\"URL\"],"
                    + "\"threatEntries\": [{\"url\": \"" + url + "\"}]"
                    + "}}";

            HttpEntity<String> gRequest = new HttpEntity<>(requestBody, gHeaders);
            ResponseEntity<String> gResponse = restTemplate.postForEntity(GOOGLE_API_URL, gRequest, String.class);

            JsonNode gRoot = mapper.readTree(gResponse.getBody());

            if (gRoot.has("matches")) {
                List<String> threats = new ArrayList<>();
                for (JsonNode match : gRoot.path("matches")) {
                    String type = match.path("threatType").asText();
                    String platform = match.path("platformType").asText();
                    String threatUrl = match.path("threat").path("url").asText();
                    threats.add(type + " detected on " + platform + " → " + threatUrl);
                }
                results.put("googleSafeBrowsing", "⚠️ Unsafe\n" + String.join("\n", threats));
            } else {
                results.put("googleSafeBrowsing", "✅ Safe");
            }


        } catch (Exception e) {
            // Clean error message parsing
            String errorMsg = e.getMessage();
            try {
                ObjectMapper errMapper = new ObjectMapper();
                JsonNode errorJson = errMapper.readTree(errorMsg);

                String code = errorJson.path("error").path("code").asText();
                String message = errorJson.path("error").path("message").asText();
                String reason = errorJson.path("error").path("status").asText();

                if ("PERMISSION_DENIED".equals(reason) || "SERVICE_DISABLED".equals(reason)) {
                    results.put("googleSafeBrowsing", "⚠️ Google Safe Browsing API is not enabled for this project. Please enable it in Google Cloud Console.");
                } else {
                    results.put("googleSafeBrowsing", "⚠️ Error in Google Safe Browsing: " + code + " - " + message);
                }
            } catch (Exception inner) {
                results.put("googleSafeBrowsing", "⚠️ Error in Google Safe Browsing: " + errorMsg);
            }
        }

        return results;
    }
}
