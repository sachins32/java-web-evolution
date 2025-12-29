# User Management App (Spring MVC REST API)

This branch documents the modernization of the User Management System into a **RESTful Web Service**. By replacing traditional JSPs with a JSON-based API, the application now follows a decoupled architecture, allowing the backend to serve any client—be it a React/Angular frontend, a Mobile app, or third-party services.



---

## 🚀 REST-Specific Features

* **Stateless Communication**: The server no longer manages HTTP sessions or UI state; it purely serves raw data, making it easier to scale.
* **JSON Serialization**: Integrated **Jackson Databind** to automatically convert Java Entities into JSON strings via the `MappingJackson2HttpMessageConverter`.
* **RESTful Routing**: Implementation of proper HTTP verbs to follow industry standards:
    * `GET /api/users` -> Fetch all users.
    * `POST /api/users` -> Create a new user.
    * `PUT /api/users/{id}` -> Update an existing user.
    * `DELETE /api/users/{id}` -> Remove a user.
* **Standardized Responses**: Leveraged `ResponseEntity<T>` to provide meaningful HTTP Status Codes (e.g., `201 Created` for new entries, `404 Not Found` for missing resources).

---

## 🛠 Tech Stack

* **Framework**: Spring Framework 6.x (Spring MVC, Spring ORM)
* **JSON Provider**: Jackson Databind 2.15+
* **ORM Provider**: Hibernate 6.x
* **Database**: MySQL 8.0
* **API Testing**: Postman / cURL
* **Server**: Apache Tomcat 10.1.x
* **Build Tool**: Maven

---

## 📂 Project Structure

In this branch, the `webapp` folder is minimized as we move away from Server-Side Rendering (JSPs) toward a data-driven API.

```text
UserManagementApp/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/sachin/
│   │   │       ├── controller/
│   │   │       │   └── UserRestController.java  # @RestController implementation
│   │   │       ├── config/
│   │   │       │   └── AppConfig.java          # Removed ViewResolver; Enabled Web MVC
│   │   │       └── ... (model, dao remains similar)
└── pom.xml                                     # Added Jackson dependencies
```

## 🚀 Key Implementation Details

### 1. @Controller vs @RestController
The most significant change in this branch is the switch from the traditional `@Controller` to **`@RestController`**. In our previous MVC branch, `@Controller` was used to return JSP view names. In this REST branch, `@RestController` (which is a convenience annotation combining `@Controller` and `@ResponseBody`) ensures that every method's return value is serialized directly into the HTTP response body as JSON.



### 2. Explicit Parameter Binding (Spring 6 Modernization)
A critical update for Spring 6 compatibility was the move to **explicit parameter naming**. Older versions of Spring could "guess" the name of a URL variable by looking at the Java parameter name. However, Spring 6 requires explicit mapping unless the `-parameters` compiler flag is specifically set.

We updated our endpoints to include names within the annotations to ensure the API remains robust across all environments:

```java
@GetMapping("/{id}")
public ResponseEntity<User> getUser(@PathVariable("id") int id) {
    // Explicitly mapping the URI template variable "{id}" 
    // to the method parameter "int id"
    return ResponseEntity.ok(userService.findById(id));
}
```

## ⚠️ Challenges & Resolutions (REST Transition)

Transitioning to a headless REST API revealed several framework-level requirements introduced in Spring 6.

### 1. The "Name for argument of type [int] not specified" Error
* **Issue**: Accessing a specific user via `GET /api/users/1` resulted in an **HTTP 500 Internal Server Error**.
* **Cause**: Spring 6 removed the legacy `LocalVariableTable` introspection. Without the `-parameters` compiler flag, Spring’s Reflection API cannot "guess" which URL variable maps to which Java parameter name.
* **Resolution**: Updated all `@PathVariable` and `@RequestParam` annotations to include the explicit name:
    * *Example:* `@PathVariable("id") int id`.



### 2. 406 Not Acceptable / JSON Conversion
* **Issue**: The API returned a **406 Not Acceptable** error despite the data being successfully retrieved from the database.
* **Cause**: The application lacked the **Jackson Databind** library. Without this on the classpath, Spring’s `HttpMessageConverter` could not find a way to serialize Java objects into JSON format.
* **Resolution**: Added `jackson-databind` to the `pom.xml`. Spring's `@EnableWebMvc` automatically detected the library and configured the necessary JSON converters.

---

## 📝 Learning Notes

* **Content Negotiation**: Learned how Spring uses the `Accept` request header to determine the desired response format (e.g., JSON vs XML) and selects the appropriate `HttpMessageConverter`.
* **Decoupling**: By removing the `ViewResolver`, the backend is now **"Frontend Agnostic"**, meaning the same API can power a web dashboard, a mobile app, or a CLI tool.
* **Semantic HTTP Status Codes**: Moved beyond basic `200 OK` responses to provide better API feedback:
    * `201 Created` for successful POSTs.
    * `204 No Content` for successful deletions.
    * `404 Not Found` for non-existent IDs.



---

## 📖 How to Run (REST Testing)

Follow these steps to test the API endpoints:

### 1. Start the Application
Build the project using `mvn clean install` and deploy the generated `.war` file to **Tomcat 10.1.x**.

### 2. Fetch Data (GET)
Open **Postman** or your browser and send a `GET` request to:
`http://localhost:8080/UserManagementApp/api/users`

### 3. Send Data (POST)
To create a new user, send a `POST` request to the same URL with the following JSON body:

```json
{
  "name": "Sachin",
  "email": "sachin@example.com"
}
```