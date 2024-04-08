# Reactive Spring Web Flux

If you do not have a PostgreSQL database already set up, you can run the following command to create a container running PostgreSQL:

```bash
docker run -d --name postgres-db -e POSTGRES_PASSWORD=user -p 5432:5432 postgres:latest
```

This command will create a PostgreSQL container named `postgres-db` with the password set to `user`, exposing port `5432` for connections. Make sure Docker is installed on your system before executing this command.
