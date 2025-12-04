from fastapi import FastAPI, Depends
from sqlalchemy.orm import Session
import database
from models import Product
from database import SessionLocal, engine
import database_models

app = FastAPI()

database_models.Base.metadata.create_all(bind=engine)

@app.get("/")
def greet():
    print("Welcome to FastAPI learning!")
    return {"message": "Welcome to FastAPI learning!"}

products = [
    Product(id=1, name="Phone", description="budget phone", price=99.99, quantity=10),
    Product(id=2, name="Laptop", description="gaming laptop", price=999.99, quantity=5),
    Product(id=3, name="Tablet", description="android tablet", price=199.99, quantity=20)
]

def get_db():
    db = SessionLocal()
    try:
        yield db
    finally:
        db.close()

def init_db():
    db = SessionLocal()
    count = db.query(database_models.Product).count()
    if count == 0:
        for product in products:
            db_product = database_models.Product(
                id=product.id,
                name=product.name,
                price=product.price,
                description=product.description,
                quantity=product.quantity
            )
            db.add(db_product)
        db.commit()
        db.close()

init_db()

@app.get("/products")
def get_all_products(db: Session = Depends(get_db)):
    db_products = db.query(database_models.Product).all()
    if db_products:
        return db_products

@app.get("/products/{product_id}")
def get_product_by_id(product_id: int, db: Session = Depends(get_db)):
    db_product = db.query(database_models.Product).filter(database_models.Product.id == product_id).first()
    if db_product:
        return db_product
    return {"error": "Product not found"}


@app.post("/product")
def add_product(product: Product, db: Session = Depends(get_db)):
    db.add(database_models.Product(**product.model_dump()))
    db.commit()
    return product

@app.put("/product")
def update_product(id: int, product: Product, db: Session = Depends(get_db)):
    db_product = db.query(database_models.Product).filter(database_models.Product.id == id).first()
    if db_product:
        db_product.name = product.name
        db_product.price = product.price
        db_product.description = product.description
        db_product.quantity = product.quantity
        db.commit()
        return product
    return {"error": "Product not found"}
# if __name__ == "__main__":
#     greet()