import {Injectable} from '@angular/core';
import {Product} from "./product";
import {HttpClient} from "@angular/common/http";


@Injectable({
  providedIn: 'root'
})
export class ProductService {

  /*private identity: number = 6;

  private products: { [key: number]: Product } = {
    1: new Product(1, "Apple", 50),
    2: new Product(2, "Meat", 150),
    3: new Product(3, "Milk", 20),
    4: new Product(4, "Banana", 70),
    5: new Product(5, "Fish", 100)
  };*/

  constructor(public http: HttpClient) {
  }

  public findAll() {
    return this.http.get<Product[]>(`/api/v1/products/all`).toPromise();
    /* return new Promise<Product[]>((resolve, reject) => {
       resolve(
         Object.values(this.products)
       )
     })*/
  }

  public findById(id: number) {
    return this.http.get<Product>(`/api/v1/products/${id}`).toPromise();
    /* return new Promise<Product>((resolve, reject) => {
       resolve(
         this.products[id]
       );
     });*/
  }

  public save(product: Product) {
    if (product.id == null) {
      return this.http.post('/api/v1/products', product).toPromise();
    } else {
      return this.http.put('/api/v1/products', product).toPromise();
    }

    /*return new Promise<void>((resolve, reject) => {
      if (product.id == -1) {
        product.id = this.identity++;
      }
      this.products[product.id] = product;
      resolve();
    });*/
  }

  public delete(id: number) {
    return this.http.delete<Product>(`/api/v1/products/${id}`).toPromise();
    /* return new Promise<void>((resolve, reject) => {
       delete this.products[id];
       resolve();
     });*/
  }
}
