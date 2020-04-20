
# BDD security testing example for Java

Cucumber/BDD security tests example for Java (Spring Boot API).

This type of work has also been called "security-as-code" as written security requirements drive executable test logic.

There is a fairly comprehensive toy/sample application here to allow for realistic security testing.

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

To compile and then run the test suite...

```
# Windows
.\gradlew.bat build

# Linux
./gradlew build
```

If you encounter issues, see the next section for Vagrant info. That virtual machine will have all dependencies.

This project has been developed for Java 13 and very current Spring Boot dependencies (April 2020).

## Vagrant

An Ubuntu 19.x virtual machine has been configured with Vagrant (2.2.7) for this project.

With Vagrant installed, you should be able to simply run `vagrant up` from the root of this project.

Notable dependencies and components in this image are as follows...

- Java 13 (OpenJDK 13)
- Gradle 6.x
- BDD-Security
    - [@gingeleski fork](https://github.com/gingeleski/bdd-security)

## RoomBook sample API

The toy/sample application "RoomBook" is a room reservation system, like corporate drones will be familiar with.

The API is documented in an OpenAPI version 3.0 spec that resides at `/openapi.json`.

### Role-based access control

Below is an *overall* plan for authorization.

- Office Administrator (`ROLE_ADMIN`)
    - Can add rooms
    - Can modify rooms
    - Can delete rooms
    - Can create appointments for anyone
    - Can modify appointments for anyone
    - Can delete appointments for anyone
- HR Manager (`ROLE_HR_MGR`)
    - Can change someone's access level
    - Can add a user
    - Can delete a user
- Administrative Assistant (`ROLE_ASSISTANT`)
    - Has the capabilities of a 'Regular Employee' but can also...
    - Make reservations for someone else who they're authorized to assist
    - Modify a reservation for that person
    - Cancel a reservation for that person
- Regular Employee (`ROLE_EMPLOYEE`)
    - Can view a list of rooms
    - Can get a room's availability
    - Can make a reservation for self
    - Can modify a reservation for self
    - Can cancel a reservation for self
    
Note that the API in its current state may not support *all* of these actions.

### Test data

"Canned data" comes from `src/main/java/resources/import.sql`, directed to an H2 (in-memory) relational database.

The following test users will be set up...

| Username    | Password   | Authorization    | Activated |
|-------------|------------|------------------|-----------|
| `admin`     | `admin`    | `ROLE_ADMIN`     | True      |
| `employee`  | `password` | `ROLE_EMPLOYEE`  | True      |
| `disabled`  | `password` | `ROLE_EMPLOYEE`  | False     |
| `assistant` | `password` | `ROLE_ASSISTANT` | True      |
| `hrmanager` | `password` | `ROLE_HR_MGR`    | True      |

## API calls

These detailed calls represent the current state of the API.

Pay particular attention to any noted TODOs, and note there may be some lacking functionality versus the access control list.

- `POST /api/authenticate`
    - Authenticates user
        - Sets JWT good for 24 hours
            - Use in `Authorization: Bearer ...` header
    - Only endpoint you can hit unauthenticated
    - Input
        - Data transfer object (DTO) as JSON
            - Make sure your request has `Content-Type: application/json`
        - `username` is a string, required
        - `password` is a string, required
    - Output
        - JSON
            - Where JWT is in field `id_token`
- `GET /api/rooms`
    - Get list of all rooms
    - Must be authenticated as at least `EMPLOYEE`
    - Output
        - JSON
- `GET /api/rooms/{roomName}`
    - Get information on the room with this room name
    - Must be authenticated as at least `EMPLOYEE`
    - Output
        - JSON
- `GET /api/rooms/{roomName}/availability`
    - Returns availability for the room with this room name
    - Must be authenticated as at least `EMPLOYEE`
    - Input
        - Optional query parameters for filtering (TODO)
            - `start` is a Unix time which available time blocks must be equal or later than
            - `end` is a Unix time which available time blocks must be earlier or equal to
    - Output
        - JSON
- `POST /api/admin/clearAllRooms` (TODO)
    - Deletes all rooms in the system
    - Must be authenticated as `ADMIN`
- `POST /api/admin/clearAllReservations` (TODO)
    - Deletes all appointments for every room, from now into the future
    - Must be authenticated as `ADMIN`
