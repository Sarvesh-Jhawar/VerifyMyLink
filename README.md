Verify MyLink

Problem Statement
Nowadays users frequently search for software in their browsers.

Unfortunately, many users tend to click on the top search results, assuming they are the correct download links.

However, these results often include links that lead to malicious files.

This tool aims to enhance user safety by providing verified and secure download options depending on the availability.

Description
Verify MyLink is a tool designed to address the risk of users downloading software from unverified sources. Many of the top results from search engines can lead to malicious websites that distribute malware or phishing content. This project solves this problem by providing a front-end and back-end tool that verifies download links using trusted third-party services like the VirusTotal and Google Safe Browsing APIs.

Features
URL Safety Check: Verifies URLs to provide instant feedback on whether a link is safe or malicious.

Third-Party API Integration: Uses the VirusTotal and Google Safe Browsing APIs to provide real-time analysis of URLs against multiple antivirus engines and threat databases.

User-Friendly Interface: Provides a simple and clean web interface for non-technical users to check any link.

System Design
Architecture
The system is composed of three main components:

Frontend (index.html): A web page built with HTML, CSS, and JavaScript that includes an input form to submit URLs.

Backend (Spring Boot): A REST controller that handles requests from the frontend, connects with the third-party APIs, analyzes the URL, and sends the results back.

Third-Party APIs: Services like VirusTotal and Google Safe Browsing that provide real-time analysis of URLs.

Workflow
A user enters a URL into the form on the frontend.

The frontend sends a POST request with the URL to the backend's /check endpoint.

The backend submits the URL to the VirusTotal and Google Safe Browsing APIs.

The APIs return an analysis report (e.g., harmless vs. malicious).

The backend processes the response and sends a formatted result back to the frontend.

The frontend displays the result in a color-coded box (green for safe, red for unsafe).

Technologies Used
Backend: Java 21, Spring Boot, RestTemplate, and Jackson for JSON processing.

Frontend: HTML, CSS, and JavaScript.

APIs: VirusTotal API and Google Safe Browsing API.

Prerequisites
Java Development Kit (JDK) 21 or later

Apache Maven

Note: The application requires API keys for both VirusTotal and Google Safe Browsing. These are currently hardcoded in src/main/java/com/cs/CyberSecurityProject/UrlCheckController.java and may be subject to request limits.

How to Run the Project
Clone the Repository:

git clone [your-repository-url]
cd CyberSecurityProject

Check Prerequisites: Ensure you have JDK 21 or a compatible version installed on your system. You can check your version by running:

java -version

Run the Backend Server: The project uses the Maven Wrapper (mvnw), so you do not need to have Maven installed separately. Execute the following command from the CyberSecurityProject directory:

./mvnw spring-boot:run

This command will start the Spring Boot application on http://localhost:8081. You will see log messages in the terminal indicating that the application has started successfully.

Access the Frontend: Once the backend is running, open a web browser and navigate to http://localhost:8081. The frontend HTML page will be served automatically, and you can begin checking URLs.

Future Work
The following enhancements are planned for future versions of the project:

Developing a Chrome/Firefox extension for direct browser integration.

Integrating AI models to improve phishing detection.

Creating and maintaining a database of trusted vendors.

Deploying the tool publicly for wider access.
