from fastapi import APIRouter, Query
from ..services import analytics

router = APIRouter(prefix="/analytics", tags=["analytics"])

@router.get("/kpis")
def get_kpis():
    return analytics.kpis()

@router.get("/top-categories")
def get_top_categories(n: int = Query(5, ge=1, le=20)):
    return analytics.top_categories(n)
