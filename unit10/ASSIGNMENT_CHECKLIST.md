# IN352 Unit 10 — Task Tracker Assignment Checklist

## Step 1: Project Setup
- [x] Create Spring Boot project via Spring Initializr with web dependency
- [x] Organize codebase following MVC architecture (`controller/`, `service/`, `model/`, `repository/`)
- [x] Use Git for version control with regular, clearly-labeled commits

## Step 2: Core Functionality
- [ ] User registration and login using Spring Security (mock API or in-memory credentials)
- [ ] Task creation with fields: title, description, due date, priority (Low/Medium/High)
- [ ] Task dashboard view listing all tasks (card or table format)
- [ ] Task editing and deletion
- [ ] Filtering/searching tasks by status (completed, pending) and priority

**Screenshots to capture:**
- [ ] Registration/login screen — valid credentials
- [ ] Registration/login screen — invalid credentials
- [ ] Task creation form — completed inputs (buy milk, 2 packs, due 6/20/25, High priority)
- [ ] Task creation form — incomplete inputs
- [ ] Dashboard view with tasks displayed and filter/search controls visible

## Step 3: UI/UX Design
- [ ] Clean, user-friendly interface using CSS Grid or Flexbox
- [ ] Mobile-responsive layout
- [ ] Form validation for empty fields and invalid dates
- [ ] Confirmation messages and alerts for errors

**Screenshots to capture:**
- [ ] Responsive layout on desktop
- [ ] Form validation — successful case
- [ ] Form validation — failed case (title required, description "groceries", due date 1/1/23, must reject past date)
- [ ] Success toast (bottom right): "✔ Task 'buy milk' was created!"
- [ ] Error modal: "✖ please fill all required fields"

## Step 4: Error Handling and Logging
- [ ] `@ControllerAdvice` for exception handling
- [ ] Log major events (logins, task creation, API failures) via SLF4J
- [ ] Clear error messages displayed on frontend
- [ ] Validate all user inputs on both frontend and backend

**Screenshots to capture:**
- [ ] Logged error message in console or log file
- [ ] Frontend error message triggered by invalid input
- [ ] Validation failure shown via Java annotations (`@NotNull`, etc.)

## Step 5: API Optimization and External Integration
- [ ] Paginate task listings (10 tasks per page)
- [ ] Use a public date/time API to timestamp tasks
- [ ] Use a weather API to display current weather on dashboard

**Screenshots to capture:**
- [ ] Paginated task list showing navigation between pages
- [ ] Date/time API response displayed in UI
- [ ] Weather API data embedded in dashboard

## Step 6: Project Structure and Documentation
- [ ] Standardized project structure:
  ```
  src/main/java/com/example/taskmanager/
  ├── controller/
  ├── service/
  ├── model/
  └── repository/
  src/main/resources/
  ├── templates/
  ├── static/
  └── application.properties
  ```
- [ ] Design flowchart (MS Visio) showing key workflows: user registration, task creation, filtering logic

**Screenshots to capture:**
- [ ] Project folder structure
- [ ] Snippet of application.properties with API key
- [ ] README file

## Submission Package
- [ ] Complete Java Spring Boot project directory (builds and runs without errors)
- [ ] `TaskApp_Screenshots.docx` — all screenshots above, each with a descriptive caption
- [ ] Visio flowchart file
- [ ] `README.md` — project overview, features, setup/install, running instructions, external API references
- [ ] Zip everything into one archive
- [ ] Name convention: `IN352_<YourLastName>_Unit10`
- [ ] Review rubric before submitting
- [ ] Submit to Unit 10 Assignment Dropbox by end of unit
