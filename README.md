# Velocity Vehicles Database Setup

This guide provides instructions on how to set up the `velocity_vehicles` MySQL database using the provided `MakeDB.ipynb` Jupyter Notebook. This script will create the necessary tables and populate them with initial data from Excel files. It also handles password hashing for customer and dealer accounts.

## Prerequisites

Before running the script, ensure you have the following installed:

* **Python 3.x**

* **MySQL Server**: A running MySQL instance.

* **Jupyter Notebook** or **JupyterLab**: To open and run the `.ipynb` file.

* **Required Python Libraries**:

  * `mysql-connector-python`

  * `pandas`

  * `openpyxl`

  * `bcrypt`

  * `configparser`

You can install the Python libraries using pip: 
```pip install mysql-connector-python pandas openpyxl bcrypt configparser```
## Project Structure

Ensure your project directory is structured as follows:

```
├── MakeDB.ipynb
├── db.properties
├── data/
│   ├── car_brands.xlsx
│   ├── dealers.xlsx
│   ├── customers.xlsx
│   ├── cars.xlsx
│   └── sales.xlsx
└── cloudinary_data/
├── dealer_images.xlsx
└── car_images.xlsx
└── passwords/ (This directory will be created by the script)
```
**Important:** The `data/` and `cloudinary_data/` directories must exist and contain the specified Excel files for the script to run successfully.

## Configuration

You need to create a `db.properties` file in the root directory of your project with your MySQL database connection details.

**`db.properties` example:**

```
[mysql]
host = localhost
user = your_mysql_username
password = your_mysql_password
```
**Replace `localhost`, `your_mysql_username`, and `your_mysql_password` with your actual MySQL server details.**

## Running the Script

Follow these steps to set up your database:

1.  **Start MySQL Server**: Ensure your MySQL server is running.

2.  **Open Jupyter Notebook/Lab**:

    ```
    jupyter notebook
    # or
    jupyter lab
    ```

3.  **Navigate and Open `MakeDB.ipynb`**: In the Jupyter interface, navigate to the directory where you saved `MakeDB.ipynb` and open it.

4.  **Run All Cells**:

    * Go to `Cell` -> `Run All` in the Jupyter menu.

    * Alternatively, you can run each cell individually by selecting it and pressing `Shift + Enter`.

## What the Script Does

The `MakeDB.ipynb` script performs the following actions:

1.  **Connects to MySQL**: Establishes a connection to your MySQL server using the credentials from `db.properties`.

2.  **Database Creation**:

    * Drops the `Velocity_vehicles` database if it already exists (to ensure a clean slate).

    * Creates a new database named `Velocity_vehicles`.

3.  **Table Creation**: Creates the following tables within the `Velocity_vehicles` database:

    * `car_brands`

    * `dealers`

    * `customers`

    * `cars`

    * `sales`
      It defines appropriate columns, primary keys, foreign keys, and constraints for each table.

4.  **Data Population**:

    * Reads data from the `.xlsx` files located in the `data/` directory (`car_brands.xlsx`, `dealers.xlsx`, `customers.xlsx`, `cars.xlsx`, `sales.xlsx`).

    * Inserts the data into their respective tables.

    * Includes error handling for `FileNotFoundError` if the Excel files are missing.

5.  **Update Car Status**:

    * Adds a `status` column to the `cars` table (defaulting to 'AVAILABLE').

    * Updates the `status` to 'SOLD' for cars that appear in the `sales` table.

6.  **Data Normalization and Image URLs**:

    * Converts all car `colour` values to uppercase.

    * Adds `car_image` and `profile_image` columns to the `cars` and `dealers` tables, respectively.

    * Updates these image columns with URLs from `cloudinary_data/dealer_images.xlsx` and `cloudinary_data/car_images.xlsx`.

7.  **Password Hashing**:

    * Retrieves existing passwords for `customers` and `dealers` from the database.

    * Hashes these passwords using `bcrypt` for enhanced security.

    * **Exclusions**: Note that the script explicitly excludes `C_ADITYACHE001` (customer) and `D_ANIKETFOR001` (dealer) from password hashing. Their passwords will remain as they are in the Excel file.

    * Updates the `password` column in the `customers` and `dealers` tables with the new hashed passwords.

    * Generates Excel files (`./passwords/customers_password.xlsx` and `./passwords/dealers_password.xlsx`) containing the original and newly hashed passwords for auditing purposes. The `passwords/` directory will be created if it doesn't exist.

## Troubleshooting

* **`mysql.connector.errors.ProgrammingError: 1045 (28000): Access denied for user...`**: Check your `db.properties` file for correct `user` and `password`. Ensure the MySQL user has the necessary permissions.

* **`FileNotFoundError`**: Make sure all `.xlsx` files are present in the `data/` and `cloudinary_data/` directories as specified in the "Project Structure" section.

* **`ModuleNotFoundError`**: Ensure all required Python libraries are installed (`pip install ...`).

* **"Error inserting row..."**: This indicates an issue with the data in your Excel files not matching the table schema (e.g., incorrect data types, missing required fields, duplicate primary keys, or foreign key violations). Check the printed error message for specifics.

* **`bcrypt._bcrypt.BcryptError`**: This might occur if the `bcrypt` library has issues. Ensure it's correctly installed.

If you encounter any other issues, please refer to the output in the Jupyter Notebook for detailed error messages
