import React, { useState } from "react";
import axios from "axios";

export default function AddProduct(onAddProduct) {
  const [newProduct, setNewProduct] = useState({ item_name: "", price: 0 });

  // post axios api
  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setNewProduct((prevProduct) => ({ ...prevProduct, [name]: value }));
  };

  const handleAddProduct = async () => {
    try {
      // input 값 보내줄 post 작성
      const response = await axios.post(
        "http://localhost:8081/api/add",
        newProduct,
        { withCredentials: true }
      );

      // 변경된 데이터 값 저장해주는 setProduct
      onAddProduct(response.data);
      // 저장 후 값 초기화
      setNewProduct({ name: "", price: 0 });
    } catch (error) {
      console.log("error", error);
    }
  };

  return (
    <div>
      <h2>상품 추가</h2>
      <div>
        <label>상품명 : </label>
        <input
          type="text"
          name="item_name"
          value={newProduct.item_name}
          onChange={handleInputChange}
        />
      </div>
      <div>
        <label>가격 : </label>
        <input
          type="text"
          name="price"
          value={newProduct.price}
          onChange={handleInputChange}
        />
      </div>
      <button onClick={handleAddProduct}>상품 추가</button>
    </div>
  );
}
