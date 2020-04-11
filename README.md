
# BDD security testing example for Java

Cucumber/BDD security tests example for Java (Spring Boot API).

This type of work has also been called "security-as-code".

## Quickstart

Grab the code.

```
git clone https://github.com/gingeleski/cucumber-spring-security-tests.git
cd cucumber-spring-security-tests
```

To launch the API with a root address `http://localhost:8080/` ...

```
# Windows
.\gradlew.bat bootRun

# Linux
./gradlew bootRun
```

To run the test suite...

```
# Windows
.\gradlew.bat test

# Linux
./gradlew test
```

## API concept

The sample API is a room reservation system for an office.

## Authorization levels

- Office Administrator (`ADMIN`)
    - Can add rooms
    - Can modify rooms
    - Can delete rooms
    - Can create appointments for anyone
    - Can modify appointments for anyone
    - Can delete appointments for anyone
- HR Manager (`HR_MGR`)
    - Can change someone's access level
    - Can add a user
    - Can delete a user
- Administrative Assistant (`ASSISTANT`)
    - Has the capabilities of a 'Regular Employee' but can also...
    - Make reservations for someone else who they're authorized to assist
    - Modify a reservation for that person
    - Cancel a reservation for that person
- Regular Employee (`EMPLOYEE`)
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

- `POST /login`
    - Authenticates user, setting `Authorization Bearer` header good for 10 days
    - Only endpoint you can hit unauthenticated
    - Input
        - Form-encoded data, make sure your request has `Content-Type: application/x-www-form-urlencoded`
        - `username` is a string, required
        - `password` is a string, required
    - Output
        - Sets header `Authorization: Bearer <TOKEN>`
- `GET /logout` (TODO)
    - Does nothing right now
    - Must be authenticated as at least `EMPLOYEE`
- `GET /rooms`
    - Get list of all rooms
    - Must be authenticated as at least `EMPLOYEE`
- `GET /rooms/<room_name>`
    - Get information on the room with this room name
    - Must be authenticated as at least `EMPLOYEE`
- `GET /rooms/<id>/availability` (TODO)
    - Input
        - Need at least one for search
        - `start` is a Unix time which available time blocks must be equal or later than
        - `end` is a Unix time which available time blocks must be earlier or equal to
    - Output
        - JSON
    - Example output
        - `[{start: 1516626000, end:1516627800, available:false}]`
    - Must be authenticated as at least `EMPLOYEE`
