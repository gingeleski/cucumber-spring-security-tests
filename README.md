
# Sample Spring Boot API

A sample Spring Boot API with Gradle bindings.

## Quickstart

```
git clone 
cd sample-spring-boot-api

# Windows
.\gradle.bat bootRun

# Linux
./gradle bootRun
```

The API is now accessible with a root address `http://localhost:8080/`

## Concept

The sample API is a room reservation system for an office.

## Authorization levels

Office Administrator (`ADMIN`)
- Can add rooms
- Can modify rooms
- Can delete rooms
- Can create appointments for anyone
- Can modify appointments for anyone
- Can delete appointments for anyone

HR Manager (`HR_MGR`)
- Can change someone's access level
- Can add a user
- Can delete a user

Administrative Assistant (`ASSISTANT`)
- Has the capabilities of a 'Regular Employee' but can also...
- Make reservations for someone else who they're authorized to assist
- Modify a reservation for that person
- Cancel a reservation for that person

Regular Employee (`EMPLOYEE`)
- Can view a list of rooms
- Can get a room's availability
- Can make a reservation for themself
- Can modify a reservation for themself
- Can cancel a reservation for themself

## Data load

Loads canned data in the following order...

1. `src/main/java/resources/rooms.json`
2. `src/main/java/resources/users.json`
3. `src/main/java/resources/appointments.json`

## API calls

`POST /login` (TODO)
- Authenticates a user, initiating a session
- Input
    - Form-encoded data
    - `Username` is a string, required
    - `Password` is a string, required
- Output
    - Sets `JSESSIONID` cookie

`GET /logout` (TODO)
- Terminates the current user session
- Output
    - Clears `JSESSIONID` cookie
- Must be authenticated as at least `EMPLOYEE`

`GET /rooms` (TODO)
- Get list of all rooms
- Must be authenticated as at least `EMPLOYEE`

`GET /rooms/<id>` (TODO)
- Get information on the room with this ID
- Must be authenticated as at least `EMPLOYEE`

`GET /rooms/<id>/availability` (TODO)
- Input
    - Need at least one for search
    - `start` is a Unix time which available time blocks must be equal or later than
    - `end` is a Unix time which available time blocks must be earlier or equal to
- Output
    - JSON
- Example output
    - `[{start: 1516626000, end:1516627800, available:false}]`
- Must be authenticated as at least `EMPLOYEE`
