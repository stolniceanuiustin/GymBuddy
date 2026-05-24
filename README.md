### GYMBUDDY - Gym Tracker in React and Spring 

# Features pentru Assignment 2: 
##### 1. Login pentru Admin/User + Register pentru User + Forget Password 
##### 2. Interfata care permite administratorului sa actualizeze catalogul de exercitii - doar administratorul poate
##### 3. Interfata care permite utilizatorului sa isi schimbe datele personale + sa vada statistici (greutate, (inaltime maybe daca sunt mai mici de 20 de ani :) ). 


# Features pentru assignment 3: 
### 1. Workout tracking(functia primara a aplicatiei)
### 2. Notificari cu achievmenturi cannd atingi un nou PR la un exercitiu 

# Featrues pentru proiect: 
### Raport XML Cu toate zilele la sala 
### Integrare model ML care estimeaza bodyfat-ul. 

# How to start:

### 1. Start the database
\`\`\`bash
docker compose up -d
\`\`\`

### 2. Start the Backend (Java)
In your IDE (IntelliJ/Eclipse), start the following Spring Boot applications:
- \`AuthApplication\` (Port 8082)
- \`BackendApplication\` (Gym Service - Port 8080)

### 3. Start the Body Fat Service (Python)
Navigate to \`backend/bodyfat-service\` and run:
\`\`\`bash
pip install -r requirements.txt
python server.py
\`\`\`
The service will run on **Port 8000**.

### 4. Start the Frontend (React)
Navigate to the \`frontend\` folder and run:
\`\`\`bash
npm run dev
\`\`\`

---

# Architecture & Routing Explanation

### 1. User Authentication (Direct Routing)
The **Auth Service** is called directly from the frontend via Axios. When you login or register, the React app talks directly to port **8082**.

### 2. Gym Data & XML Export (Direct Routing)
The **Gym Service** (port **8080**) handles workout logging and the XML Export feature. The XML Export uses a **Strategy Pattern** (\`FileExporter\` interface) to generate the file, which is then sent back to the browser as a download blob.

### 3. Body Fat Prediction (Proxy Routing / Backend-to-Backend)
Unlike the login service, the **Body Fat Service** is NOT called directly by the frontend. 
- **Routing Flow:** React UI -> Gym Service (8080) -> Body Fat Service (8000).
- **How it works:** When you click "Predict Body Fat", the Gym Service acts as a proxy. It collects your physical measurements from the database and uses a \`RestTemplate\` to make a POST request to the Python microservice. 
- **Why this way?** This follows SOLID principles and ensures that your physical data remains secure within the backend network. The Gym Service handles the persistence (saving the log to the DB) and the Python service handles only the math/ML logic.

---

# GymBuddy Application

## Test Credentials
The following mock accounts are available for testing (generated on startup):

- **Admin User**
  - Username: \`admin\`
  - Email: \`admin@gymbuddy.com\`
  - Password: \`password123\`
  - Role: \`ADMINISTRATOR\`

- **Standard User**
  - Username: \`gym_pro\`
  - Email: \`pro@gymbuddy.com\`
  - Password: \`password123\`
  - Role: \`STANDARD_USER\`


-----------------------------------
Note

A1: 5

A2: 7,5

A3: 8

P1: 9,5

P2: 9

P3: 9

P Implementation: 

P Final Design: 

---------------------------------------

Nota Lab 1: 6,83

Nota Lab 2: 
