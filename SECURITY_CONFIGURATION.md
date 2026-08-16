# Security configuration

Database settings are read from the DB_URL, DB_USERNAME, and DB_PASSWORD environment variables. Do not commit real credentials or IDE run configurations containing them.

Database integration tests are disabled by default because they require a configured Oracle instance. Run them explicitly with mvn -B clean test -Pdb-integration.

This project targets Java 17, Spring Framework 6.2, Jakarta Servlet 6, and Tomcat 10.1 or later. Oracle access uses ojdbc11 directly; the obsolete log4jdbc proxy has been removed.

No real Oracle integration test was performed during this audit. No repository-level license was selected because ownership and upstream teaching-material terms require external confirmation.

FindSecBugs treats the Hikari property key named password as a hard-coded password even though its value comes from DB_PASSWORD. The scoped spotbugs-exclude.xml filter documents and excludes only that finding in RootConfig.dataSource.
