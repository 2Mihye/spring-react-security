import React, { useEffect, useState } from "react";
import axios from "axios";
import { BrowserRouter as Router, Routes, Route, Link } from "react-router-dom";
import AddProduct from "./AddProduct";
// import { BrowserRouter as Router, Switch, Route, Link } from "react-router-dom"; // Switch => Routes 로 변경됨.

// react-router-dom
export default function App() {
  // data가 아닌 producs로 적을 예정
  const [products, setProducts] = useState([]);
  const [newProduct, setNewProduct] = useState({ item_name: "", price: 0 });

  // get 해주는 axios API 적기
  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await axios.get(
          "http://localhost:8081/api/product/item"
        );
        setProducts(response.data);
      } catch (error) {
        console.log("데이터를 불러오지 못했습니다.", error);
      }
    };
    fetchData();
  }, []);

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
      setProducts((prevProduct) => [...prevProduct, response.data]);
      // 저장 후 값 초기화
      setNewProduct({ name: "", price: 0 });
    } catch (error) {
      console.log("error", error);
    }
  };

  return (
    <Router>
      <div>
        <h1>상품 목록</h1>
        <ul>
          {products.map((product) => (
            <li key={product.id}>
              {product.item_name} : {product.price} 원
            </li>
          ))}
          <li>
            <Link to="/add">상품 목록 추가</Link>
          </li>
        </ul>
      </div>
      <Routes>
        <Route
          path="/add"
          element={<AddProduct onAddProduct={handleAddProduct} />}
        />
        <Route path="/item">
          <h2>상품 목록</h2>
        </Route>
      </Routes>
    </Router>
  );
}
