<div align="center">

# 🐎 EquiClub Manager

### Native Android Equestrian Club Management Application

**A complete Java Android application for managing an equestrian club: horses, riders, sessions, health states, planning conflicts, and admin statistics.**

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
![Database](https://img.shields.io/badge/Data-Realm%20Local%20Database-orange?style=flat-square)
![Version](https://img.shields.io/badge/Version-1.0-lightgrey?style=flat-square)

</div>

---

## 📑 Table of Contents

- [Overview](#-overview)
- [Project Purpose](#-project-purpose)
- [Portfolio Highlights](#-portfolio-highlights)
- [User Roles](#-user-roles)
- [Feature Set](#-feature-set)
- [Application Workflow](#-application-workflow)
- [Technical Architecture](#-technical-architecture)
- [Data Layer and Realm Database](#-data-layer-and-realm-database)
- [Database Models](#-database-models)
- [Main Android Components](#-main-android-components)
- [Business Logic](#-business-logic)
- [UI and Design System](#-ui-and-design-system)
- [Screenshots](#-screenshots)
- [Technologies Used](#-technologies-used)
- [Dependencies](#-dependencies)
- [Project Structure](#-project-structure)
- [Build Configuration](#-build-configuration)
- [How to Run](#-how-to-run)
- [Demo Access](#-demo-access)
- [Testing Strategy](#-testing-strategy)
- [Security Notes](#-security-notes)
- [Future Improvements](#-future-improvements)
- [Author](#-author)

---

## 📌 Overview

**EquiClub Manager** is a native Android application developed with **Java** and **Android Studio**. It is designed to help an equestrian club manage its daily operations from a mobile device.

The application provides two main experiences:

- **Admin space**: complete management of horses, riders, sessions, dashboard indicators, health monitoring, and planning.
- **Rider space**: simplified interface to search horses, view horse details, check availability, and access club actions.

The project uses **Realm Database** as an embedded local database, which means the application can store and manage data directly on the Android device without needing a remote server.

---

## 🎯 Project Purpose

The goal of this project is to replace manual equestrian club organization with a structured Android management system.

The app helps the club to:

- Digitize horse records.
- Manage rider accounts.
- Plan riding sessions.
- Track horse health and availability.
- Detect scheduling conflicts.
- Monitor weekly activity through an admin dashboard.
- Provide riders with quick access to horse information.
- Improve the organization of daily club operations.

---

## 🏆 Portfolio Highlights

This project demonstrates practical Android development skills through a real-world management use case.

| Area | Implementation |
|---|---|
| Native Android Development | Java Android application created with Android Studio |
| Local Database | Realm Database integration and local persistence |
| Role-Based Navigation | Separate Admin and Rider spaces |
| CRUD Operations | Create, read, update, and delete horses, riders, and sessions |
| Dynamic UI Lists | RecyclerView adapters for horses, riders, and sessions |
| Scheduling Logic | Date/time-based session planning |
| Conflict Detection | Avoids duplicate booking and unavailable horse scheduling |
| Dashboard Statistics | Calculates operational indicators from stored data |
| Data Visualization | Uses MPAndroidChart for planning performance |
| UI Design | XML layouts, cards, dialogs, menus, buttons, and status badges |
| Demo Data | App is ready to test immediately after launch |
| GitHub Readiness | Organized source code, screenshots, and documentation |

---

## 👥 User Roles

### 🧑‍💼 Admin

The admin has access to the operational and management side of the application.

Admin can:

- Access the admin dashboard.
- View weekly session statistics.
- Monitor horse utilization.
- Manage the full horse registry.
- Add, edit, and delete horses.
- Monitor sick and stopped horses.
- Manage riders and user accounts.
- View rider details.
- Plan, edit, and cancel riding sessions.
- Detect planning conflicts.
- Open admin navigation menu.
- Refresh dashboard statistics.

### 🧍 Rider / Cavalier

The rider has access to a simplified mobile space focused on horse consultation.

Rider can:

- Access the rider dashboard.
- Search for horses.
- View available horses.
- Open horse details.
- Check horse health status.
- See horses of the day.
- Access rider overflow menu.
- Open contact/about actions.
- Log out from the rider space.

---

## ✨ Feature Set

### 🔐 Authentication Module

The authentication module manages the first interaction with the app.

Features:

- Email/password login screen.
- Account creation screen.
- Admin quick access for demonstration.
- Rider quick access for demonstration.
- Role-based redirection after login.
- Share application action.

Main files:

```text
LoginActivity.java
activity_sign_up.java
activity_login.xml
activity_sign_up.xml
```

Authentication flow:

```text
User opens the app
        ↓
LoginActivity
        ↓
Credentials / quick access validation
        ↓
Role detection
        ↓
Admin dashboard OR Rider dashboard
```

---

### 🧑‍💼 Admin Dashboard Module

The admin dashboard gives a quick overview of club activity.

Features:

- Welcome admin section.
- Quick access buttons for planning and horses.
- Weekly sessions count.
- Horse utilization percentage.
- Most used horse indicator.
- Conflict counter.
- Planning performance chart.
- Sick horse monitoring.
- Stopped horse monitoring.
- Refresh cockpit button.

Main files:

```text
ActivityAdmin.java
activity_admin.xml
```

Dashboard indicators:

```text
Weekly sessions
Completed sessions
Upcoming sessions
Horse utilization rate
Most used horse
Planning conflicts
Sick horses
Stopped horses
```

---

### 🐎 Horse Management Module

The horse management module allows the admin to manage the club horse registry.

Features:

- Display all horses.
- Search horses by name, breed, or rider.
- Filter horses by status.
- Select a horse from the list.
- Add new horse.
- Modify horse information.
- Delete horse with confirmation dialog.
- Show horse image.
- Show health status badge.
- Show assigned rider.
- Track whether the horse has a course today.

Main files:

```text
activity_club_horses.java
Horse.java
HorseAdapter.java
DrawableResolver.java
activity_club_horses.xml
item_cheval.xml
```

Horse fields managed by the app:

```text
Name
Parent
Breeding farm
Breed / race
Age
Today course state
Health state
Assigned rider
Horse image reference
```

---

### 🐴 Horse Detail Module

This module displays a detailed horse profile.

Features:

- Horse image preview.
- Horse name.
- Parent information.
- Breeding farm.
- Race/breed.
- Age.
- Current daily course state.
- Health state.
- Update state action.
- Schedule session action.
- Return button.
- Toast feedback when horse is unavailable.

Main files:

```text
HorseDetailActivity.java
HorseInfoActivity.java
activity_horse_detail.xml
activity_horse_info.xml
```

---

### 📅 Session Planning Module

The planning module handles the creation, update, cancellation, search, and validation of riding sessions.

Features:

- Display all sessions.
- Display completed sessions.
- Search by horse, rider, or date.
- Filter by all sessions.
- Filter by today.
- Filter by week.
- Filter by alerts/conflicts.
- Plan a new session.
- Edit an existing session.
- Cancel a session.
- Detect conflicts.
- Validate unavailable horses.
- Show success message after scheduling.

Main files:

```text
ManageSessionsActivity.java
Session.java
SessionAdapter.java
SessionDateUtils.java
DataManager.java
activity_manage_sessions.xml
dialog_session.xml
dialog_schedule_session.xml
item_session.xml
```

Session fields:

```text
Date
Time
Rider / cavalier
Horse / cheval
Status
Completed state
Conflict state
```

---

### 👤 Rider Management Module

The rider management module is used by the admin to manage riders/users.

Features:

- Display rider list.
- Add new rider.
- View rider details.
- Store rider data locally.
- Use rider information in session planning.

Main files:

```text
RiderListActivity.java
RiderDetailActivity.java
AddUserActivity.java
RiderAdapter.java
User.java
activity_rider_list.xml
activity_rider_detail.xml
activity_add_user.xml
item_rider.xml
```

---

### 🧍 Rider Space Module

The rider space is a simplified user interface for club riders.

Features:

- Rider home screen.
- Search horse field.
- Search and clear actions.
- Horse of the day section.
- Horse cards.
- Horse detail access.
- Rider menu.
- Contact and about actions.
- Logout option.

Main files:

```text
activity_acceuil.java
RiderMenuActivity.java
BaseMenuActivity.java
activity_acceuil.xml
```

---

## 🔄 Application Workflow

```text
Application launch
        ↓
MyApplication initializes Realm
        ↓
Realm configuration is loaded
        ↓
Demo data is inserted if needed
        ↓
LoginActivity is displayed
        ↓
User authenticates or uses demo access
        ↓
Role is detected
        ↓
Admin opens management dashboard
Rider opens rider home space
        ↓
User interacts with horses, riders, sessions, and statistics
```

---

## 🏗 Technical Architecture

The project uses a classic native Android structure based on **Activities**, **XML layouts**, **RecyclerView adapters**, **Realm models**, and utility classes.

```text
┌──────────────────────────────────────┐
│              UI Layer                │
│ Activities, XML layouts, dialogs     │
│ menus, cards, buttons, RecyclerViews │
└───────────────────┬──────────────────┘
                    │
┌───────────────────▼──────────────────┐
│          Business Logic Layer         │
│ Role navigation, validation, search,  │
│ filters, scheduling, statistics       │
└───────────────────┬──────────────────┘
                    │
┌───────────────────▼──────────────────┐
│              Data Layer               │
│ Realm models, queries, transactions,  │
│ demo data initialization              │
└──────────────────────────────────────┘
```

### Component responsibilities

| Layer | Responsibility |
|---|---|
| UI Layer | Displays screens, receives user actions, shows dialogs and lists |
| Business Logic Layer | Controls validation, scheduling rules, filters, role decisions, and statistics |
| Data Layer | Stores and retrieves users, horses, and sessions using Realm |

---

## 🗄 Data Layer and Realm Database

The application uses **Realm Database** for local persistence.

Realm is initialized in:

```text
MyApplication.java
```

`MyApplication` responsibilities:

- Initialize Realm.
- Configure Realm database schema.
- Set database version.
- Seed demo admin account.
- Seed demo rider accounts.
- Seed demo horses.
- Seed demo sessions.
- Prepare the app so it can be tested immediately.

Because Realm is embedded in the app, the project can work offline and does not require a backend server.

---

## 🧬 Database Models

### User Model

Represents an application user.

```text
User
├── id
├── nom
├── prenom
├── age
├── email
├── password
├── telephone
└── role
```

Used for:

- Authentication.
- Admin/rider role detection.
- Rider list.
- Rider detail page.
- Rider assignment to sessions.

---

### Horse Model

Represents a horse inside the club.

```text
Horse
├── id
├── name
├── parent
├── elevage
├── race
├── age
├── coursAujourdhui
├── etat
├── cavalier
└── imageName
```

Used for:

- Horse registry.
- Horse management.
- Horse details.
- Health monitoring.
- Availability validation.
- Scheduling decisions.

---

### Session Model

Represents a riding session.

```text
Session
├── id
├── date
├── heure
├── cavalier
├── cheval
├── status
└── completed
```

Used for:

- Planning display.
- Session creation.
- Session editing.
- Completed session tracking.
- Dashboard statistics.
- Conflict detection.

---

## 🧱 Main Android Components

| File | Responsibility |
|---|---|
| `LoginActivity.java` | Handles login, quick access buttons, account navigation, and role redirection |
| `activity_sign_up.java` | Handles account creation |
| `MyApplication.java` | Initializes Realm and inserts demo data |
| `ActivityAdmin.java` | Displays admin dashboard, statistics, health monitoring, and planning overview |
| `activity_acceuil.java` | Displays rider home space |
| `activity_club_horses.java` | Manages horse list, filters, selection, add, edit, and delete actions |
| `HorseDetailActivity.java` | Displays detailed horse information and scheduling actions |
| `HorseInfoActivity.java` | Displays horse information from rider space |
| `ManageSessionsActivity.java` | Manages session list, search, filters, editing, and scheduling |
| `AddUserActivity.java` | Adds users/riders |
| `RiderListActivity.java` | Displays all riders |
| `RiderDetailActivity.java` | Displays selected rider information |
| `HorseAdapter.java` | RecyclerView adapter for horse cards |
| `RiderAdapter.java` | RecyclerView adapter for rider cards |
| `SessionAdapter.java` | RecyclerView adapter for session cards |
| `SessionDateUtils.java` | Provides date-related utility methods for sessions |
| `DrawableResolver.java` | Resolves horse image resources dynamically |
| `DataManager.java` | Centralizes data-related operations |
| `AdminMenuActivity.java` | Handles admin menu navigation |
| `RiderMenuActivity.java` | Handles rider menu navigation |
| `BaseMenuActivity.java` | Shared menu/navigation behavior |

---

## 🧠 Business Logic

### 📅 Scheduling and Conflict Detection

The scheduling system is one of the most important technical parts of the project.

When a session is created or modified, the app checks:

- Selected date.
- Selected time.
- Selected horse.
- Selected rider.
- Horse health state.
- Horse daily availability.
- Existing sessions for the same horse.
- Existing sessions for the same rider.

Simplified logic:

```text
Create session request
        ↓
Validate date and time
        ↓
Check selected horse
        ↓
Check horse health state
        ↓
Check horse availability
        ↓
Check existing sessions
        ↓
If no conflict → save session
If conflict exists → show warning/error
```

This prevents problems such as:

- Scheduling a sick horse.
- Booking the same horse twice at the same time.
- Booking the same rider twice at the same time.
- Planning a session with an unavailable horse.

---

### 📊 Dashboard Statistics Logic

The admin dashboard reads local Realm data and calculates operational indicators.

The dashboard displays:

- Total sessions of the week.
- Completed sessions.
- Upcoming sessions.
- Horse utilization percentage.
- Most used horse.
- Number of conflicts.
- Sick horse count.
- Stopped horse count.
- Weekly planning performance chart.

This makes the dashboard dynamic instead of static.

---

### 🔎 Search and Filtering Logic

Search and filtering are implemented in multiple modules.

Used in:

- Horse management.
- Session planning.
- Rider space.

Search/filter criteria include:

- Horse name.
- Horse breed/race.
- Rider/cavalier name.
- Date.
- Status.
- Availability.
- Alerts/conflicts.

---

## 🎨 UI and Design System

The app uses Android XML layouts and custom drawable resources.

Design characteristics:

- Purple primary action buttons.
- Dark green admin header cards.
- Soft neutral screen background.
- White cards with rounded corners.
- Status badges for health state.
- Dialog-based editing.
- Mobile-friendly forms.
- Overflow menu navigation.
- RecyclerView cards for lists.
- Chart section for planning performance.

Important layout files:

```text
activity_login.xml
activity_sign_up.xml
activity_admin.xml
activity_acceuil.xml
activity_club_horses.xml
activity_horse_detail.xml
activity_horse_info.xml
activity_manage_sessions.xml
activity_rider_list.xml
activity_rider_detail.xml
activity_add_user.xml
dialog_schedule_session.xml
dialog_session.xml
item_cheval.xml
item_rider.xml
item_session.xml
```

Important drawable resources:

```text
bg_admin_hero.xml
bg_button_primary.xml
bg_button_secondary.xml
bg_screen_soft.xml
bg_status_good.xml
bg_status_sick.xml
bg_status_stopped.xml
bg_surface_card.xml
```

---

## 📸 Screenshots

The screenshots are organized by user role and feature.

---

### 🔐 Authentication Screens

<table>
  <tr>
    <td align="center" width="50%">
      <img src="screenshots/auth/login-screen.png" width="260" alt="Login Screen"><br>
      <b>Login Screen</b><br>
      <sub>Email/password login with quick admin and rider demo access.</sub>
    </td>
    <td align="center" width="50%">
      <img src="screenshots/auth/register-screen.png" width="260" alt="Register Screen"><br>
      <b>Register Screen</b><br>
      <sub>Account creation form for new users.</sub>
    </td>
  </tr>
</table>

---

### 🧑‍💼 Admin Dashboard Screens

<table>
  <tr>
    <td align="center">
      <img src="screenshots/admin/admin-dashboard-overview.png" width="230" alt="Admin Dashboard Overview"><br>
      <b>Dashboard Overview</b><br>
      <sub>Weekly sessions, utilization, conflicts, and planning chart.</sub>
    </td>
    <td align="center">
      <img src="screenshots/admin/admin-dashboard-health-capacity.png" width="230" alt="Admin Health and Capacity"><br>
      <b>Health & Capacity</b><br>
      <sub>Sick horse monitoring and capacity indicators.</sub>
    </td>
    <td align="center">
      <img src="screenshots/admin/admin-overflow-menu.png" width="230" alt="Admin Overflow Menu"><br>
      <b>Admin Menu</b><br>
      <sub>Navigation menu for dashboard, horses, riders, sessions, and logout.</sub>
    </td>
  </tr>
</table>

---

### 🐎 Horse Management Screens

<table>
  <tr>
    <td align="center">
      <img src="screenshots/admin/horse-management-list.png" width="230" alt="Horse Management List"><br>
      <b>Horse Management List</b><br>
      <sub>Admin list with search, filters, status badges, and horse cards.</sub>
    </td>
    <td align="center">
      <img src="screenshots/admin/horse-management-form-empty.png" width="230" alt="Empty Horse Management Form"><br>
      <b>Empty Horse Form</b><br>
      <sub>Form used to add a new horse to the registry.</sub>
    </td>
    <td align="center">
      <img src="screenshots/admin/horse-management-form-filled.png" width="230" alt="Filled Horse Management Form"><br>
      <b>Filled Horse Form</b><br>
      <sub>Selected horse data loaded for modification or deletion.</sub>
    </td>
  </tr>
</table>

<table>
  <tr>
    <td align="center">
      <img src="screenshots/admin/delete-horse-confirmation-dialog.png" width="230" alt="Delete Horse Confirmation"><br>
      <b>Delete Confirmation Dialog</b><br>
      <sub>Safety confirmation before deleting a horse.</sub>
    </td>
  </tr>
</table>

---

### 📅 Session Planning Screens

<table>
  <tr>
    <td align="center">
      <img src="screenshots/admin/session-planning-list.png" width="230" alt="Session Planning List"><br>
      <b>Session Planning List</b><br>
      <sub>List of planned sessions with search and filters.</sub>
    </td>
    <td align="center">
      <img src="screenshots/admin/edit-session-dialog.png" width="230" alt="Edit Session Dialog"><br>
      <b>Edit Session Dialog</b><br>
      <sub>Dialog used to modify session date, time, rider, and horse.</sub>
    </td>
  </tr>
</table>

---

### 🧍 Rider Space Screens

<table>
  <tr>
    <td align="center">
      <img src="screenshots/rider/rider-home-dashboard.png" width="230" alt="Rider Home Dashboard"><br>
      <b>Rider Home Dashboard</b><br>
      <sub>Rider home screen with horse search and daily horses.</sub>
    </td>
    <td align="center">
      <img src="screenshots/rider/rider-overflow-menu.png" width="230" alt="Rider Overflow Menu"><br>
      <b>Rider Menu</b><br>
      <sub>Simple menu for rider navigation and logout.</sub>
    </td>
    <td align="center">
      <img src="screenshots/rider/horse-detail-available-state.png" width="230" alt="Available Horse Detail"><br>
      <b>Available Horse Detail</b><br>
      <sub>Horse details when the horse is available for sessions.</sub>
    </td>
  </tr>
</table>

---

### 🐴 Horse Details and Scheduling Screens

<table>
  <tr>
    <td align="center">
      <img src="screenshots/horse-details/horse-detail-unavailable-state.png" width="230" alt="Unavailable Horse State"><br>
      <b>Unavailable Horse State</b><br>
      <sub>Horse profile showing unavailable or sick status.</sub>
    </td>
    <td align="center">
      <img src="screenshots/horse-details/horse-detail-unavailable-toast.png" width="230" alt="Unavailable Horse Toast"><br>
      <b>Unavailable Toast</b><br>
      <sub>Feedback message when a horse cannot be scheduled.</sub>
    </td>
    <td align="center">
      <img src="screenshots/horse-details/schedule-session-dialog-date.png" width="230" alt="Schedule Session Date Dialog"><br>
      <b>Schedule Session Dialog</b><br>
      <sub>Date/time scheduling dialog for a selected horse.</sub>
    </td>
  </tr>
</table>

<table>
  <tr>
    <td align="center">
      <img src="screenshots/horse-details/schedule-session-dialog-time-keyboard.png" width="230" alt="Schedule Session Time Input"><br>
      <b>Time Input</b><br>
      <sub>Time entry with numeric keyboard.</sub>
    </td>
    <td align="center">
      <img src="screenshots/horse-details/schedule-session-success.png" width="230" alt="Schedule Session Success"><br>
      <b>Session Scheduled Successfully</b><br>
      <sub>Success toast after a valid session is created.</sub>
    </td>
  </tr>
</table>

---

## 🛠 Technologies Used

| Technology | Purpose |
|---|---|
| Java | Main programming language |
| Android Studio | Development environment |
| Android SDK | Native Android framework |
| XML | UI layout design |
| Realm Database | Local mobile database |
| RecyclerView | Dynamic list rendering |
| Material Components | UI components and styling |
| AppCompat | Android compatibility support |
| ConstraintLayout | Responsive screen layout |
| MPAndroidChart | Dashboard chart visualization |
| Gradle | Build automation and dependency management |
| Git | Version control |
| GitHub | Source code hosting and portfolio presentation |

---

## 📦 Dependencies

Dependencies are managed through Gradle.

Main dependency categories:

```text
AndroidX AppCompat
Material Components
AndroidX Activity
ConstraintLayout
MPAndroidChart
Realm Android Plugin
JUnit
Espresso
```

Realm plugin usage:

```gradle
plugins {
    id 'com.android.application'
    id 'realm-android'
}
```

Project-level plugins:

```gradle
classpath 'com.android.tools.build:gradle:8.13.2'
classpath 'io.realm:realm-gradle-plugin:10.18.0'
```

---

## 📁 Project Structure

```text
equi-club-android-management/
│
├── app/
│   ├── build.gradle
│   ├── proguard-rules.pro
│   └── src/
│       ├── main/
│       │   ├── AndroidManifest.xml
│       │   │
│       │   ├── java/com/example/equi/
│       │   │   ├── ActivityAdmin.java
│       │   │   ├── activity_acceuil.java
│       │   │   ├── activity_club_horses.java
│       │   │   ├── activity_sign_up.java
│       │   │   ├── AddUserActivity.java
│       │   │   ├── AdminMenuActivity.java
│       │   │   ├── BaseMenuActivity.java
│       │   │   ├── DataManager.java
│       │   │   ├── DrawableResolver.java
│       │   │   ├── Horse.java
│       │   │   ├── HorseAdapter.java
│       │   │   ├── HorseDetailActivity.java
│       │   │   ├── HorseInfoActivity.java
│       │   │   ├── LoginActivity.java
│       │   │   ├── ManageSessionsActivity.java
│       │   │   ├── MyApplication.java
│       │   │   ├── RiderAdapter.java
│       │   │   ├── RiderDetailActivity.java
│       │   │   ├── RiderListActivity.java
│       │   │   ├── RiderMenuActivity.java
│       │   │   ├── Session.java
│       │   │   ├── SessionAdapter.java
│       │   │   ├── SessionDateUtils.java
│       │   │   └── User.java
│       │   │
│       │   └── res/
│       │       ├── drawable/
│       │       ├── layout/
│       │       ├── menu/
│       │       ├── mipmap/
│       │       ├── values/
│       │       ├── values-night/
│       │       └── xml/
│       │
│       ├── test/
│       └── androidTest/
│
├── screenshots/
│   ├── admin/
│   ├── auth/
│   ├── horse-details/
│   └── rider/
│
├── gradle/
├── build.gradle
├── gradle.properties
├── settings.gradle
├── gradlew
├── gradlew.bat
├── .gitignore
└── README.md
```

---

## ⚙️ Build Configuration

Main build configuration:

```text
Namespace: com.example.equi
Application ID: com.example.equi
Compile SDK: 36
Minimum SDK: 27
Target SDK: 35
Version Code: 1
Version Name: 1.0
Java Compatibility: Java 11
Android Gradle Plugin: 8.13.2
Realm Gradle Plugin: 10.18.0
```

Important build files:

```text
build.gradle
settings.gradle
gradle.properties
app/build.gradle
gradlew
gradlew.bat
```

---

## 🚀 How to Run

### Requirements

Install:

```text
Android Studio
JDK 17 or newer
Android SDK
Android Emulator or physical Android device
Git
```

### Clone the repository

```bash
git clone https://github.com/asfouri/equi-club-android-management.git
```

### Open in Android Studio

1. Open Android Studio.
2. Click **Open**.
3. Select the cloned project folder.
4. Wait for Gradle sync.
5. Choose an emulator or physical Android device.
6. Click **Run**.

### Build from terminal

Windows:

```bash
gradlew.bat assembleDebug
```

macOS/Linux:

```bash
chmod +x gradlew
./gradlew assembleDebug
```

---

## 🔑 Demo Access

The application includes demo data for quick portfolio testing.

### Admin demo account

```text
Email: admin@etrier.ma
Password: admin123
```

### Rider demo access

```text
Password: cavalier123
```

The login screen also includes quick access buttons:

```text
Mode admin
Mode chevalier / rider
```

These buttons are included only to make project testing faster.

---

## 🧪 Testing Strategy

The project includes default Android testing folders:

```text
app/src/test/
app/src/androidTest/
```

Suggested future tests:

- Authentication tests.
- Role redirection tests.
- Horse CRUD tests.
- Rider CRUD tests.
- Session creation tests.
- Session modification tests.
- Conflict detection tests.
- Search/filter tests.
- Dashboard calculation tests.
- Realm database tests.
- UI navigation tests.

Example test scenarios:

| Scenario | Expected Result |
|---|---|
| Admin logs in | Admin dashboard opens |
| Rider logs in | Rider space opens |
| Admin adds a horse | Horse appears in the list |
| Admin edits horse data | Updated data appears in the form/list |
| Admin deletes a horse | Confirmation dialog appears, then horse is removed |
| Sick horse is selected | Scheduling is blocked or warning is shown |
| Valid session is created | Success message appears |
| Same horse is booked twice | Conflict is detected |
| User searches horse by name | Matching horses are displayed |
| User filters weekly sessions | Week sessions are displayed |

---

## 🔐 Security Notes

This project is designed as an academic and portfolio project.

Current limitations:

```text
Demo passwords are stored locally.
Authentication is local only.
No backend server is connected.
Demo quick access buttons are available.
Realm database is local to the device.
```

Recommended production improvements:

| Current Implementation | Production Improvement |
|---|---|
| Local demo authentication | Secure backend authentication |
| Plain demo passwords | Password hashing |
| Local Realm database | Cloud synchronization or API backend |
| Demo quick access buttons | Remove from production build |
| Local-only storage | Add backup/export system |
| Basic validation | Add stronger validation and error handling |
| Simple role control | Add permission management |
| Local migration reset | Add proper Realm migration strategy |

---

## 🚧 Future Improvements

Future versions could include:

- Firebase Authentication.
- Cloud database synchronization.
- Admin web dashboard.
- Calendar-based planning screen.
- Push notifications for upcoming sessions.
- PDF export for weekly planning reports.
- Horse medical history module.
- Rider subscription/payment tracking.
- QR code check-in for riders.
- Dark mode.
- Multilingual support.
- French/English language switch.
- Advanced analytics dashboard.
- Online/offline synchronization.
- Improved UI animations.
- Profile pictures for riders.
- Export/import club data.
- Role permission management.
- Production-ready security layer.

---

## 👨‍💻 Author

<div align="center">

### Ahmad Asfouri

Computer Science Engineering Student  
Interested in mobile development, software engineering, artificial intelligence, and cybersecurity.

<br>

[![GitHub](https://img.shields.io/badge/GitHub-asfouri-181717?style=for-the-badge&logo=github)](https://github.com/asfouri)

</div>

---

<div align="center">

## ⭐ Repository

If you like this project, feel free to star the repository.

<br>

### 🐎 EquiClub Manager

**Smart Android Management for Equestrian Clubs**

</div>
