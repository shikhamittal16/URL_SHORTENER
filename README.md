# URL_SHORTENER

A lightweight and fast **URL Shortener** built with **Spring Boot**, **HTML/CSS/JS**, and **Redis**.  
It allows users to submit a long URL and get a short, shareable link in return.  
When a user accesses the short link, it automatically redirects them to the original long URL — with Redis caching to boost performance.

---

## 🚀 Features

- ✂️ Shorten long URLs into unique, shareable short links  
- 🔁 Instant redirection to the original URL  
- ⚡ Optimized performance using **Redis caching**  
- 💾 Persistent storage with H2 (in-memory database) but you can easily switch from H2 to PostgreSQL/MySQL by updating the database configuration in application.properties.  
- 🧾 RESTful API endpoints  
- 🌐 Simple and clean web UI (HTML, CSS, JS)

---

## 🏗️ Architecture Overview

User → HTML/JS Frontend → Spring Boot REST API → Redis Cache → Database

---

## 🖼️ Screenshots

![Original URL](https://github.com/shikhamittal16/URL_SHORTENER/blob/main/assets/originalUrl.png)  
![Short URL](https://github.com/shikhamittal16/URL_SHORTENER/blob/main/assets/shortUrl.png)
