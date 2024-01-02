import "./App.css";
import React, { useState, useEffect } from "react";
import axios from "axios";

function App() {
  const [data, setData] = useState([]);
  const [newMember, setNewMember] = useState({ member_name: "", email: "" });

  // fetchData라는 이름을 정의하여 try-catch 문을 사용해 비동기작업 중 발생하는 에러를 잡아내고 콘솔에 메세지를 출력하는 것
  // 간접적으로 호출
  useEffect(() => {
    const fetchData = async () => {
      try {
        const res = await axios.get("http://localhost:8080/api/member", {
          withCredentials: true,
        });
        setData(res.data);
      } catch (error) {
        console.log(error);
      }
    };
    fetchData();
  }, []);

  // 데이터 작성한 내용으로 변경하는 함수
  // e : even의 약자
  const handleInputChange = (e) => {
    const { name, value } = e.target; // input 안에 넣는 name과 value !
    setNewMember((prevMember) => ({ ...prevMember, [name]: value })); // prevMember을 복제해서 name을 내가 쓴 값으로 설정하는 것
  };

  // 데이터 보내줄 버튼함수 post 추가
  const handleAddMember = async () => {
    try {
      const response = await axios.post(
        "http://localhost:8080/api/member",
        newMember,
        {
          withCredentials: true,
        }
      );
      // 변경된 데이터 값 저장
      setData((prevMember) => [...prevMember, response.data]);

      // 데이터 저장된 후 빈 값으로 초기화 하기 원한다면 초기화도 진행
      setNewMember({ member_name: "", email: "" });
    } catch (error) {
      console.error("데이터가 부적합합니다.", error);
    }
  };

  /*
  직접적으로 한 번에 axios를 호출하는지 차이가 있을 뿐 위 코드와 아래 코드는 같은 것임.



  useEffect 안에서 직접 비동기 작업을 수행하고 간단하게 catch문을 사용하여 에러를 처리하고 콘솔에 에러 메세지를 출력

  useEffect(() => {
    axios
      // API 주소값을 가져옴.
      .get("http://localhost:8080/api/expression", { withCredentials: true })
      // response와 res는 같은 의미이며, 안에 변수값은 정해진 변수값은 없지만 되도록이면 res response를 사용하면 더 좋음 !
      .then((res) => {
        setData(res.data);
      })
      .catch((error) => {
        console.log("데이터없음", error);
      });
    // 처음에만 가져올 것이기 때문에 [] 공백을 넣어줌.
  }, []);
  */
  return (
    <div>
      <h1>API 호출 확인</h1>
      <ul>
        {data.map((member) => (
          <li key={member.id}>
            {member.membername} = {member.email}
          </li>
        ))}
      </ul>
      <h2>새로운 회원 저장</h2>
      <div>
        <label>회원 이름 : </label>
        <input
          type="text"
          name="member_name"
          value={newMember.member_name}
          onChange={handleInputChange}
        />
      </div>
      <div>
        <label>회원 이메일 : </label>
        <input
          type="text"
          name="email"
          value={newMember.email}
          onChange={handleInputChange}
        />
      </div>
      <button onClick={handleAddMember}>회원 저장</button>
    </div>
  );
}

export default App;
