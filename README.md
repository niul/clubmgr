# clubmgr - Sports Team Website Manager

A comprehensive web platform for managing sports teams, fixtures, standings, squads, and player availability. Built with Spring Boot 3.3.11, PostgreSQL, and Thymeleaf.

## Overview

**clubmgr** is a multi-team sports management system that allows administrators to manage team rosters, track fixtures (matches), maintain league standings, and handle player availability for upcoming games. It features an automated data pipeline that scrapes external league data and keeps team information synchronized.

The platform serves two primary user groups:
- **Public Users**: View team schedules, standings, news, and contact teams
- **Administrators**: Manage teams, squads, fixtures, player availability, and system settings

## Key Features

### Public-Facing Features
- **Team Schedules**: Browse upcoming fixtures and past results for managed teams
- **Standings**: View current league positions and statistics
- **Squad Information**: See active rosters for teams and seasons
- **News Feed**: WordPress-integrated news updates
- **Contact Forms**: Direct messaging to teams
- **Season Details**: Comprehensive team and player statistics per season

### Administrative Features
- **Squad Management**: Add/remove players from team rosters by season
- **Fixture Management**: Record match results and manage upcoming games
- **Player Availability**: Track player availability for upcoming fixtures
- **User Management**: Administrative user account creation and password management
- **Automated Data Import**: Scheduled jobs that scrape league data to update fixtures and standings
- **Multi-Team Support**: Manage multiple teams and seasons simultaneously

## Architecture

### Modules

The project is organized into six Maven modules that work together:

1. **clubmgr-bfc** (Web Application)
   - Spring Boot web application with Thymeleaf templates
   - Spring Security for authentication and authorization
   - MVC controllers for public and admin pages
   - REST endpoints for data retrieval
   - Routes:
     - Public: `/`, `/about.html`, `/news.html`, `/fixture.html`, `/season/{team}/{season}`, etc.
     - Admin: `/admin/*` - squad management, fixture editing, player management, reports

2. **clubmgr-data** (Data Processing Layer)
   - Scrapes external league websites (e.g., MWSL, VMSL)
   - Abstract `DataManager` pattern with pluggable implementations per league
   - Transforms raw web data into database entities
   - Integrates with jsoup for HTML parsing
   - Triggered by scheduled jobs

3. **clubmgr-db** (Data Access & Core Models)
   - JPA/Hibernate entity models: Club, Team, Season, Player, Fixture, Standing, PasswordReset, Role, User, Status, Position
   - Spring Data repositories for all entities
   - Service layer for business logic (PlayerService, TeamService, FixtureService, etc.)
   - Caching layer (EHCache) for performance
   - Input validation on entities

4. **clubmgr-email** (Email Service)
   - Spring Mail integration for sending notifications
   - Email utilities and templates
   - Used for password reset and contact form submissions

5. **clubmgr-jobs** (Scheduled Jobs)
   - Background batch processing jobs scheduled with cron expressions
   - `FixtureStandingsJob`: Runs every 3 hours to scrape and update fixtures and standings
   - `FixtureAvailabilityJob`: Processes player availability for upcoming fixtures
   - Entry point: `FixtureJobApp.java`

6. **clubmgr-util** (Shared Utilities)
   - Spring Security utilities
   - WordPress API client for news feed integration
   - Common utility functions

### Data Flow

```
External League Sites (web scraping)
         ↓
  clubmgr-data (DataManager)
         ↓
  clubmgr-db (Entities & Repositories)
         ↓
  clubmgr-bfc (Controllers) → Public/Admin Views
```

**Scheduled Update Flow:**
1. `FixtureStandingsJob` runs on cron schedule (every 3 hours)
2. Selects teams configured to be updated
3. Creates appropriate `DataManager` for league type (MWSL, VMSL, etc.)
4. `updateFixtures()` and `updateStandings()` scrape external data
5. Results persisted to PostgreSQL via Hibernate
6. Public views fetch cached data from database

## Database Models

