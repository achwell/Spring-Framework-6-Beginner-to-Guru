CREATE DATABASE restdb;
CREATE USER restadmin WITH ENCRYPTED PASSWORD 'password';
GRANT ALL PRIVILEGES ON DATABASE "restdb" to restadmin;