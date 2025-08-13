## Real Estate Manager Demo
### How to Run
- Clone the repository
	`git clone https://github.com/berkerogluu/realestatemanager.git`
- Connect to Postgresql (Default user is postgres):
	 `psql -U postgres`
- Create a new database:
	 `CREATE DATABASE testdb;`
- Edit the application.properties file with the appropriate database configuration.
- Compile the project:
	` mvn clean compile`
- Run the app:
	`mvn spring-boot:run`
- Navigate to the frontend/config.js and change the BASE_URL constant to where you will run and what port it will be running on. Example:
	 `BASE_URL = http://localhost:8080`
- Navigate to the index.html file, go to the settings page and register your company with the proper API key.
