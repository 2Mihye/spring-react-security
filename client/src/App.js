import React, { useState, useEffect } from "react";
import axios from "axios";

const App = () => {
  const [products, setProducts] = useState([]);
  const [newProduct, setNewProduct] = useState({ item_name: "", price: 0 });

  useEffect(() => {
    axios
      .get("http://localhost:8081/api/item")
      .then((response) => {
        setProducts(response.data);
      })
      .catch((error) => {
        console.error(":", error);
      });
  }, []);

  const handleAddProduct = () => {
    axios
      .post("http://localhost:8081/api/add", newProduct)
      .then((response) => {
        setProducts((prevProducts) => [...prevProducts, response.data]);
        setNewProduct({ item_name: "", price: 0 });
      })
      .catch((error) => {
        console.error("추가 에러:", error);
      });
  };

  const handleDeleteProduct = (id) => {
    axios
      .delete(`http://localhost:8081/api/delete/${id}`)
      .then((response) => {
        setProducts((prevProduct) =>
          // 현재 목록에서 삭제할 제품을 제외하고 새로운 배열을 생성
          // 삭제할 제품의 ID와 다른 제품들만 필터로 남겨주겠다 해주는 것 (삭제할 ID 빼고 모두 남기는 것)
          prevProduct.filter((product) => product.id !== id)
        );
      })
      .catch((error) => {
        console.error("error", error);
      });
  };
  return (
    <div>
      <h2>상품 리스트</h2>
      <ul>
        {products.map((product) => (
          <li key={product.id}>
            {product.item_name} - {product.price} 원
            <button onClick={() => handleDeleteProduct(product.id)}>
              삭제
            </button>
          </li>
        ))}
      </ul>

      <h2>상품 추가</h2>
      <label>상품명:</label>
      <input
        type="text"
        name="item_name"
        value={newProduct.item_name}
        onChange={(e) =>
          setNewProduct({ ...newProduct, item_name: e.target.value })
        }
      />

      <label>가격:</label>
      <input
        type="number"
        name="price"
        value={newProduct.price}
        onChange={(e) =>
          setNewProduct({ ...newProduct, price: e.target.valueAsNumber })
        }
      />

      <button onClick={handleAddProduct}>상품추가</button>
    </div>
  );
};

export default App;
