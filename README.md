# Scala JSON Parser

## Description
This project implements a JSON parser in Scala that validates JSON strings and determines if they are valid or invalid based on their format.

## Environment Setup

### Prerequisites
- Java (version 8 or higher)
- Scala (version 2.13 or higher)
- SBT (Scala Build Tool)

### Installation Steps
1. **Install Java**: Verify Java installation with:
   ```bash
   java -version
   ```
2. **Install Coursier**: Use Homebrew to install Coursier for Scala dependency management:
   ```bash
   brew install coursier/formulas/coursier && cs setup
   ```
3. **Install SBT**: Install SBT via Homebrew:
   ```bash
   brew install sbt
   ```
4. **Create `build.sbt`**: At the project root, create a `build.sbt` file with:
   ```scala
   name := "JsonParser"

   version := "0.1"

   scalaVersion := "2.13.8"
   ```
5. **Download Test Data**: For testing, download JSON test data from [Coding Challenge 2](https://codingchallenges.substack.com/p/coding-challenge-2) and place it in the `data/` directory.

## Running the Project
To run the project, use the following command in the terminal at the project root:
```bash
sbt run
sbt "run stepX"  # Replace X with the desired step number (in data/tests folder)
```

### Example JSON Files
- **Invalid JSON Example** (`data/tests/step4/invalid.json`):
    ```json
    {
      "key": "value",
      "key-n": 101,
      "key-o": {
        "inner key": "inner value"
      },
      "key-l": ['list value']  // Invalid due to single quotes
    }
    ```

- **Valid JSON Example** (`data/tests/step4/valid2.json`):
    ```json
    {
      "key": "value",
      "key-n": 101,
      "key-o": {
        "inner key": "inner value"
      },
      "key-l": ["list value"]  // Valid with double quotes
    }
    ```

### Expected Output
- Running the invalid JSON file will result in:
  ```
  /path/to/invalid.json is an invalid JSON.
  ```

- Running the valid JSON file will result in:
  ```
  /path/to/valid2.json is a valid JSON.
  ```