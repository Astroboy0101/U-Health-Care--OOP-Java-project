# University Health Care - AAU Health Management System

## Introduction
University Health Care (U Health Care) is a comprehensive health management system developed for Addis Ababa University (AAU) to tackle the critical challenges in managing student health records, medical appointments, and healthcare services. Our system is crafted to streamline healthcare delivery, boost patient care coordination, and improve the efficiency of medical staff operations across the university's healthcare facilities.

## Literature Review

### Existing Solutions and Their Limitations

#### Traditional Paper-Based Systems
- **Pros**: Simple to implement, no technical expertise required
- **Cons**: Prone to errors, difficult to search/retrieve records, space-consuming, and at high risk of data loss

#### Commercial Healthcare Management Systems
- **Pros**: Offers comprehensive features and professional support
- **Cons**: Often expensive, complex to customize, and tends to be over-featured for university needs

#### Open-Source Healthcare Systems
- **Pros**: Cost-effective and customizable
- **Cons**: May suffer from security issues, limited support, and integration challenges

### Identified Gaps
- Many students face difficulties obtaining proper medical excuse certificates while some misuse the system by submitting fraudulent ones
- There is a lack of specialized systems dedicated to university healthcare management
- Integration between student records and healthcare services is insufficient
- Real-time appointment scheduling capabilities are limited
- Tracking of medical history and treatments is often inadequate
- There is poor coordination between different departments

## Proposed Solution
U Health Care addresses these gaps by delivering:

### Integrated Student Health Management
- Centralized student health records
- Real-time, reliable appointment scheduling
- Department-specific healthcare tracking

### Multi-User Access Control
- Role-based access for Patients (Students and Staff), Doctors, and Administrators
- Secure authentication and authorization
- A detailed audit trail for all medical activities

### Comprehensive Medical Records
- Digital storage of patient medical history
- Effective treatment tracking and management
- Streamlined prescription management

## Methodology

### Technology Stack
- **Frontend**: Java Swing built with Java OOP principles for a robust and maintainable desktop application
- **Backend**: SQLite database ensures robust data persistence
- **Architecture**: The application follows the MVC (Model-View-Controller) pattern for a structured and scalable codebase

### System Components

#### User Management
- Handles student registration and profile management
- Manages medical staff information
- Supports department administration

#### Appointment System
- Allows for real-time scheduling of appointments
- Sends automated notifications and handles conflict resolution

#### Medical Records
- Enables digital storage and retrieval of health records
- Tracks treatment history
- Manages prescriptions effectively

## Results

### Implementation Details

#### Login System
- Provides secure authentication
- Implements role-based access control
- Manages user sessions efficiently

#### Main Dashboard
- Offers quick access to key features
- Displays real-time notifications
- Provides an overview of system status

#### Medical Records Management
- Tracks patient history accurately
- Documents treatments methodically
- Manages prescription details securely

#### Appointment Management
- Integrates a calendar view
- Supports automated scheduling
- Provides a reminder system for appointments

## Conclusion
U Health Care successfully addresses the major gaps in university healthcare management by offering:
- Streamlined healthcare service delivery
- Improved patient care coordination
- Enhanced management of medical records
- Efficient and reliable appointment scheduling
- Secure and robust data management

The system has notably contributed to improved healthcare efficiency, higher patient satisfaction, increased productivity of medical staff, and enhanced data accuracy and accessibility.

## Future Framework

### Planned Improvements

#### Mobile Application
- Launch a patient mobile app for booking appointments
- Provide real-time notifications and integrate telemedicine functionalities

#### Advanced Analytics
- Implement health trend analysis
- Introduce predictive healthcare insights
- Collect and analyze performance metrics

#### Integration Capabilities
- Integrate with University ERP systems
- Connect with external healthcare providers
- Include insurance system integrations

#### Enhanced Security
- Introduce two-factor authentication
- Adopt advanced encryption methods
- Perform regular security audits

## UML Diagrams

### System Architecture
```
[User Interface Layer]
        ↓
[Business Logic Layer]
        ↓
[Data Access Layer]
        ↓
[Database Layer]
```

### User Authentication Flow
```
[Login Screen] → [Authentication] → [Role Check] → [Dashboard]
```

### Appointment Management
```
[Schedule Request] → [Availability Check] → [Confirmation] → [Notification]
```

## Getting Started
1. Ensure that Java JDK is installed
2. Clone the repository
3. Run `run.bat` to start the application
4. Login with the following default credentials:
   - Admin: username: admin, password: admin123
   - Doctor: username: doctor, password: doctor123
   - Patient: username: patient, password: patient123

## Contributors / Project Members
- Kena Fayera
- Sewyishal Netsanet
- Yisakor Tamirat
- Leulekal Nahusenay
- Semere Hailu

## License
This project is licensed under the AAiT.

By 2nd year Biomedical Engineering, Addis Ababa University, Ethiopia.

## Questions or Feedback?
For any questions or further information, please contact:
- Email: fayerakena@gmail.com
- Telegram: https://t.me/Astroboy0101 
