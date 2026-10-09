# Moving from weather alert platform to Reddit clone
soon

## Git commands, frequently used

```
git status - check current rep. status
git add <file name> - add the file to the project
git commit -m "<message>" - save changes and give description
git push origin main - push changes to remote repository
```
```
git checkout -b <branch name> - create a new branch
git checkout <branch name> - switch between branches
git branch -d <branch name> - delete branch locally
```
```
git push -u origin <branch name> - push secondary branch to remove rep
```

## Health check

Built with `mvn spring-boot:run`
Check with `./mvnw test`

After adding dependencies run:
```
./mvnw clean install
```
or just run tests:
```
./mvnw test
```

### Create db before connecting jdbc

```
CREATE DATABASE reddit_db;
CREATE USER reddit_admin WITH PASSWORD '12345678';
ALTER DATABASE admin OWNER TO reddit_admin;
```
give all permissions before running jdbc
```
GRANT ALL ON SCHEMA public TO reddit_admin;
```