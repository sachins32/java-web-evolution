# Simple User Management Servlet App

A lightweight Java Web Application built using **Jakarta Servlet API** and **Tomcat 10**. This project demonstrates the core fundamentals of the Request-Response cycle using `doGet` and `doPost`.

## 🚀 Features
- **Welcome Page**: A simple HTML interface to interact with the app.
- **Create User (POST)**: Collects user data from a form and stores it in an in-memory `ArrayList`.
- **View Users (GET)**: Dynamically generates an HTML list of all registered users.
- **Post-Redirect-Get Pattern**: Implementation of `sendRedirect` to prevent duplicate form submissions on page refresh.

## 🛠 Tech Stack
- **Language**: Java 17+
- **Specification**: Jakarta EE 10 (Servlet API 6.0)
- **Server**: Apache Tomcat 10.1.x
- **Build Tool**: Maven
- **IDE**: IntelliJ IDEA (Community Edition) with **Smart Tomcat** plugin

## 📂 Project Structure
```text
UserManagementApp/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── User.java          # Data Model
│   │   │   └── UserServlet.java   # The Controller (Logic)
│   │   ├── webapp/
│   │   │   └── index.html         # Front-end Form
├── pom.xml                        # Maven Dependencies
└── README.md
```

## ⚙️ Setup Instructions (Ubuntu)

### 1. Prerequisites
Ensure you have the following installed:
- `java -version` (OpenJDK 17 or 21)
- `mvn -version` (Apache Maven)

### 2. Tomcat Setup
1. Download Tomcat 10 (tar.gz) from the official website.
2. Extract it to your home directory: `tar -xvf apache-tomcat-10.1.x.tar.gz`.
3. Give execution permissions: `chmod +x bin/*.sh`.

### 3. IntelliJ Configuration
1. Install the **Smart Tomcat** plugin.
2. Go to `Run > Edit Configurations`.
3. Add a new **Smart Tomcat** configuration.
4. Set **Tomcat Server** to your extracted folder.
5. Set **Deployment Directory** to `src/main/webapp`.
6. Set **Context Path** to `/`.

### 4. Running the App
1. Click the **Green Play button** in IntelliJ.
2. Open your browser and navigate to: `http://localhost:8080`.

## 📝 Learning Notes
- **Why no main method?** This app is a "Library" (WAR) that is loaded and executed by the Tomcat Container.
- **In-Memory Storage**: Data is stored in an `ArrayList,` meaning data is lost when the server restarts (Next step: Database integration using JDBC!).

```text
The IntelliJ IDEA Community Edition (free version) does not natively support the "Tomcat Server" run 
configuration; that feature is exclusive to the paid Ultimate Edition.

To bypass this limitation, we use a third-party plugin called Smart Tomcat. This plugin allows 
Community Edition users to configure and run a local Tomcat server directly from the IDE, bridging 
the gap between the free tool and the required server environment.
```