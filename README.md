# 🛡️ Verify MyLink

**Verify MyLink** is a web-based tool designed to enhance user safety by verifying download links using trusted third-party APIs.

---

## 📝 Problem Statement

Users frequently search for and download software from the internet. Unfortunately, top search results often include malicious links that can lead to malware or phishing websites.  

**Verify MyLink** addresses this risk by providing a tool that verifies download links to ensure they are safe before a user proceeds.

---

## ✨ Features

- **URL Safety Check:** Provides instant feedback on whether a link is safe or malicious.  
- **Third-Party API Integration:** Utilizes the VirusTotal and Google Safe Browsing APIs for real-time analysis against multiple antivirus engines and threat databases.  
- **User-Friendly Interface:** Simple, clean, and intuitive web interface for non-technical users.  
- **Color-Coded Results:** Displays safe results in green and unsafe results in red for clear visual feedback.  

---

## 🏗️ System Design

### Architecture

The system is composed of three main components:

1. **Frontend:**  
   - Built with HTML, CSS, and JavaScript.  
   - Provides the user interface for submitting URLs and displaying results.

2. **Backend:**  
   - Spring Boot REST controller written in Java (`UrlCheckController.java`).  
   - Handles requests from the frontend, communicates with third-party APIs, analyzes URLs, and sends the results back.

3. **Third-Party APIs:**  
   - VirusTotal API  
   - Google Safe Browsing API  

### Workflow

1. User enters a URL in the frontend form.  
2. Frontend sends a `POST` request with the URL to the backend `/check` endpoint.  
3. Backend submits the URL to VirusTotal and Google Safe Browsing APIs.  
4. APIs return an analysis report.  
5. Backend processes the response and sends a formatted result back to the frontend.  
6. Frontend displays the result in a color-coded box based on the analysis.  

---

## 💻 Technologies Used

- **Backend:** Java 21, Spring Boot, RestTemplate, Jackson for JSON processing  
- **Frontend:** HTML, CSS, JavaScript  
- **APIs:** VirusTotal API, Google Safe Browsing API  

---

## ⚙️ Prerequisites

- **Java Development Kit (JDK) 21** or later  
- **Apache Maven** (Maven Wrapper included, no separate installation required)  
- **API Keys:**  
  - VirusTotal API key  
  - Google Safe Browsing API key  
  > Keys are currently hardcoded in `src/main/java/com/cs/CyberSecurityProject/UrlCheckController.java` and may be subject to request limits.  
