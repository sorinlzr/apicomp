# APIComp

## Project Setup

### Prerequisites
- Java 17 or higher
- Gradle 7.0 or higher
- H2 Database

### Installation

1. Clone the repository:
    ```sh
    git clone https://github.com/your-repo/apicomp.git
    cd apicomp
    ```

2. Install dependencies:
    ```sh
    ./gradlew clean build
    ```

### Build

To build the project, run:
```sh
./gradlew build
```

### Start the Application

To start the application, run:
```sh
./gradlew bootRun
```

On startup the H2 database should be initialized with some sample data usint Liquibase.

## APIs

All three APIs implement the same functionalities to manage **students**. 
The REST API is implemented using Spring Boot, the GraphQL API is implemented using Spring Boot and GraphQL Java, and the gRPC API is implemented using Spring Boot and gRPC.

### REST API

The REST API is available at `http://localhost:8080/students`.

#### Endpoints

- **GET /students**: Retrieve all students.
- **GET /students/{id}**: Retrieve a student by ID.
- **POST /students**: Create a new student.
- **PUT /students/{id}**: Update an existing student.
- **DELETE /students/{id}**: Delete a student by ID.

#### Example Request

```sh
curl -X GET http://localhost:8080/students
```

### GraphQL API

The GraphQL API is available at `http://localhost:8080/graphql`.

#### Schema

The `schema.graphqls` file defines the GraphQL schema:

```graphql
type Student {
    id: ID!
    name: String!
    email: String!
    enrollments: [Enrollment]
}

type Enrollment {
    id: ID!
    courseDescription: String!
    studentId: ID!
    studentName: String!
    studentEmail: String!
    enrollmentDate: String!
}

type Query {
    getAllStudents: [Student]
    getStudentById(id: ID!): Student
}

type Mutation {
    createStudent(name: String!, email: String!): Student
    updateStudent(id: ID!, name: String!, email: String!): Student
    deleteStudent(id: ID!): Boolean
}
```

#### Example Request

```graphql
query {
   students {
        id
        name
        email
    }
}
```

### gRPC API

The gRPC API is available at `localhost:9090`.

#### Proto Files

The `student_service.proto` file defines the gRPC service:

```proto
syntax = "proto3";

package net.pfsnc.apicomp.fh.grpc;

service StudentService {
    rpc GetStudent (StudentRequest) returns (StudentResponse);
    rpc CreateStudent (CreateStudentRequest) returns (StudentResponse);
    rpc DeleteStudent (StudentRequest) returns (DeleteStudentResponse);
    rpc GetAllStudents (Empty) returns (GetAllStudentsResponse);
    rpc SubscribeStudentCount (Empty) returns (stream StudentCountResponse);
}

message StudentRequest {
    int64 id = 1;
}

message CreateStudentRequest {
    string name = 1;
    string email = 2;
}

message StudentResponse {
    int64 id = 1;
    string name = 2;
    string email = 3;
    repeated Enrollment enrollments = 4;
}

message DeleteStudentResponse {
    bool success = 1;
    string message = 2;
}

message GetAllStudentsResponse {
    repeated StudentResponse students = 1;
}

message StudentCountResponse {
    int32 count = 1;
}

message Enrollment {
    int64 id = 1;
    string courseDescription = 2;
    int64 studentId = 3;
    string studentName = 4;
    string studentEmail = 5;
    string enrollmentDate = 6;
}

message Empty {}
```

#### Example Request

Use a gRPC client to call the `GetStudent` method:

```sh
grpcurl -plaintext -d "{\"id\":1}" -import-path src\main\proto -proto student_service.proto localhost:9090 StudentService/GetStudent
```

To install `grpcurl`, visit the following link: https://github.com/fullstorydev/grpcurl