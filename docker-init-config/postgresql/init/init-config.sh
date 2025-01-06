#!/bin/bash
set -e

# Verifica se o banco já foi inicializado
if [ ! -f "/var/lib/postgresql/data/PG_VERSION" ]; then
  echo "PostgreSQL ainda está sendo inicializado pela primeira vez..."
else
  echo "PostgreSQL já foi inicializado, copiando arquivos de configuração..."
  cp -r /tmp/postgres-config/* /var/lib/postgresql/data/
  
  psql -U "$POSTGRES_USER" -d "$POSTGRES_DB" -f /tmp/postgres-config/init-script.sql

  POSTGRES_USER="root"
  POSTGRES_DB="postgres"
fi
