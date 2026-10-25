# 📰 NewsHub – Online News Application

A full-stack news portal built with **Spring Boot (REST API)**, **React.js** and **MySQL**.
Readers can browse the latest headlines by category, search articles, read full stories and comment.
Editors use the admin panel to publish, edit and delete articles and manage categories.

## ✨ Features

**Reader**
- Home page with featured stories, latest news grid and a 🔥 trending sidebar (most viewed)
- Browse news by category (India, Technology, Sports, Business, Entertainment…)
- Live search on headline and summary
- Pagination
- Article page with reading time, view counter, share link and comments

**Admin / Editor**
- Publish, edit and delete articles
- Mark articles as *featured* for the home page
- Add and delete categories
- Article list with views and publish date

**Backend**
- Layered architecture: Controller → Service → Repository (Spring Data JPA)
- Pagination, sorting and dynamic JPQL search (category + keyword)
- Atomic view counter (`UPDATE … SET views = views + 1`)
- Bean Validation with clear field-level error messages
- Global exception handling with consistent JSON error responses
- Sample categories and articles loaded on first start
- CORS configured for the React dev server

## 🛠 Tech Stack

| Layer     | Technology                                        |
|-----------|---------------------------------------------------|
| Backend   | Java 17, Spring Boot 3, Spring Web, Spring Data JPA, Hibernate, Bean Validation |
| Database  | MySQL 8 (H2 in-memory profile for quick testing)  |
| Frontend  | React 18, React Router 6, Axios, Vite             |
| Tools     | Maven, Git, GitHub Actions                        |

## 📁 Project Structure

```
online-news-application/
├── backend/                         Spring Boot REST API
│   └── src/main/java/com/prachi/news/
│       ├── controller/              REST controllers
│       ├── service/                 Business logic
│       ├── repository/              Spring Data JPA repositories
│       ├── entity/                  JPA entities (Category, Article, Comment)
│       ├── dto/                     Request / response objects
│       ├── exception/               Custom exceptions + global handler
│       └── config/                  CORS config, sample data loader
└── frontend/                        React application (Vite)
    └── src/
        ├── pages/                   Home, ArticlePage, Admin
        ├── components/              Navbar, ArticleCard, Pagination
        └── api.js                   Axios API client
```

## 🗄 Database Design

```
categories (id, name UNIQUE, description)
    1
    │
    *
articles (id, title, summary, content, image_url, author, category_id FK,
          featured, views, published_at, updated_at)
    1
    │
    *
comments (id, article_id FK, name, text, created_at)
```

## 🔌 REST API

| Method | Endpoint                          | Description                                          |
|--------|-----------------------------------|------------------------------------------------------|
| GET    | `/api/categories`                 | List categories                                      |
| POST   | `/api/categories`                 | Add category                                         |
| DELETE | `/api/categories/{id}`            | Delete category (only if it has no articles)         |
| GET    | `/api/articles`                   | Paginated list (`?categoryId=&search=&page=&size=`)  |
| GET    | `/api/articles/featured`          | Featured articles                                    |
| GET    | `/api/articles/trending`          | Top 5 most viewed                                    |
| GET    | `/api/articles/{id}`              | Article details (+1 view; `?count=false` to skip)    |
| POST   | `/api/articles`                   | Publish article                                      |
| PUT    | `/api/articles/{id}`              | Update article                                       |
| DELETE | `/api/articles/{id}`              | Delete article and its comments                      |
| GET    | `/api/articles/{id}/comments`     | Comments of an article                               |
| POST   | `/api/articles/{id}/comments`     | Add comment                                          |
| DELETE | `/api/comments/{id}`              | Delete comment                                       |

Example request:

```json
POST /api/articles
{
  "title": "Pune emerges as a top hub for Java developers",
  "summary": "IT companies are expanding hiring for Spring Boot roles.",
  "content": "First paragraph...\n\nSecond paragraph...",
  "imageUrl": "https://picsum.photos/seed/code/800/450",
  "author": "Tech Desk",
  "categoryId": 2,
  "featured": true
}
```

## 🚀 Getting Started

### Prerequisites
- Java 17+, Maven 3.8+
- Node.js 18+
- MySQL 8 (optional – see H2 option below)

### 1. Backend

```bash
cd backend
# update spring.datasource.username / password in src/main/resources/application.properties
mvn spring-boot:run
```

The database `news_app_db` is created automatically. API runs on **http://localhost:8080**.

No MySQL? Run with the in-memory H2 database:

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=h2
```

### 2. Frontend

```bash
cd frontend
npm install
npm run dev
```

Open **http://localhost:5173**.

## 👨‍💻 Author

**Prachi Karad** – Java Full Stack Developer
