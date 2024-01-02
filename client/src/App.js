import React, { useState, useEffect } from "react";
import axios from "axios";

const App = () => {
  const [products, setProducts] = useState([]);
  const [newProduct, setNewProduct] = useState({ item_name: "", price: 0 });
  const [editingProduct, setEditingProduct] = useState(null);
  const [isEditing, setIsEditing] = useState(false);

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

  const handleEditProduct = (product) => {
    setEditingProduct(product);
    setNewProduct({item_name : product.item_name, price: product.price});
    setIsEditing(true);
  };

  const handleUpdateProduct = () => {
    axios
    .put(`http://localhost:8081/api/update/${editingProduct.id}`, newProduct)
    .then((response) => {
        setProducts((prevProducts = prevProducts.map((product) => {
            if(product.id === editingProduct.id) {
                return response.data;
            }
            return product;
        })))
        reuturn updatedProducts;
    })
  }


  return (
    <div>
      <h2>상품 리스트</h2>
      <ul>
        {products.map((product) => (
          <li key={product.id}>
            {product.item_name} - {product.price} 원
            <button onClick={() => handleEditProduct(product)}>수정</button>
            <button onClick={() => handleDeleteProduct(product.id)}>
              삭제
            </button>
          </li>
        ))}
      </ul>

      <h2>{isEditing ? "상품 수정" : "상품 추가"}</h2>
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
      {isEditing ? (
        <div>
          <button onClick={handleUpdateProduct}>수정</button>
        </div>
      ) : (
        <button onClick={handleCancelProduct}>수정취소</button>
      )}
    </div>
  );
};

export default App;
