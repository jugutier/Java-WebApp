mvn clean package
rm $CATALINA_HOME/webapps/*.war
cp target/*.war $CATALINA_HOME/webapps
