## Instant Messaging App - CHATTO

<img src="/app/src/main/ic_launcher-playstore.png" alt="Chatto App Logo" width="200"/>

---

## 📱 Device Specifications
- **Model Name:** Samsung Galaxy A50  
- **Model Code:** SM-A505FN/DS  
- **Android Version:** 11  

---

## 🛠️ Libraries and Frameworks
- **UI Framework:** [Android Jetpack Compose](https://developer.android.com/jetpack/compose)  
- **Dependency Injection:** [HILT](https://developer.android.com/training/dependency-injection/hilt-android)  

---

## 💡 Implementation Highlights
In addition to the minimum project requirements, the app includes a **Login feature**, which allows users to enter their phone number at the first launch. Key implementation details:  

- **Conditional Navigation:** A custom `Dispatcher` component handles navigation logic:  
  - If the user enters a valid phone number, it is saved in the local database as a user profile, and the app navigates to the **Chat screen**. On subsequent launches, the user bypasses the Login screen.  
  - If the phone number is invalid or missing, the user remains on the Login screen until a valid number is provided.  

- **Navigation Management:** All app navigation is implemented using Jetpack Compose’s **Navigation Component**.  

- **Chat Simulation:**  
  - Each chat is assigned a random avatar to simulate a real conversation partner.  
  - Messages are generated randomly from a predefined list to mimic a realistic chat experience.  

---

## ⚙️ Features
- User authentication via phone number  
- Persistent user profile stored locally  
- Dynamic and condition-based navigation  
- Simulated chat conversations with avatars and randomized messages  

---

## 📂 Project Structure
The project follows a modular architecture for easier maintenance and scalability, leveraging **Jetpack Compose** components and **HILT** for clean dependency management.
