# internship-management-system

This project contains a Java application that performs internship management functions.

## Prerequisites

Make sure you have the following software installed on your computer before proceeding:

- Java Development Kit (JDK) version 17
- PostgreSQL database server version 16
- pgAdmin4 v7 as SQL client tool
- Eclipse IDE 2023-09 (4.29)

## Steps

### Importing the Project

1. Open Eclipse IDE.
2. Select the **File** menu and choose the **Import** option.
3. In the opened window, select **Existing Maven Projects** and click **Next**.
4. Specify the directory where the project is located (Root Directory) and click **Finish**.
5. Eclipse will import the Maven project and add it to the project explorer and package explorer.

### Lombok Installation (version 1.18.30)

Ensure that Lombok is installed in your IDE. If not, follow these steps:
1. Close your IDE.
2. Download the specified version of Lombok.
3. Run the Lombok JAR file to open the installation wizard.
4. Select your Eclipse IDE installation directory and click the "Install/Update" button.
5. Complete the installation of Lombok, Lombok library is added to your Eclipse IDE.
7. Start your IDE.

### Loading Dependencies and Building the Project

After importing the project, Eclipse will automatically load the dependencies using Maven. This process may take some time and runs in the background. To compile your Maven project, follow these steps:

1. Select "Debug Configurations" from the Run menu.
2. Click on "Arguments" in the opened window.
3. Clear everything in VM arguments and paste the following:
    ```
    -Dspring.profiles.active=dev
    ```
4. Right-click on the project and select "Run As" from the context menu, then click on "Maven Install".
5. Once the Maven project build process is complete, you will see a successful message in the console.

### PostgreSQL Integration

To use the PostgreSQL database server, follow these steps:

1. Open the `application-dev.properties` file located under `src/main/resources`.
2. Update your database connection information according to your PostgreSQL server (e.g., URL, username, password, etc.).
3. Save the changes.
4. Return to the Git terminal and enter the following command:
    ```
    $ git update-index --assume-unchanged src/main/resources/application-dev.properties
    ```

### Importing Dummy Data into PostgreSQL

To import dummy data into the PostgreSQL database, follow these steps:

1. Open a SQL client tool (pgAdmin4 v7).
2. Connect to your PostgreSQL database server.
3. Create the **Internship Management System** database:
    - On pgAdmin v7, expand the Servers tab located on the left in the window named Object Explorer.
    - Right-click on **Databases**.
    - Create a database, name it **"internship-management-system"**.

4. Open the `db-scripts` folder located in the project's root directory.
5. Execute the SQL statements in the folder to create tables and their connections.
6. Open the `datagenerator` folder in Visual Studio Code or an IDE where you can run Python files.
7. Enter your information in the `main.py` file and run the file. 

### Running the Application

1. There should be a Java class representing the entry point of the application under `src/main/java/tr.edu.ogu.ceng.InternshipManagementSystemApplication` class.
2. To run the application, follow these steps:
   - Locate the entry point class and right-click on it.
   - From the context menu, select **Run As**, and then click on **Java Application**.
3. When the application runs successfully, you will see the relevant output in the console.

### Accessing the Application

When the application is running, you can access the expected screens by navigating to `localhost:8080` in your web browser.

> Note: Make sure the application is running on the specified port, which is `8080` by default.


