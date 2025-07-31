FROM icr.io/appcafe/open-liberty:full-java21-openj9-ubi-minimal

# Argumento con valor por defecto para el driver JDBC
ARG ENV_DRIVER_JDBC_DIR=./target/lib/*.jar

# Copiar driver JDBC al contenedor con los permisos adecuados
COPY --chown=1001:0 ${ENV_DRIVER_JDBC_DIR} /config/

# Copiar configuración del servidor Open Liberty
COPY src/main/liberty/config/server.xml /config/server.xml

# Copiar archivo WAR generado por Maven (name basado en finalName del pom.xml)
COPY target/kawsayfit-web.war /config/apps/
