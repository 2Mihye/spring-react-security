import React from "react";
import NaverLogin from "react-naver-login";

// npm install react-naver-login
const NaverApp = () => {
  const clientId = "tmfesNnXtMZzYFKN7Xuu";
  const NaverLoginSuccess = (res) => {
    console.log(res);
  };
  const NaverLoginFailure = (err) => {
    console.error(err);
  };
  // render{(props)} : 사용자가 버튼을 클릭하는 것을 나타내는 추가 구문
  return (
    <div>
      <NaverLogin
        clientId={clientId}
        callbackUrl="http://localhost:3000/naverLogin"
        onSuccess={NaverLoginSuccess}
        onFailure={NaverLoginFailure}
        render={(props) => (
          <button onClick={props.onClick}>네이버 로그인</button>
        )}
      />
    </div>
  );
};
export default NaverApp;
