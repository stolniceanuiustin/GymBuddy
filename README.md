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


# GymBuddy Application

## Test Credentials
The following mock accounts are available for testing (generated on startup):

- **Admin User**
  - Username: \`admin2\`
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
