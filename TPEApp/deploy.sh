rm log.txt > /dev/null 2>&1
touch log.txt
psql -U postgres postgres -f src/test/resources/createDb.sql > log.txt 2>&1
echo "Database created."
psql -U paw -d paw2 -a -f src/test/resources/create.sql > log.txt 2>&1
echo "Tables created."
echo "Inserting rows ..."
psql -U paw -d paw2 -a -f src/test/resources/insert.sql > log.txt 2>&1
echo "Rows inserted."
echo "Building .war ..."
mvn clean package > log.txt 2>&1
echo "Done."
rm $CATALINA_HOME/webapps/GAJAmdb.war > log.txt 2>&1
echo "Removing previous .war in tomcat, if any."
cp target/GAJAmdb.war $CATALINA_HOME/webapps > log.txt 2>&1
echo "Copying .war to tomcat."