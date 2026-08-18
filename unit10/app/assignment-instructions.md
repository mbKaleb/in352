ASSIGNMENT DETAILS
Enhancing User Experience (UX) in Web Applications Using AI

UNIT OUTCOMES

Apply software design principles.
Improve UI/UX design.
Integrate error handling, logging, and validation.
Optimize API calls and resource usage.
Document an application’s design and flow
COURSE OUTCOME

IN352-6: Design interactive web applications.

PURPOSE

In this assignment, you will design and implement a Task Tracker web application with the backend developed using Java and Spring Boot, and the frontend built with HTML, CSS, and JavaScript or Thymeleaf templates.

This project aims to help you:

Apply key software design principles.
Enhance UI/UX design for better usability.
Implement robust error handling, logging, and validation.
Optimize API usage and system resource management.
Thoroughly document your application's architecture and workflow.
STEP 1: PROJECT SETUP

Create a new Spring Boot project via Spring Initializr with at least the web dependency.
Organize your codebase following the MVC (Model-View-Controller) architecture.
Use Git for version control and make regular commits with clear messages.
STEP 2: CORE FUNCTIONALITY (STANDARDIZED IMPLEMENTATION)

Required Task Management Features:

User registration and login using Spring Security with a mock API or in-memory credentials.
Task creation with fields: title, description, due date, and priority (Low, Medium, High).
Task dashboard view to list all tasks in a card or table format.
Task editing and deletion options.
Filtering and searching tasks by status (completed, pending, and priority).
Submit screenshots of the following:

The registration and login screen, both with valid and invalid credentials. Use the data listed in the sample.

Screenshot showing the registration and login screen, both with valid and invalid credentials.

The task creation form showing completed and incomplete inputs. Use the data listed in the sample.

The task creation form showing completed and incomplete inputs for buying milk, 2 packs, due on 6.20.25, with high priority. 

The dashboard view with tasks displayed and filter/search controls visible. Use the data listed in the sample.

Screenshot of the The dashboard view with tasks displayed and filter/search controls visible.
STEP 3: UI/UX DESIGN (CONSISTENT LAYOUT AND FEEDBACK)

Build a clean, user-friendly interface using CSS Grid or Flexbox that meets the requirements below:

Mobile-responsive layout.
Form validation for empty fields and invalid dates.
Confirmation messages and alerts for errors.
Submit screenshots of the following:

The responsive layout on desktop.

The responsive layout on desktop.

Form validation in action, showing both successful and failed input cases. Use the data listed in the sample.

Form validation in action, showing both successful and failed input cases. Title required, description - groceries, due date 1.1.23, date must be future. 

Confirmation or alert messages appearing after an action. Use the data listed in the sample.

Confirmation or alert messages appearing after an action. Success toast (bottom right): [check] Task "buy milk" was created! Error Modal: [X] please fill all required fields.
STEP 4: ERROR HANDLING AND LOGGING

Ensure robust backend error handling and meaningful user feedback based on the requirements below. Use the data listed in the sample.

Use @ControllerAdvice for exception handling.
Log major events (logins, task creation, API failures) using SLF4J or similar.
Display clear error messages on the frontend.
Validate all user inputs on both frontend and backend.
 Submit screenshots of the following:

A logged error message in the console or log file. Use the data listed in the sample.



A frontend error message triggered by invalid input. Use the data listed in the sample.



A validation failure shown via Java annotations (@NotNull, etc.). Use the data listed in the sample.


STEP 5: API OPTIMIZATION AND EXTERNAL INTEGRATION

Integrate and optimize the use of APIs using the following requirements:

Paginate task listings (10 tasks per page). Use the data listed in the sample.



Use a public date/time API to timestamp tasks.



Use a weather API to display the current weather. Use the data listed in the sample.
Submit screenshots of the following:

The paginated task list showing navigation between pages.
The date/time API response displayed in your UI.
The weather API data embedded into your dashboard.


STEP 6: PROJECT STRUCTURE AND DOCUMENTATION

Follow this standardized project structure:

src/

├── main/

│   ├── java/com/example/taskmanager/

│   │   ├── controller/

│   │   ├── service/

│   │   ├── model/

│   │   └── repository/

│   ├── resources/

│   │   ├── templates/

│   │   ├── static/

│   │   └── application.properties

├── pom.xml or build.gradle

Submit screenshots of the following:

Your project folder structure.



A snippet of your application properties with any API key.



Your README file, clearly explaining how to run the project.


Using MS Visio, create a design flowchart showing key application workflows.

SUBMISSION REQUIREMENTS

Your submission should include the following components, organized and clearly labeled:

Java Project Files
Include your complete Java Spring Boot project folder structured as follows:
src/main/java/ – All Java source files (controllers, models, services, repositories, etc.)
src/main/resources/ – Configuration files, templates (Thymeleaf), and static resources (CSS, JS, images).
pom.xml
Ensure your project builds and runs without errors.
Screenshots Document (in Microsoft Word format)

Create a Microsoft Word document titled TaskApp_Screenshots.docx and include all the previous labeled screenshots. Each screenshot should include a caption describing what the screenshot represents.
Design Flowchart

Create a design flowchart using Microsoft Visio showing key application workflows (user registration, task creation, filtering logic).
README File

Include a README.md file that contains:
Project overview and purpose.
Features and functionality.
Set up and installation instructions.
Running the application (commands and usage guide).
External API references (weather, date/time).
How to Submit

Zip all the following into a single .zip archive file:

The entire Java project directory
The Microsoft Word document with all screenshots
The Visio flowchart file
The README.md file
Name your assignment document according to this convention: IN352_<YourLastName>_Unit10. Submit your completed assignment to the Unit 10 Assignment Dropbox by the end of the unit.

Review the rubric before beginning this activit
