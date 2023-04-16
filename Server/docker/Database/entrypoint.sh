#!/bin/bash

/opt/mssql/bin/sqlservr > >(
  while read line; do
  echo $line
  if [[ "$line" =~ "Recovery is complete" ]]; then
    echo "server runnign"
    /opt/mssql-tools/bin/sqlcmd -S localhost -U sa -P ${MSSQL_SA_PASSWORD} -d master -i database.sql
    break
  fi
done
)