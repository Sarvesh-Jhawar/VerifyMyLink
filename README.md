🛡️ Verify MyLink
A tool for enhancing user safety by verifying download links using trusted third-party APIs.

📝 Problem Statement
Users frequently search for and download software from the internet. Unfortunately, top search results often include malicious links that lead to malware or phishing websites. This project addresses this risk by providing a tool that verifies download links to ensure they are safe before a user proceeds.

✨ Features
URL Safety Check: Provides instant feedback on whether a link is safe or malicious.

Third-Party API Integration: Utilizes the VirusTotal and Google Safe Browsing APIs for real-time analysis against multiple antivirus engines and threat databases.

User-Friendly Interface: Features a simple, clean, and intuitive web interface for non-technical users to check any link.

Color-Coded Results: Displays safe results in green and unsafe results in red for clear visual feedback.

🏗️ System Design
The system is composed of three main components:

Architecture
Frontend: A web page (index.html) built with HTML, CSS, and JavaScript. It provides the user interface for submitting URLs and displaying results.

Backend: A Spring Boot REST controller (UrlCheckController.java) written in Java. It handles requests from the frontend, communicates with the third-party APIs, analyzes the URL, and sends the results back.

Third-Party APIs: The VirusTotal API and Google Safe Browsing API provide real-time analysis of URLs.

Workflow
A user enters a URL into the form on the frontend.

The frontend sends a POST request with the URL to the backend's /check endpoint.

The backend submits the URL to the VirusTotal and Google Safe Browsing APIs.

The APIs return an analysis report.

The backend processes the API response and sends a formatted result back to the frontend.

The frontend displays the result in a color-coded box based on the analysis.

💻 Technologies Used
Backend: Java 21, Spring Boot, RestTemplate, Jackson for JSON processing.

Frontend: HTML, CSS, and JavaScript.

APIs: VirusTotal API and Google Safe Browsing API.

⚙️ Prerequisites
Java Development Kit (JDK) 21 or later.

Apache Maven (The project uses the Maven Wrapper, so a separate installation is not required).

API Keys: The application requires API keys for both VirusTotal and Google Safe Browsing. These are currently hardcoded in the src/main/java/com/cs/CyberSecurityProject/UrlCheckController.java file and may be subject to request limits.

🚀 How to Run the Project
Clone the Repository

Bash

git clone https://github.com/sarvesh-jhawar/verifymylink.git
cd verifymylink/CyberSecurityProject
Check Prerequisites
Ensure you have JDK 21 installed by running:

Bash

java -version
Run the Backend Server
Navigate to the CyberSecurityProject directory and execute the following command using the Maven Wrapper:

For Linux/macOS:

Bash

./mvnw spring-boot:run
For Windows:

Bash

./mvnw.cmd spring-boot:run
This command starts the Spring Boot application on port 8081.

Access the Frontend
Once the backend is running, open a web browser and go to http://localhost:8081. The frontend will be served automatically, and you can begin checking URLs.

📈 Future Work
Developing a Chrome/Firefox extension for direct browser integration.

Integrating AI models to improve phishing detection.

Creating and maintaining a database of trusted vendors.

Deploying the tool publicly for wider access.

📄 License
This project is licensed under the Apache License, Version 2.0. See the LICENSE file for details.
