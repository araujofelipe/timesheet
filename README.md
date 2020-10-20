timesheet api.

Clone this project and in terminal:

```./mvnw spring-bott:run```

On startup, a h2 database is created and seeded with some data.

Opened resources:
[GET]
/api/projects - List all projects.

To use the following resource, use a basic authentication
- user:velhocoelho password:secret (Basic dmVsaG9jb2VsaG86c2VjcmV0)
- user:opatolino password:secret (Basic b3BhdG9saW5vOnNlY3JldA==)

[GET]
/api/records - List all records for user in basic authentication

[POST]
/api/records/stamp - Stamp a record to user in basic authentication
Request body example:

```
{
    "date" : "2020-10-19",
    "start" : "13:30",
    "end" : "17:00",
    "project": {"id": 1}//optional
}
```
 
/api/projects/{projectId}/allocate
Request body example:
```
[//records ids array
   {"id": 1}
]
```
