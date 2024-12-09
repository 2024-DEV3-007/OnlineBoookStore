# Simple Online Bookstore

This project is a simple code kata that involves creating a basic online bookstore which  will display a list of books and users will have the possibility to add books to their cart, display the cart and modify the quantity of items and remove items from the cart.

## **Features**

- Display a list of books with:
    - Title, Author, and Price.
- Shopping cart functionality:
    - Add books to the cart.
    - Modify quantities or remove items.
- Order summary during checkout.

## Requirements

- **Java** : 1.8
- **Springboot** : 2.7
- **Maven** : For Dependency management
- **JUnit** : 5.x


## Commit Message Style Guide
The project have followed the [Udacity Git Commit Message Style Guide](https://udacity.github.io/git-styleguide/), which provides a consistent format for writing commit messages.
Each commit messages contains **Title**. The title consists of the type of the message and subject. `type: Subject`

#### Commit Types

- **feat**: A new feature
- **fix**: A bug fix
- **docs**: Changes to documentation
- **style**: Code formatting changes (e.g., fixing indentation, removing spaces, etc.)
- **refactor**: Code refactoring without affecting functionality
- **test**: Adding or refactoring tests
- **chore**: Updates to build processes or auxiliary tools (e.g., package manager configs)

## How to Build the Application
- Clone this repository:
   ```bash
   https://github.com/2024-DEV3-007/OnlineBoookStore.git
- Build the project and run the tests by running
    ```bash
    mvn clean install
- The **Model Classes** used in the project are generated from the **OpenAPI** specification during the build process. Running `mvn clean install` will regenerate the models as part of the build.
- Run the application
- The frontend for this project is configured in `https://github.com/2024-DEV3-007/OnlineBookStoreClient.git`.
Run the backend application first followed by the frontend.

## Test reports

- Once after successful build of
  `mvn clean install`, navigate to target folder of the project root directory
- **Jacoco code coverage report :** Code Coverage report will be available in `target\site\jacoco` folder. View the report by launching **index.html**
