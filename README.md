# 🐎 EquiClub Manager

<div align="center">

![Android](https://img.shields.io/badge/Platform-Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)
![Java](https://img.shields.io/badge/Language-Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Realm](https://img.shields.io/badge/Database-Realm-39477F?style=for-the-badge&logo=realm&logoColor=white)
![Gradle](https://img.shields.io/badge/Build-Gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white)
![Android Studio](https://img.shields.io/badge/IDE-Android%20Studio-3DDC84?style=for-the-badge&logo=androidstudio&logoColor=white)

<br>

**EquiClub Manager** is a native Android application designed to manage an equestrian club.  
It provides an organized digital system for managing horses, riders, riding sessions, planning conflicts, health monitoring, and club activity statistics.

</div>

---

## 📌 Table of Contents

- [Overview](#-overview)
- [Project Objectives](#-project-objectives)
- [Main Features](#-main-features)
- [User Roles](#-user-roles)
- [Screenshots](#-screenshots)
- [Technical Architecture](#-technical-architecture)
- [Technologies Used](#-technologies-used)
- [Database Models](#-database-models)
- [Project Structure](#-project-structure)
- [Demo Accounts](#-demo-accounts)
- [How to Run the Project](#-how-to-run-the-project)
- [Security Notes](#-security-notes)
- [Future Improvements](#-future-improvements)
- [Author](#-author)

---

## 📖 Overview

**EquiClub Manager** is a mobile application built with **Java** in **Android Studio**.  
The application is designed for an equestrian club environment where administrators need to manage horses, riders, and riding sessions, while riders need quick access to horse information and club services.

The project uses **Realm Database** for local data persistence and provides a role-based experience between:

- **Admin users**
- **Rider / Cavalier users**

The application includes a complete admin dashboard, horse management module, rider management module, planning system, session scheduling, and horse availability tracking.

---

## 🎯 Project Objectives

The goal of this project is to provide a simple and efficient Android solution for managing an equestrian club.

The application helps the club to:

- Digitize horse records
- Manage riders and club users
- Schedule riding sessions
- Detect planning conflicts
- Monitor sick or unavailable horses
- Display useful admin statistics
- Provide riders with easy access to horse information
- Improve daily organization inside the club

---

## ✨ Main Features

### 🔐 Authentication System

- User login with email and password
- Account creation screen
- Role-based redirection
- Admin mode access
- Rider mode access
- Quick access demo buttons
- Share application option

### 🧑‍💼 Admin Dashboard

- Admin welcome dashboard
- Weekly session statistics
- Horse usage percentage
- Most used horse display
- Conflict tracking
- Planning performance chart
- Sick horse monitoring
- Stopped horse monitoring
- Quick access to main management modules

### 🐎 Horse Management

- Display all club horses
- Search horses by name, breed, or rider
- Filter horses by status
- View horse details
- Add a new horse
- Update horse information
- Delete horses
- Track horse health status
- Detect unavailable horses
- Assign horses to riders

### 📅 Session Planning

- Display all planned sessions
- Filter sessions by day or week
- Search sessions by horse, rider, or date
- Add new riding sessions
- Edit existing sessions
- Cancel sessions
- Detect scheduling conflicts
- Prevent scheduling with unavailable horses
- Display completed sessions

### 🧍 Rider Space

- Rider home dashboard
- Search available horses
- View horse details
- View horses of the day
- Access session-related actions
- Contact club administration
- Access club information page

### 📊 Statistics and Monitoring

- Number of weekly sessions
- Completed sessions count
- Upcoming sessions count
- Horse utilization percentage
- Sick horse count
- Stopped horse count
- Conflict count
- Planning performance chart

---

## 👥 User Roles

### 1. Admin

The admin has access to the management side of the application.

Admin can:

- Manage horses
- Manage riders
- Manage sessions
- View statistics
- Monitor horse health
- Detect planning conflicts
- Add, edit, and delete club data

### 2. Rider / Cavalier

The rider has access to a simplified user interface.

Rider can:

- View available horses
- Search for horses
- View horse details
- Access club-related information
- Contact administration
- Navigate through rider-specific screens

---

## 📸 Screenshots

The screenshots are organized by user type and feature.

---

## 🔐 Authentication Screens

### Login Screen

<p align="center">
  <img src="screenshots/auth/login-screen.png" width="260" alt="Login Screen">
</p>

### Register Screen

<p align="center">
  <img src="screenshots/auth/register-screen.png" width="260" alt="Register Screen">
</p>

---

## 🧑‍💼 Admin Screens

### Admin Dashboard Overview

<p align="center">
  <img src="screenshots/admin/admin-dashboard-overview.png" width="260" alt="Admin Dashboard Overview">
</p>

### Health and Capacity Monitoring

<p align="center">
  <img src="screenshots/admin/admin-dashboard-health-capacity.png" width="260" alt="Admin Health Capacity">
</p>

### Admin Overflow Menu

<p align="center">
  <img src="screenshots/admin/admin-overflow-menu.png" width="260" alt="Admin Overflow Menu">
</p>

### Horse Management List

<p align="center">
  <img src="screenshots/admin/horse-management-list.png" width="260" alt="Horse Management List">
</p>

### Empty Horse Management Form

<p align="center">
  <img src="screenshots/admin/horse-management-form-empty.png" width="260" alt="Horse Management Empty Form">
</p>

### Filled Horse Management Form

<p align="center">
  <img src="screenshots/admin/horse-management-form-filled.png" width="260" alt="Horse Management Filled Form">
</p>

### Delete Horse Confirmation

<p align="center">
  <img src="screenshots/admin/delete-horse-confirmation-dialog.png" width="260" alt="Delete Horse Confirmation Dialog">
</p>

### Session Planning List

<p align="center">
  <img src="screenshots/admin/session-planning-list.png" width="260" alt="Session Planning List">
</p>

### Edit Session Dialog

<p align="center">
  <img src="screenshots/admin/edit-session-dialog.png" width="260" alt="Edit Session Dialog">
</p>

---

## 🧍 Rider Screens

### Rider Home Dashboard

<p align="center">
  <img src="screenshots/rider/rider-home-dashboard.png" width="260" alt="Rider Home Dashboard">
</p>

### Rider Overflow Menu

<p align="center">
  <img src="screenshots/rider/rider-overflow-menu.png" width="260" alt="Rider Overflow Menu">
</p>

### Horse Detail - Available State

<p align="center">
  <img src="screenshots/rider/horse-detail-available-state.png" width="260" alt="Horse Detail Available State">
</p>

---

## 🐎 Horse Detail and Session Screens

### Horse Detail - Unavailable State

<p align="center">
  <img src="screenshots/horse-details/horse-detail-unavailable-state.png" width="260" alt="Horse Detail Unavailable State">
</p>

### Unavailable Horse Toast Message

<p align="center">
  <img src="screenshots/horse-details/horse-detail-unavailable-toast.png" width="260" alt="Unavailable Horse Toast">
</p>

### Schedule Session Dialog - Date

<p align="center">
  <img src="screenshots/horse-details/schedule-session-dialog-date.png" width="260" alt="Schedule Session Date Dialog">
</p>

### Schedule Session Dialog - Time Keyboard

<p align="center">
  <img src="screenshots/horse-details/schedule-session-dialog-time-keyboard.png" width="260" alt="Schedule Session Time Keyboard">
</p>

### Session Scheduled Successfully

<p align="center">
  <img src="screenshots/horse-details/schedule-session-success.png" width="260" alt="Schedule Session Success">
</p>

---

## 🏗 Technical Architecture

The application follows a simple native Android architecture based on:

- Java Activity classes
- XML layout files
- Realm local database
- RecyclerView adapters
- Model classes
- Utility/data management classes
- Role-based navigation logic

### General Flow

```text
User opens app
      ↓
LoginActivity
      ↓
Role verification
      ↓
Admin Dashboard or Rider Space
      ↓
Access to horses, riders, sessions, and statistics
