FROM mcr.microsoft.com/mssql/server:2022-latest


ENV ACCEPT_EULA=Y
ENV MSSQL_TCP_PORT=1433
ENV MSSQL_SA_PASSWORD=Password1
ENV MSSQL_DATA_DIR=/var/opt/mssql

WORKDIR /src

COPY Docker/db .

HEALTHCHECK CMD /opt/mssql-tools/bin/sqlcmd -U sa -P ${MSSQL_SA_PASSWORD} -Q "SELECT 1" || exit 1

EXPOSE 1433

CMD /bin/bash ./entrypoint.sh