package com.cs.CyberSecurityProject;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/check")
@CrossOrigin(origins = "*")
public class UrlCheckController {

    private static final String API_KEY = "96172db7a6e6f29db8592de91d0637802ede94a48f6d57f0985b2a6deda45710"; // paste your VirusTotal key
    private static final String API_URL = "https://www.virustotal.com/api/v3/urls";

    @PostMapping
    public String checkUrl(@RequestParam String url) {
        try {
            RestTemplate restTemplate = new RestTemplate();

            // Step 1: Encode the URL
            HttpHeaders headers = new HttpHeaders();
            headers.set("x-apikey", API_KEY);
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            HttpEntity<String> request = new HttpEntity<>("url=" + url, headers);

            // Step 2: Submit URL to VirusTotal
            ResponseEntity<String> response = restTemplate.postForEntity(API_URL, request, String.class);

            // Parse JSON to get the analysis ID
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response.getBody());
            String analysisId = root.path("data").path("id").asText();

            // Step 3: Get analysis report
            String reportUrl = "https://www.virustotal.com/api/v3/analyses/" + analysisId;
            HttpEntity<Void> entity = new HttpEntity<>(headers);
            ResponseEntity<String> reportResponse = restTemplate.exchange(reportUrl, HttpMethod.GET, entity, String.class);

            // Parse final report
            JsonNode reportRoot = mapper.readTree(reportResponse.getBody());
            JsonNode stats = reportRoot.path("data").path("attributes").path("stats");

            int harmless = stats.path("harmless").asInt();
            int malicious = stats.path("malicious").asInt();

            return "✅ Harmless: " + harmless + " | ❌ Malicious: " + malicious;

        } catch (Exception e) {
            e.printStackTrace();
            return "Error checking URL: " + e.getMessage();
        }
    }
}
