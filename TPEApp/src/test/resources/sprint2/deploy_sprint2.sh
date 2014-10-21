rm log.txt > /dev/null 2>&1
touch log.txt
`# mkdir temp`
`# cd temp`
`# git clone https://bitbucket.org/itba/paw-2014b-02.git`
`# git checkout sprint1`
echo "Migrating database.."
psql -U paw -d paw2 -a -f migrate.sql > log.txt 2>&1
echo "Database migrated."
echo "Building .war ..."
mvn clean package > log.txt 2>&1
echo "Done."
rm $CATALINA_HOME/webapps/GAJAmdb.war > log.txt 2>&1
echo "Removing previous .war in tomcat, if any."
cp target/GAJAmdb.war $CATALINA_HOME/webapps > log.txt 2>&1
echo "Copying .war to tomcat."
`# rm temp`