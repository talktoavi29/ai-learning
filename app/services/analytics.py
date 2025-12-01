from pathlib import Path
import pandas as pd

DATA = Path(__file__).resolve().parents[2] / "datasets"
ORDERS_CSV = DATA / "ecommerce_orders.csv"

def kpis():
    df = pd.read_csv(ORDERS_CSV, parse_dates=["order_date","ship_date"])
    df = df.assign(
        gross=df.unit_price*df.quantity,
        net=lambda d: d.gross*(1 - d.discount),
        delay=(df.ship_date - df.order_date).dt.days,
    )
    return {
        "orders": int(len(df)),
        "net_total": float(df["net"].sum()),
        "return_rate": float(df["returned"].mean()),
        "avg_delay_days": float(df["delay"].mean()),
    }

def top_categories(n=5):
    df = pd.read_csv(ORDERS_CSV, parse_dates=["order_date","ship_date"])
    df = df.assign(gross=df.unit_price*df.quantity, net=lambda d: d.gross*(1-d.discount))
    s = df.groupby("category")["net"].sum().sort_values(ascending=False).head(n)
    return s.reset_index().to_dict(orient="records")
