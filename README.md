# internship-management-system

This project contains a Java application that performs internship management functions.

## Prerequisites

Make sure you have the following software installed on your computer before proceeding:

- Java Development Kit (JDK)
- PostgreSQL database server
- Eclipse IDE

## Steps

### Importing the Project

1. Open Eclipse IDE.
2. Select the **File** menu and choose the **Import** option.
3. In the opened window, select **Existing Maven Projects** and click **Next**.
4. Specify the directory where the project is located (Root Directory) and click **Finish**.
5. Eclipse will import the Maven project and add it to the project explorer.

### Lombok Installation

1. Ensure that Lombok is installed in your IDE.
2. If Lombok is not installed, follow these steps:
   - Close your IDE.
   - Add the Lombok library to your IDE.
   - Start your IDE.

### Loading Dependencies and Building the Project

After importing the project, Eclipse will automatically load the dependencies using Maven. This process may take some time and runs in the background. To compile your Maven project, follow these steps:

1. Right-click on the project and select **Run As** from the context menu, then click on **Maven Install**.
2. When the Maven project build process is complete, you will see a successful message in the console.

### PostgreSQL Integration

To use the PostgreSQL database server, follow these steps:

1. Open the `application.properties` file located under `src/main/resources`.
2. Update your database connection information according to your PostgreSQL server (e.g., URL, username, password, etc.).
3. Save the changes.

### Importing Dummy Data into PostgreSQL

To import dummy data into the PostgreSQL database, follow these steps:

1. Open a SQL client tool.
2. Connect to your PostgreSQL database server.
3. Open the **Internship Management System** database.
4. Open the `dummy_data.sql` file located in the project's root directory.
5. Execute the SQL statements in the `dummy_data.sql` file to populate the database with dummy data.

### Running the Application

1. There should be a Java class representing the entry point of the application under `src/main/java/tr.edu.ogu.ceng.InternshipManagementSystemApplication` class.
2. To run the application, follow these steps:
   - Locate the entry point class and right-click on it.
   - From the context menu, select **Run As**, and then click on **Java Application**.
3. When the application runs successfully, you will see the relevant output in the console.

### Accessing the Application

When the application is running, you can access the expected screens by navigating to `localhost:8080` in your web browser.

> Note: Make sure the application is running on the specified port, which is `8080` by default.
