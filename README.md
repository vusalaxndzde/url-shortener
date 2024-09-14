# URL Shortener

A URL shortening service built with Spring Boot, using Redis as the primary datastore. The service allows for secure random URL generation with support for TTL (Time-To-Live), which automatically deletes URLs from Redis when they expire.

<p align="center">
     <a alt="Java">
        <img src="https://img.shields.io/badge/Java-v17-blue.svg" />
    </a>
    <a alt="Spring Boot">
        <img src="https://img.shields.io/badge/Spring%20Boot-v3.3.3-brightgreen.svg" />
    </a>
    <a alt="Maven">
        <img src="https://img.shields.io/badge/Maven-v4.0.0-orange.svg" />
    </a>
    <a alt="Redis">
        <img src="https://img.shields.io/badge/Redis-v7.4.0-red.svg" />
    </a>
</p>

## Features

- **Secure URL Shortening**: Generates unique, random, non-sequential short URLs using `SecureRandom`.
- **TTL Management**: Allows setting an expiration time (TTL) for each shortened URL, with Redis automatically removing the expired entries.
- **Redis for Storage**: Redis is used for storing short URLs and efficiently managing their expiration.
- **Redirection**: Shortened URLs are redirected using HTTP status 302 (Found) for temporary redirection.
- **Error Handling**: Custom exceptions (`URNotFoundException`) and enhanced error handling.

## Getting Started

### Prerequisites

- Java 17+ (or another compatible version)
- Maven
- Redis (for store and TTL management)

### Installation

1. Clone the repository:
    ```bash
    git clone https://github.com/vusalaxndzde/url-shortener.git
    cd url-shortener
    ```

2. Build the project:
    ```bash
    mvn clean install
    ```

3. Configure the application properties in `src/main/resources/application.properties` or `application.yml` to connect to Redis:
    ```properties
    spring.redis.host=localhost
    spring.redis.port=6379
    ```

4. If you don't have Redis running locally, you can use Docker to set it up:
    ```bash
    docker-compose up
    ```

5. Start the application:
    ```bash
    mvn spring-boot:run
    ```

### Usage

Once the application is running, you can shorten URLs, manage their TTL, and use the shortened URLs to perform redirects.

#### API Endpoints:

- **POST** `/api/v1/urls`
    - Description: Shortens a given long URL with an optional TTL.
    - Request Body:
      ```json
      {
        "longUrl": "https://www.example.com",
        "ttl": {
          "days": 1,
          "hours": 5,
          "minutes": 30
        }
      }
      ```
    - Response:
      ```json
      {
        "shortUrl": "http://localhost:8080/abc123"
      }
      ```

- **GET** `/api/v1/urls/{shortedUrl}`
    - Description: Redirects the shortened URL to the original long URL.
    - Example: `http://localhost:8080/api/v1/urls/abc123`

### Built With

- **Spring Boot**: For building the application.
- **Maven**: For project dependency management.
- **Redis**: Used for storing short URLs and managing TTL.
- **Docker**: Provides an easy setup for Redis.

## Docker Setup

If Redis is not running locally, you can quickly set it up using Docker:

1. Ensure Docker and Docker Compose are installed.
2. Run Redis:
    ```bash
    docker-compose up -d
    ```

This will start Redis in the background.

## Contributing

1. Fork the repository.
2. Create a new branch:
    ```bash
    git checkout -b feature/your-feature
    ```
3. Commit your changes:
    ```bash
    git commit -m "Add your commit message here"
    ```
4. Push to the branch:
    ```bash
    git push origin feature/your-feature
    ```
5. Open a pull request.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
