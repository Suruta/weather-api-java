# Moving from weather alert platform to Reddit clone
soon

## Git commands, frequently used

```cmd
git status - check current rep. status
git add <file name> - add the file to the project
git commit -m "<message>" - save changes and give description
git push origin main - push changes to remote repository
```
```cmd
git checkout -b <branch name> - create a new branch
git checkout <branch name> - switch between branches
git branch -d <branch name> - delete branch locally
```
```cmd
git push -u origin <branch name> - push secondary branch to remove rep
```

## Health check

run SpringBoot: `mvn spring-boot:run`
reload Maven after installing dependency: `mvn clean complie`

## Create db before connecting jdbc

```sql
CREATE DATABASE reddit_db;
CREATE USER reddit_admin WITH PASSWORD '12345678';
ALTER DATABASE <db name> OWNER TO reddit_admin;
```
give all permissions before running jdbc
```sql
GRANT ALL ON SCHEMA public TO reddit_admin;
```

## flyway migration

If had errors after running migration, reset the `public` schema
```sql
DROP SCHEMA public CASCADE;
CREATE SCHEMA public;
```

If db user is n't owner of schema:
```sql
GRANT ALL ON SCHEMA public TO public;
```