
FROM tomcat:9
COPY target/ORSProject-03.war /usr/local/tomcat/webapps/ORSProject-03.war
COPY Report/Meh.jrxml /opt/reports/Meh.jrxml
EXPOSE 8080
CMD ["catalina.sh", "run"]