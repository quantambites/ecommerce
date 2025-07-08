<h1 align="center"># 🛍️ Ecommerce – Full-Stack Shopping Platform</h1>

Ecommerce is a full-stack cloud-based shopping platform allowing users to register, browse products, manage cart, place orders via PayPal, and leave reviews. It includes a Spring Boot backend and a Vite-powered React frontend deployed via Firebase.

---

## 📚 Table of Contents

- [✨ Introduction](#-introduction)
- [🚀 Get Started](#-get-started)
  - [📦 Backend Setup (Spring Boot)](#-backend-setup-spring-boot)
  - [🛍️ Frontend Setup (React - Vite)](#-frontend-setup-react)
- [🔐 Environment Variables](#-environment-variables)
  - [Frontend `.env`](#frontend-env)
  - [Backend `application.properties`](#backend-applicationproperties)
- [📂 Project Structure](#-project-structure)
- [🛠️ Technologies Used](#️-technologies-used)
- [🔗 Live Project](#-live-project)
---

## ✨ Introduction

Ecommerce is a modern shopping platform that enables users to:

- Register and authenticate
- Browse and search products
- Add, update, and remove items in the cart
- Place and track orders via PayPal
- Post and view product reviews

It is composed of:
- A **Spring Boot** backend for handling users, products, cart, and orders
- A **React (Vite)** frontend hosted on **Firebase**

---

## 🚀 Get Started

### 📦 Backend Setup (Spring Boot)

1. Clone the repo:
   ```bash
   git clone https://github.com/quantambites/ecommerce.git
   cd ecommerce/server
   ```

2. Install dependencies and run:
   - Using Maven:
     ```bash
     ./mvnw clean install
     ./mvnw spring-boot:run
     ```

   - Or via your IDE (IntelliJ/VSCode): Run `EcommerceServerApplication.java`

---

### 📱 Frontend Setup (React - Vite)

1. Navigate to frontend directory:
   ```bash
   cd ecommerce/client
   ```

2. Install dependencies:
   ```bash
   npm install
   ```

3. Set up your environment variables (see below) , remove the firebase hosting files

4. Start the app:
   ```bash
   npm run dev
   ```

---

## 🔐 Environment Variables

### Frontend `.env`

> Place in `cloud_in_frontend/.env`

```env
VITE_BACKEND_URL=

```

---

### Backend `application.properties`

> Place in `cloud_in_backend/src/main/resources/application.properties`

```properties
spring.application.name=
server.port=
spring.frontend.origin=
spring.cache.type=
spring.redis.uri=
spring.data.mongodb.uri=
spring.servlet.multipart.max-file-size=
spring.servlet.multipart.max-request-size=
cloudinary.cloud-name=
cloudinary.api-key=
cloudinary.api-secret=
paypal.client.id=
paypal.client.secret=
paypal.mode=

```

⚠️ **Never commit these values directly. Use `.env` and `.gitignore` to keep them secure.**

---

## 📂 Project Structure

```
ecommerce/
│
├── server/              # Spring Boot backend
│   └── src/main/java/   # Controllers, Services, Models, Repositories, Config
│
├── client/              # React frontend (Firebase ready)
│   └── src/             # Components, Pages, Redux slices, Utils
│
├── .gitignore
└── README.md
```

---

## 🛠️ Technologies Used

- **Frontend:** React + Vite + Redux Toolkit + Axios
- **Backend:** Spring Boot + MongoDB + Cloudinary + Redis
- **Auth:** Cookie-based JWT Auth
- **Database:** MongoDB Atlas
- **Image Hosting:** Cloudinary
- **Payments:** PayPal (sandbox)
- **Caching:** Redis cloud


---
### 🔗 Live Project

[https://ecommerce-d6069.firebaseapp.com/](https://ecommerce-d6069.firebaseapp.com/)






<p align="center">
  <img src="Screenshot 2025-07-09 012750.png" alt="App Screenshot" width="500"/>
</p>
