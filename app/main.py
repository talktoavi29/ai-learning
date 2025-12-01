from fastapi import FastAPI
from .routes import health, analytics, chat

app = FastAPI(title="Data & AI API")
app.include_router(health.router)
app.include_router(analytics.router)
app.include_router(chat.router)