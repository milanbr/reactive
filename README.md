### Starting application

`spring-boot:run`

#### Async
`curl -N -H "Accept: application/stream+json" http://localhost:8080/api/users`

#### Server sent events
`curl -N http://localhost:8080/api/users/stream`

`curl -N http://localhost:8080/api/users/sse`

### Insomnia project

File: Reactive_*.json

### Frontend

Frontend is in repository:

https://github.com/milanbr/reactiveFE