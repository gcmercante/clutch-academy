
-- Enable the dblink extension
CREATE EXTENSION IF NOT EXISTS dblink;

DO
$$
BEGIN
    IF NOT EXISTS (
        SELECT FROM pg_catalog.pg_database
        WHERE datname = 'user_service'
    ) THEN
        PERFORM dblink_connect('dbname=postgres');
        PERFORM dblink_exec('CREATE DATABASE user_service');
        PERFORM dblink_disconnect();
    END IF;
END
$$;

DO
$$
BEGIN
    IF NOT EXISTS (
        SELECT FROM pg_catalog.pg_database
        WHERE datname = 'keycloak'
    ) THEN
        PERFORM dblink_connect('dbname=postgres');
        PERFORM dblink_exec('CREATE DATABASE keycloak');
        PERFORM dblink_disconnect();
    END IF;
END
$$;

DO
$$
BEGIN
    IF NOT EXISTS (
        SELECT FROM pg_catalog.pg_roles
        WHERE rolname = 'user_service'
    ) THEN
        CREATE ROLE user_service WITH LOGIN PASSWORD 'q1w2e3';
    END IF;
END
$$;

DO
$$
BEGIN
    IF NOT EXISTS (
        SELECT FROM pg_catalog.pg_roles
        WHERE rolname = 'keycloak'
    ) THEN
        CREATE ROLE keycloak WITH LOGIN PASSWORD 'q1w2e3';
    END IF;
END
$$;

-- Conceder privilégios após a criação do banco de dados e do usuário
GRANT ALL PRIVILEGES ON DATABASE user_service TO user_service;

-- Conectar ao banco de dados user_service e conceder privilégios no esquema public
\c user_service

GRANT ALL PRIVILEGES ON SCHEMA public TO user_service;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO user_service;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO user_service;

-- Desconectar do banco de dados user_service
\c postgres

GRANT ALL PRIVILEGES ON DATABASE keycloak TO keycloak;

-- Conectar ao banco de dados keycloak e conceder privilégios no esquema public
\c keycloak

GRANT ALL PRIVILEGES ON SCHEMA public TO keycloak;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO keycloak;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO keycloak;