### Core Entities
- **Club**: Organization containing multiple teams
- **Team**: Represents a sports team (e.g., Bombastic FC men's team)
- **Season**: League season/year (e.g., "2024-2025 MWSL")
- **TeamSeasonMap**: Junction between Team and Season with scheduling config
- **Player**: Individual player with history across seasons/teams
- **Fixture**: Match between teams on specific date with result
- **PlayerFixtureInfo**: Availability status of player for a specific fixture
- **Standing**: League position data for team in a season
- **Status**: Enumeration for match status (scheduled, completed, cancelled)
- **Position**: Player position field (goalkeeper, defender, etc.)
- **User**: Admin user account with role-based access
- **Role**: Authorization role (ADMIN, USER, etc.)
- **PasswordReset**: Token for password reset flow

## Technology Stack

- **Framework**: Spring Boot 3.3.11 with Spring MVC
- **Security**: Spring Security 6.3.3 with role-based access control
- **ORM**: Hibernate 6.6.1 with Spring Data JPA
- **Database**: PostgreSQL 42.7.2
- **Migrations**: Flyway 9.22.3
- **View Template**: Thymeleaf
- **Build**: Maven 3.8.7
- **Runtime**: Java 21 (with preview features)
- **Caching**: EHCache
- **Logging**: SLF4J with Log4j backend
- **Testing**: JUnit 4.11, Spring Test
- **Web Scraping**: jsoup

## Getting Started

### Prerequisites
- Java 21
- Maven 3.8.7+
- PostgreSQL 10+

### Build & Run

**Build all modules:**
```bash
mvn clean install
```

**Run the web application:**
```bash
mvn spring-boot:run -pl clubmgr-bfc
```

The application will start on `http://localhost:8080`

**Run scheduled jobs:**
```bash
mvn spring-boot:run -pl clubmgr-jobs
```

### Database Setup

1. Create PostgreSQL database for clubmgr
2. Update `clubmgr-bfc/src/main/resources/application.properties` with database credentials
3. Flyway will automatically run migrations on application startup

### Configuration

Key configuration files:
- `clubmgr-bfc/src/main/resources/application.properties` - Spring Boot settings
- `clubmgr-bfc/src/main/resources/applicationContext.xml` - Main Spring XML context (loads additional context files)
- `clubmgr-bfc/src/main/resources/applicationContext-db.xml` - Database/Hibernate config
- `clubmgr-bfc/src/main/resources/applicationContext-security.xml` - Security config with role definitions
- `clubmgr-bfc/src/main/resources/global.properties` - Application settings (team configs, news posts count, etc.)

## Development

### Project Structure
- Public-facing views: `clubmgr-bfc/src/main/resources/templates/`
- Admin views: `clubmgr-bfc/src/main/resources/templates/admin/`
- Static assets: `clubmgr-bfc/src/main/resources/static/`
- Database entities: `clubmgr-db/src/main/java/com/niulbird/clubmgr/db/model/`
- Controllers: `clubmgr-bfc/src/main/java/com/niulbird/clubmgr/bfc/controller/`
- Services: `clubmgr-db/src/main/java/com/niulbird/clubmgr/db/service/`
- Data scrapers: `clubmgr-data/src/main/java/com/niulbird/clubmgr/data/`

### Testing
```bash
# Run all tests
mvn test

# Run single module tests
mvn test -pl clubmgr-db

# Run specific test
mvn test -Dtest=TeamRepositoryTest
```

## Use Cases

### Administrator Workflow
1. Log in to admin portal
2. Navigate to Squad Management
3. Select team and season
4. Add/remove players to team roster
5. Edit upcoming fixture details
6. Set player availability for fixtures
7. View reports and statistics

### Public User Workflow
1. Visit website home page
2. Browse team schedules and standings
3. View detailed fixture information
4. Check player rosters
5. Read news feed
6. Submit contact form to team

### Automated Data Flow
1. Scheduled job runs on cron (every 3 hours)
2. Scrapes external league website for new fixtures
3. Updates match results and league standings
4. Caches data for fast public access
5. Notifies relevant parties via email
