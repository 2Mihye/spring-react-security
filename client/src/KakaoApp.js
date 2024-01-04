import React from "react";
// npm install react-kakao-login
import KakaoLogin from "react-kakao-login";

//a28ba390970876871ae92c073b9b9cf2

const KakaoApp = () => {
  const KakaoLoginSuccess = (res) => {
    console.log(res);
  };
  const KakaoLoginFailure = (err) => {
    console.error(err);
  };

  return (
    <div>
      <KakaoLogin
        token="a28ba390970876871ae92c073b9b9cf2"
        onSuccess={KakaoLoginSuccess}
        onFailure={KakaoLoginFailure}
        getProfile={true}
        render={(props) => (
          <button onClick={props.onClick}>카카오 로그인</button>
        )}
      />
    </div>
  );
};
export default KakaoApp;
