# 🗓️ EVYNT - Event Planner App

## 📌 The Team
- **Calvin Robinson**
- **Ethan Hillman**
- **Daniel Boyce**
- **Joy Brown**
- **Talia Crockett**

*Team 5 - Code Differently CS-25-2*

---

## 🖼️ Screenshot
<img src="./event-planner/Read_Me/Website-Image.png" alt="photo of our website" />

---

## 📝 Description
**EVYNT** is an all-in-one event management platform designed to solve common challenges in event planning and coordination.

### The Problem We Solve
Our app addresses four key challenges in event management:
1. **Community Events** - Difficulty discovering and accessing local community events
2. **Creation Barriers** - Complex processes that make event creation challenging
3. **Private Events** - Lack of streamlined tools for managing private gatherings

### Our Solution
EVYNT provides a comprehensive platform with:
- 🌐 **All-in-One Event Hub** - Centralized platform for all event needs
- ✨ **Simplified Event Creation** - Intuitive tool for creating events quickly
- 📨 **Private Invitations** - Secure system for managing private event invites
- ✅ **RSVP Management** - Streamlined tracking and management of attendee responses

**Key Features:**
- Browse public community events and private invitations
- RSVP directly through your dashboard
- Create events with ease
- Real-time updates on event details
- Track RSVPs
- Responsive design for mobile and desktop


## 🚀 Demo Link
**Live Demo:** [https://cs-25-2-team5.vercel.app/](https://cs-25-2-team5.vercel.app/)

---

## 🛠️ Installation Instructions

### Prerequisites
- [Node.js](https://nodejs.org/) (v16+ recommended)  
- npm or yarn
- java 17
- PostgresSQL - start postgresql server locallty at `jdbc:postgresql://localhost:5432/postgres`
    - with username postgres and password postgres
- [LocationIQ API Key ](https://docs.locationiq.com/docs/search-forward-geocoding?_gl=1*xiem2h*_ga*OTk2NjI1MDEuMTc2MTQ5ODY0NQ..*_ga_TRV5GF9KFC*czE3NjE1Mjk5MTUkbzQkZzEkdDE3NjE1Mjk5MTckajU4JGwwJGgw)

### Steps
```bash
#Frontend
# 1. Clone the repository
git clone https://github.com/code-differently/cs-25-2-team5.git

# 2. Navigate into the project directory
cd event-planner/frontend

# 3. Install dependencies
npm install

# 4. Start the development server for front end
npm run dev
# 5
#Backend
cd event-planner/api/demo
./gradlew bootRun
```
---

## ⚠️ Known Issues
- Error handling refreshing screens
    - if you are on a screen that requires log in and you refresh it will prompt you to move to the log in page even though your still logged in

### Technical Challenges We've Addressed
- **Database Connection** - Ongoing optimization for reliability
- **Complex Entity Relationships** - Managing multiple interconnected data models
- **Security and Authentication** - Implementing robust user authentication
- **Unit Testing** - Comprehensive test coverage in development
- **Software Design** - Learning and applying best practices
- **External API Integration** - Working with third-party services

## 🗺️ Roadmap Features

### 🔜 In The Future
- [ ] **Calendar of Events** - Integrated calendar view for better event visualization
- [ ] **Notification System** - Real-time alerts for event updates and reminders
- [ ] **Different Themes** - Customizable UI themes for personalization
- [ ] **AI Integration** - Smart event recommendations and automated scheduling

## 🙏 Credits

### Team
Built with ❤️ by **Team 5** at [Code Differently](https://codedifferently.com/)
- Calvin Robinson
- Ethan Hillman
- Daniel Boyce
- Joy Brown
- Talia Crockett

### Technologies Used
- **Frontend:** React, Vite
- **Backend:** Java, Springboot
- **Database:** PostgreSQL
- **Deployment:** Vercel
- **Backend Deployment:** Render
- **Authentication:** Clerk Authentication

---

