# Comic Book Library (CBL)

A Java-based comic book management system for searching, filtering, and managing comic collections.

## Problem

Comic book collectors need an easy way to:
- Search their collection by character, creator, or title
- Find rare and valuable comics
- Add new comics to their library

## Solution

Our CLI application provides:

1. **Search & Filter** - Find comics by character, creator, title, genre, or publisher
2. **Rare Comics Discovery** - Sort by rarity based on print count, grade, and age
3. **Collection Management** - Add new comics with validation and duplicate prevention

## How to Run

**Prerequisites:** Java 21, Gradle

```bash
# Build and test
./gradlew build test

# Run the CLI application
java -cp build/classes/java/main com.team5.cbl.cbl_app.ComicLibraryManager
```

## Future Improvements

- **Web Application** - Migrate from CLI to modern web interface
- **User Accounts** - Create personal accounts with login/registration
- **Bookmarks & Favorites** - Save and organize your most liked comics
- **Collection Statistics** - Track how many comics collected, genres, publishers, etc.
- **Edit Comics** - Modify or update comics you've added to your collection
- **Database Integration** - Add persistent storage with JPA/PostgreSQL

---

**Team 5** - Code Society 25.2 Cohort

