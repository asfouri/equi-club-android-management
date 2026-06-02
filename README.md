<!-- ========================================================= -->
<!--                       PROJECT HEADER                      -->
<!-- ========================================================= -->

<div align="center">

# 🐎 EquiClub Manager

### Native Android Equestrian Club Management Application

A complete Android application for managing an equestrian club, including horse registry, rider management, riding session planning, health monitoring, scheduling conflict detection, and admin statistics.

<br>

![Android](https://img.shields.io/badge/Platform-Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)
![Java](https://img.shields.io/badge/Language-Java-F89820?style=for-the-badge&logo=openjdk&logoColor=white)
![Realm](https://img.shields.io/badge/Database-Realm-39477F?style=for-the-badge&logo=realm&logoColor=white)
![Gradle](https://img.shields.io/badge/Build-Gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white)
![Android Studio](https://img.shields.io/badge/IDE-Android%20Studio-3DDC84?style=for-the-badge&logo=androidstudio&logoColor=white)

<br>

![Status](https://img.shields.io/badge/Status-Portfolio%20Project-success?style=flat-square)
![Architecture](https://img.shields.io/badge/Architecture-Native%20Android-blue?style=flat-square)
![UI](https://img.shields.io/badge/UI-XML%20Layouts-purple?style=flat-square)
![Database](https://img.shields.io/badge/Data-Local%20Persistence-orange?style=flat-square)

</div>

---

## 📌 Project Summary

**EquiClub Manager** is a native Android application developed to digitalize the daily management of an equestrian club.

The application provides two different experiences:

- **Admin space** for managing horses, riders, planning, statistics, and club operations.
- **Rider space** for viewing available horses, accessing horse details, and interacting with club information.

This project demonstrates practical Android development skills including **Java programming**, **Realm local database integration**, **RecyclerView-based lists**, **role-based navigation**, **CRUD operations**, **session scheduling**, **data filtering**, and **dashboard statistics visualization**.

---

## 🎯 Main Objective

The goal of this project is to replace manual club organization with a structured mobile system that helps administrators manage:

- Horse information
- Rider accounts
- Riding sessions
- Horse health states
- Session conflicts
- Weekly planning
- Club activity statistics

The application is especially useful for equestrian clubs that need a simple mobile tool to organize daily sessions and monitor horse availability.

---

## 🧠 What This Project Demonstrates

This project is not only a UI prototype. It includes real application logic and local data management.

It demonstrates:

| Area | Implementation |
|---|---|
| Mobile development | Native Android app using Java |
| Local persistence | Realm Database |
| UI design | XML layouts, cards, forms, dialogs |
| List rendering | RecyclerView with custom adapters |
| Role management | Admin and rider navigation flow |
| CRUD logic | Create, read, update, delete horses, users, and sessions |
| Scheduling | Session planning with date and time |
| Validation | Availability and conflict checks |
| Dashboard | Statistics and monitoring cards |
| Data visualization | Planning chart with MPAndroidChart |
| Project organization | Android Studio + Gradle structure |

---

## 🧩 Application Modules

The application is divided into several functional modules.

```txt
EquiClub Manager
│
├── Authentication Module
│   ├── Login
│   ├── Register
│   ├── Demo admin access
│   └── Demo rider access
│
├── Admin Module
│   ├── Dashboard
│   ├── Horse management
│   ├── Rider management
│   ├── Session management
│   └── Statistics monitoring
│
├── Rider Module
│   ├── Rider home
│   ├── Horse search
│   ├── Horse details
│   ├── Session request flow
│   └── Club information
│
├── Data Module
│   ├── Realm initialization
│   ├── Demo data seeding
│   ├── Horse model
│   ├── User model
│   └── Session model
│
└── UI Module
    ├── XML layouts
    ├── Drawable resources
    ├── Menus
    ├── Cards
    └── Dialogs
